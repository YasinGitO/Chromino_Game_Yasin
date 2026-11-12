package chrominox.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette class va fabriquer et gérer la configuration d'une partie. Cette classe
 * permet de créer une nouvelle partie, d'ajouter des joueurs, et de configurer
 * des parties classiques ou rapides avec un nombre défini de chrominos par
 * joueur
 */
public class ChroGameFactory {

	private ChroGame currentGame;
	private final ChrominoGenerator generator;
	private final List<Player> players = new ArrayList<>();
	
	/**
	 * Ce constructeur initialise un generator de chromino
	 * 
	 * @param generator le generateur de chromino qui sera utilisé pour créer les
	 *                  objets du jeu
	 */
	public ChroGameFactory(ChrominoGenerator generator) {
		this.generator = generator;

		Player player1 = new Player("Joueur1", "ORANGE");
		Player player2 = new Player("Joueur2", "PINK");
		addPlayer(player1);
		addPlayer(player2);
	}

	/**
	 * Cette méthode va ajouter le Player en paramètre à la List
	 * 
	 * @param player player qu'on veut ajouter
	 */
	private void addPlayer(Player player) {
		if (player != null) {
			players.add(player);
		}
	}

	/**
	 * Cette méthode va créer une partie classique avec 10 chrominos
	 */
	public void createClassicGame() {
		setupNewGame(GameMode.CLASSIC_GAME);
	}

	/**
	 * Cette méthode va créer une partie rapide avec 5 chrominos
	 */
	public void createQuickGame() {
		setupNewGame(GameMode.QUICK_GAME);
	}

	/**
	 * Cette méthode va permettre de créer une nouvelle partie
	 * 
	 * @param nbChrominosPerPlayer le nombre de chromino par joueur
	 */
	private void setupNewGame(GameMode gameMode) {
		this.currentGame = new ChroGame(gameMode, generator.generateChrominoes(), players);
	}

	/**
	 * Cette méthode est un accesseur pour récuperer la game en cours
	 * 
	 * @return retourne la game en cours
	 */
	public ChroGame getCurrentGame() {
		return this.currentGame;
	}

}
