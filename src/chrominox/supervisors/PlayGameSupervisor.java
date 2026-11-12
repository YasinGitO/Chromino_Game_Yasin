package chrominox.supervisors;

import java.util.List;
import java.util.Objects;

import chrominox.domains.ChroGameFactory;
import chrominox.domains.ChroGame;
import chrominox.domains.Chromino;
import chrominox.domains.CoordAndTile;
import chrominox.supervisors.PlayGameView.ViewMode;
import chrominox.supervisors.commons.ViewId;

/**
 * Supervise le jeu
 * Gère l'affichage de la board, la hand du joueur, et les actions (placer, piocher, passer,..)
 */
public class PlayGameSupervisor extends Supervisor {

	private PlayGameView view;
	private final ChroGameFactory factory;
	private ChroGame game;
	private ViewMode currentMode = ViewMode.HAND;

	/**
	 * Constructeur qui initialise le superviseur avec une factory
	 * 
	 * @param factory qui permet d'obtenir un jeu
	 */
	public PlayGameSupervisor(ChroGameFactory factory) {
		this.factory = factory;
	}

	public void setView(PlayGameView view) {
		this.view = Objects.requireNonNull(view);
	}

	@Override
	public void onEnter(ViewId fromScreen) {
		if (fromScreen == ViewId.MAIN_MENU) {
			this.game = factory.getCurrentGame();
			view.addMessage("Au tour du " + game.getCurrentPlayerName());
		}
		draw(); 
	}

	private void draw() {
		view.startDraw();

		drawBoard();
		drawHand();

		view.endDraw();
	}

	private void drawBoard() {
		
		
		for (CoordAndTile ct : game.getCoordAndTiles()) {
			view.addToBoard(ct.getColorName(), "BLACK", ct.getRow(), ct.getCol());
		}

		if (currentMode == ViewMode.BOARD) {

			Chromino activeChromino = game.getActiveChrominoCurrentPlayer();
			if(activeChromino != null) {
				for (CoordAndTile ct : activeChromino.getCoordAndTiles()) {
					view.addToBoard(ct.getColorName(), game.getCurrentPlayerColor(), ct.getRow(), ct.getCol());
				}
			}

		}
	}

	private void drawHand() {
		List<Chromino> hand = game.getCurrentPlayerHandForView();
		 
		for (int i = 0; i < hand.size(); i++) {
			Chromino chromino = hand.get(i);
			view.addToHand(chromino.getChrominoPartToString(0), chromino.getChrominoPartToString(1),
					chromino.getChrominoPartToString(2), (i == game.getSelectedChrominoIndex()) ? game.getCurrentPlayerColor() : "BLACK");
		}

	}

	/**
	 * Méthode appelée par la vue pour déplacer le chromino actif sur la mosaique de
	 * {@code dr} ligne et de {@code dc} colonnes.
	 */
	public void onMove(int dr, int dc) {
		if (currentMode == ViewMode.BOARD) {
			game.moveActualChromino(dr, dc);
			draw();
		}
	}

	/**
	 * Méthode appelée par la vue pour effectuer une rotation de 90 du chromino
	 * actif.
	 */
	public void onRotate() {
		if (currentMode == ViewMode.BOARD) {
			game.rotateActiveChromino();
			draw();

		}
	}
 
	/**
	 * Méthode appelée par la vue quand le joueur souhaite ajouter son chromino à la
	 * mosaique.
	 */
	public void onConfirm() {
		if (currentMode == ViewMode.BOARD) {
			var result = game.tryToPlaceActiveChromino();
			
			if(result.getMessage() != null) {
			view.addMessage(result.getMessage());
			}
			
			if (result.isSuccess()) {
				currentMode = ViewMode.HAND;
				view.switchMode(ViewMode.HAND);
				view.addMessage("Au tour du " + game.getCurrentPlayerName()+ " Score du Joueur1 : " + game.getPlayerScore(0) + " Score du Joueur2 : " + game.getPlayerScore(1));
			
				
				if (game.isGameOver()) {
	                view.goTo(ViewId.END_GAME);
	            }
			
			}
			
			
			draw();
		}
	}

	/**
	 * Méthode appelée par la vue quand le joueur souhaite passer son tour.
	 */
	public void onPass() {
		if (!game.currentPlayerCanPassTurn()) {
			view.addMessage("Vous devez piocher avant de pouvoir passer !");
			return;
		} 

		if (game.isGameOver()) {
	        view.goTo(ViewId.END_GAME);
	    }
		
		game.nextPlayer();
		view.addMessage("Au tour du " + game.getCurrentPlayerName() + " Score du Joueur1 : " + game.getPlayerScore(0) + " Score du Joueur2 : " + game.getPlayerScore(1));

		currentMode = ViewMode.HAND;
		view.switchMode(ViewMode.HAND);
		draw();
	}

	/**
	 * Appelée par la vue quand le joueur souhaite passer au chromino suivant.
	 */
	public void onSelectNextPiece() {
		  if (currentMode == ViewMode.HAND) {
		        game.selectNextChrominoInHand();
		       if(game.getCurrentPlayerHandSize()>0) {
		    	 draw(); 
		       }
		
		    }

	}

	/**
	 * Appelée dans le mode {@code BOARD} si le joueur souhaite changer de pièce.
	 */
	public void onBack() {
		if (currentMode == ViewMode.BOARD) {
			
				game.putChrominoBackInHand();
				view.switchMode(ViewMode.HAND);
				currentMode = ViewMode.HAND;
				draw();
			
		}
	}

	/**
	 * Appelée si le joueur souhaite piocher un nouveau chromino
	 */
	public void onPick() {
		if (currentMode == ViewMode.HAND) {
	        var result = game.pickOneChromino();
	        
	        if(result.getMessage() != null) {
	        	view.addMessage(result.getMessage());
	        }
	        if (result.isSuccess()) {
	            currentMode = ViewMode.BOARD;
	            view.switchMode(ViewMode.BOARD);
	        }

	        draw();
	    }
	}

	/**
	 * Appelée dans le mode {@code HAND} si le joueur a choisi son chromino.
	 */
	public void onPieceSelected() {
		if (currentMode == ViewMode.HAND) {
			game.selectChrominoForPlacement(game.getSelectedChrominoIndex());

			currentMode = ViewMode.BOARD;
			view.switchMode(ViewMode.BOARD);
			draw();
		}
	}
}
