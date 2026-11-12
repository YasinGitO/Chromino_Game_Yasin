package chrominox.domains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * La class ChroGame gère la logique principale du jeu. Elle maintient la liste
 * des joueurs, le sac e chromino, la mosaique. Elle permet de faire le lien
 * avec toutes les classes pour la mise en place optimale du jeu
 */
public class ChroGame {

	private Player currentPlayer;
	private final ChroBag chrominoBag;

	private final Map<Player, PlayerTurnState> playersWithTurnState = new HashMap<>();
	private ChroMosaic mosaique;
	private final GameMode gameMode;
	private Player winner;

	/**
	 * Ce constructeur va initialliser une partie
	 * 
	 * @param gameMode le mode de jeu quichGame ou ClassicGame
	 * @param chrominoes la list de chromino bag
	 * @param players la list de tout les joueurs
	 */
	public ChroGame(GameMode gameMode, List<Chromino> chrominoes, List<Player> players) {
		if (players == null || players.size() != 2) {
			throw new IllegalArgumentException();
		}
		this.chrominoBag = new ChroBag(chrominoes);
		this.gameMode = gameMode;

		for (Player player : players) {
			playersWithTurnState.put(player, new PlayerTurnState());
		}

		this.mosaique = new ChroMosaic(chrominoBag.getFirstCameleon());

		List<Player> playerList = new ArrayList<>(players);
		playerList.get(0).buildHand(chrominoBag.retirerNChrominos(gameMode.getNbChrominoesInHand()));
		playerList.get(1).buildHand(chrominoBag.retirerNChrominos(gameMode.getNbChrominoesInHand()));

		setFirstPlayer(playerList);
	}

	/**
	 * Cette méthode va réinitialiser toute la game pour pouvoir en relancer une de
	 * 0.
	 */
	public void resetGame() {
		for (Player player : getPlayerList()) {
			player.resetScore();
			player.resetTurn();
			player.buildHand(chrominoBag.retirerNChrominos(gameMode.getNbChrominoesInHand()));
		}
		mosaique = new ChroMosaic(chrominoBag.getFirstCameleon());
		resetAllPlayerStates();
		setFirstPlayer(getPlayerList());
	}

	/**
	 * Cette méthode va retouner le premier Player de manière aleatoire entre tout
	 * les players de la list de player
	 * 
	 * @param playerList Une List avec tout les players qui jouent
	 * @return un player aleatoire
	 */
	private void setFirstPlayer(List<Player> playerList) {
		Collections.shuffle(playerList);
		this.currentPlayer = playerList.get(0);
	}

	/**
	 * Cette méthode va retourner le joueur suivant dans la List
	 * 
	 * @param currentPlayer le joueur actuel de la partie
	 * @return le joueur de la list suivant
	 */
	public Player nextPlayer() {
		List<Player> playerList = getPlayerList();
		int posPlayerList = playerList.indexOf(currentPlayer);
		int nextPlayerPos = (posPlayerList + 1) % playerList.size();
		this.currentPlayer = playerList.get(nextPlayerPos);

		if (hasRoundEnded() && !shouldEndGameInsteadOfReset()) {
			resetAllPlayerStates();
		}

		return this.currentPlayer;
	}

	/**
	 * Cette méthode récupère une copie de la main du joueur
	 * 
	 * @return une copie de la main du currentPlayer
	 */
	public List<Chromino> getCurrentPlayerHandForView() {
		return currentPlayer.getHandForViewOnly();
	}

	/**
	 * Cette méthode va nous fournir le nom du currentPlayer
	 * 
	 * @return le nom du currentPlayer
	 */
	public String getCurrentPlayerName() {
		return currentPlayer.getName();
	}


	/**
	 * Cette méthode indique si le currentPlayer peut passer son tour
	 * 
	 * @return true si le joueur peut passer son tour/ false si il ne peut pas
	 */
	public boolean currentPlayerCanPassTurn() {
		return playersWithTurnState.get(currentPlayer).canPass();
	}

	/**
	 * Vérifie si le joueur actuel peut piocher un chromino
	 * 
	 * @return true si le joueur peut piocher, false sinon
	 */
	private boolean currentPlayerCanDraw() {
		return playersWithTurnState.get(currentPlayer).canDraw();
	}

	/**
	 * Cette méthode va ajouter un chromino a la main du joueur actif
	 * 
	 * @param chromino qu'on veut ajouter a la hand
	 */
	private void addChrominoToCurrentPlayerHand(Chromino chromino) {
		this.currentPlayer.addChrominoToHand(chromino);
	}

	/**
	 * Cette méthode va retourner le chromino actif du current player
	 * 
	 * @return va retourner le chromino actif du current player
	 */
	public Chromino getActiveChrominoCurrentPlayer() {
		return currentPlayer.getActiveChromino();
	}

	/**
	 * Cette méthode va mettre a jour temporairement la position du chromino actif
	 * 
	 * @param coords la coordonnée sur laquelle on veut deplacer le chromino
	 */
	public void moveActualChromino(int dr, int dc) {
		currentPlayer.moveActiveChromino(dr, dc);
	}

	/**
	 * Cette méthode va nous retourner une copie de la List de Player
	 * 
	 * @return une copie de la List de player
	 */
	public List<Player> getPlayerList() {
		return new ArrayList<>(playersWithTurnState.keySet());
	}

	/**
	 * Cette méthode va faire piocher un chromino depuis le chromino bag au current
	 * player pour l'ajouter a sa main
	 * 
	 */
	public PlacementResult pickOneChromino() {
		if (!currentPlayerCanDraw()) {
			return PlacementResult.failure("Vous avez déjà pioché ce tour.");
		}

		Chromino drawn = chrominoBag.retirerUnSeulChromino();
		currentPlayer.setActiveChromino(drawn);
		playersWithTurnState.get(currentPlayer).setDrawn(true);

		return PlacementResult.success();
	}

	/**
	 * Cette méthode va faire rotate le chromino actif de 90 degrés
	 */
	public void rotateActiveChromino() {
		if (currentPlayer.getActiveChromino() != null) {
			currentPlayer.rotateActiveChromino90Degres();

		}
	}

	/**
	 * Cette méthode nous retourne la couleur du currentPlayer sous forme de string
	 * 
	 * @return La couleur du joueur
	 */
	public String getCurrentPlayerColor() {
		return currentPlayer.getColor();
	}

	/**
	 * Cette méthode va nous retourner une List de CoordAndTile avec toutes les
	 * cases occupées de la mosaique
	 * 
	 * @return une list de coordAndTile
	 */
	public List<CoordAndTile> getCoordAndTiles() {
		List<CoordAndTile> list = new ArrayList<>();
		for (Coordinate coord : mosaique.getOccupiedCoordinates()) {
			TileType tile = mosaique.getTileTypeFromCoordinate(coord);
			list.add(new CoordAndTile(coord, tile));
		}
		return list;
	}

	/**
	 * Cette méthode va nous permettre de paramètrer le chromino actif pour qu'il
	 * soit le chromino qu'on va lui donner en paramètre
	 * 
	 * @param chromino le chromino qu'on veut mettre en chrominoActif
	 */
	private void setActiveChromino(Chromino chromino) {
		currentPlayer.setActiveChromino(chromino);
	}

	/**
	 * Cette méthode va nous retourner la taille de la hand du joueur
	 * 
	 * @return un entier avec le nombre de chromino que le joueur a dans sa main
	 */
	public int getCurrentPlayerHandSize() {
		return currentPlayer.getHandSize();
	}

	/**
	 * Selectionne le chromino suivant dans la hand
	 */
	public void selectNextChrominoInHand() {
		currentPlayer.selectNextChromino();
	}

	/**
	 * Retourne l'index du chromino selectionné
	 * 
	 * @return l'index du chromino selectionné
	 */
	public int getSelectedChrominoIndex() {
		return currentPlayer.getSelectedChrominoIndex();
	}

	/**
	 * Cette méthode va nous permettre de choisir un chromino avant de le placer
	 * 
	 * @param i La position du chromino dans la hand du currentPlayer
	 */
	public void selectChrominoForPlacement(int i) {
		currentPlayer.getChrominoFromHandToActive(i);

	}

	/**
	 * Cette méthode va essayer de placer le chromino actif sur la mosaique a une
	 * position et nous retouenr un PlacementResult
	 * 
	 * @return Si on arrive a le placer : succes donc on le place et si on arrive
	 *         pas, failure avec un message qui indique le problème
	 */
	public PlacementResult tryToPlaceActiveChromino() {
		Chromino active = currentPlayer.getActiveChromino();

		if (active == null) {
			return PlacementResult.failure("Aucun chromino actif.");
		}

		PlacementResult result = mosaique.tryPlace(active);

		if (result.isSuccess()) {
			currentPlayer.addToScore(mosaique.computeScore(active));
			playersWithTurnState.get(currentPlayer).setPlaced(true);
			currentPlayer.setActiveChromino(null);
			resetSelectedChrominoInHand();
			nextPlayer();
		}

		return result;
	}

	/**
	 * Cette méthode reset l'index du chromino selectionné a 0
	 */
	void resetSelectedChrominoInHand() {
		currentPlayer.resetSelectedChromino();

	}

	/**
	 * Cette méthode vérifie toutes les conditions pour voir si une partie est
	 * terminée ou non
	 * 
	 * @return true si la parie est terminée/ false si elle n'est pas finie
	 */
	public boolean isGameOver() {
		boolean anyHandEmpty = hasAnyPlayerEmptyHand();
		boolean allPlayed = haveAllPlayersPlayed();
		boolean bagEmpty = chrominoBag.isEmpty();
		boolean allPassed = haveAllPlayersPassed();

		if (isHandEmpty(anyHandEmpty, allPlayed) || isClassicGameFinished(bagEmpty, allPassed)
				|| isQuickGameFinished(bagEmpty, allPassed)) {

			applyPenaltyForRemainingChrominoes();
			setWinner();
			return true;
		}

		return false;
	}

	private boolean isHandEmpty(boolean anyHandEmpty, boolean allPlayed) {
		return anyHandEmpty && allPlayed;
	}

	private boolean isClassicGameFinished(boolean bagEmpty, boolean allPassed) {
		return gameMode == GameMode.CLASSIC_GAME && bagEmpty && allPassed;
	}

	private boolean isQuickGameFinished(boolean bagEmpty, boolean allPassed) {
		return gameMode == GameMode.QUICK_GAME && (bagEmpty || allPassed);
	}

	/**
	 * Cette méthode regarde si un des joueurs à sa hand vide et son chromino actif
	 * vide
	 * 
	 * @return
	 */
	private boolean hasAnyPlayerEmptyHand() {
		for (Player p : playersWithTurnState.keySet()) {
			if (p.getHandSize() == 0 && getActiveChrominoCurrentPlayer() == null)
				return true;
		}
		return false;
	}

	/**
	 * Cette méthode vérifie si tout les joueurs ont joué dans un tour
	 * 
	 * @return true si tout les joueurs ont joué(placé un chromino ou pioché)/ false
	 *         si le cas contraire
	 */
	private boolean haveAllPlayersPlayed() {
		for (PlayerTurnState state : playersWithTurnState.values()) {
			if (!(state.playerHasPlaced() || state.playerHasDrawned())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Cette méthode regarde si tout les joueurs ont passé leur tour ou non
	 * 
	 * @return true si ils ont passé leur tour/ false si ils n'ont pas encore passé
	 */
	private boolean haveAllPlayersPassed() {
		for (PlayerTurnState state : playersWithTurnState.values()) {
			if (!state.canPass())
				return false;
		}
		return true;
	}


	/**
	 * Cette méthode vérifie si le round est termine c'est a dire quand tout les
	 * joueurs ont joué
	 * 
	 * @return true si ils ont joué et le tour est terminé/ false si l'inverse
	 */
	private boolean hasRoundEnded() {
		for (PlayerTurnState state : playersWithTurnState.values()) {
			if (!state.playerHasPlaced() && !state.canPass()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Cette méthode reset l'état de tout les joueurs. Si ils ont joué ou pioché.
	 * 
	 */
	private void resetAllPlayerStates() {
		for (PlayerTurnState state : playersWithTurnState.values()) {
			state.reset();
		}
	}

	/**
	 * Cette méthode retourne la méthode isGameOver si la game devrait être terminée
	 * au lieu d'être réinitialiséée a la fin du tour
	 * 
	 * @return isGameOver
	 */
	private boolean shouldEndGameInsteadOfReset() {
		return isGameOver();
	}

	/**
	 * Cette méthode remet le chromino actif dans la hand du player
	 * 
	 */
	public void putChrominoBackInHand() {
		Chromino chrominoActif = getActiveChrominoCurrentPlayer();
		if (chrominoActif != null) {
			addChrominoToCurrentPlayerHand(chrominoActif);
			setActiveChromino(null);
		}
	}

	/**
	 * Cette méthode compare le résultat des deux joueurs pour voir qui a gagné la
	 * partie. Le gagnant est celui qui a le score le plus élevé.
	 */
	private void setWinner() {
		List<Player> playerList = getPlayerList();
		if (playerList == null || playerList.size() != 2) {
			throw new IllegalArgumentException();
		}

		Player p1 = playerList.get(0);
		Player p2 = playerList.get(1);

		if (p1.getScore() > p2.getScore()) {
			winner = p1;
		} else if (p2.getScore() > p1.getScore()) {
			winner = p2;
		}
	}

	/**
	 * Cette méthode retourne le gagnant de la partie
	 * 
	 * @return le gagnant de la partie
	 */
	public Player getWinner() {
		return this.winner;
	}

	/**
	 * Cette méthode retourne le nom du gagnant
	 * 
	 * @return le nom de gagnant sous forme de String
	 */
	public String getWinnerName() {
		return winner.getName();
	}

	/**
	 * Cette méthode va calculer le nombre de points restant pour les chrominos de
	 * la hand et les soustraire du score du player
	 */
	private void applyPenaltyForRemainingChrominoes() {
		for (Player player : getPlayerList()) {
			int score = 0;
			for (Chromino chromino : player.getHandForViewOnly()) {
				score += chromino.getValue();
			}
			player.addToScore(-score);
		}
	}

	/**
	 * Cette méthode va retourner le score du player entré dans l'index
	 * 
	 * @param i l'index du player a qui on veut le score
	 * @return le score du player de l'index
	 */
	public int getPlayerScore(int i) {
		List<Player> playerList = getPlayerList();
		Player player = playerList.get(i);
		return player.getScore();
	}

}
