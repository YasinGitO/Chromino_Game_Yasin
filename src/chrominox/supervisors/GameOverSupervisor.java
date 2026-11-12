package chrominox.supervisors;

import chrominox.domains.ChroGame;
import chrominox.domains.ChroGameFactory;
import chrominox.domains.Player;
import chrominox.supervisors.commons.ViewId;

/**
 * Affiche les scores des joueurs et désigne le vainqueur.
 * */
public class GameOverSupervisor extends Supervisor {
	
	private final ChroGameFactory factory;
	private GameOverView view;
	private ChroGame game;
	
	/**
	 * Ce constructeur initialise la factory
	 * 
	 * @param factory qu'on veut initialiser
	 */
	public GameOverSupervisor(ChroGameFactory factory) {
		this.factory = factory;
	}
	
	public void setView(GameOverView view) {
		this.view = view;
	}
	
	/**
	 * {@inheritDoc}
	 * */
	@Override
	public void onEnter(ViewId fromView) {
		if(fromView == ViewId.PLAY_GAME) {
			this.game = factory.getCurrentGame();
				view.startDraw();

				for (Player player : game.getPlayerList()) {
					view.addScore(player.getName(), player.getScore(), player.getColor());
				}

				Player winner = game.getWinner();
				if (winner != null) {
					view.setWinner(game.getWinnerName());
				}

				view.endDraw();
			}
			//TODO: afficher les scores des joueurs
			
			//TODO: afficher le vainqueur
		}
	

	/**
	 * Appelée par la vue quand l'utilisateur souhaite revenir au menu principal.
	 * */
	public void onGoToMainMenu() {
		//TODO: naviguer vers le menu principal
		if(game.isGameOver()) {
			view.goTo(ViewId.MAIN_MENU);
			game.resetGame();
		}
	}
}
