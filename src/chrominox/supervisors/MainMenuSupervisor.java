package chrominox.supervisors;

import java.util.*;

import chrominox.domains.ChroGameFactory;
import chrominox.supervisors.commons.ViewId;

/**
 * Cette classe va gérer le menu principal du jeu.
 * 
 * elle supervise les liens entre la vue du menu principal
 * et la création d’une nouvelle partie ou la fermeture du jeu.
 * 
 * Elle permet :
 * - d’afficher les options disponibles (nouvelle partie ou quitter),
 * - de lancer une partie
 * - de rediriger vers la bonne vue après le choix.
 */
public final class MainMenuSupervisor extends Supervisor {
	public static final String NEW_CLASSIC_GAME = "Partie classique";
	public static final String NEW_QUICK_GAME = "Partie rapide";
	public static final String QUIT = "Quitter";
	
	private final List<String> items = List.of(NEW_CLASSIC_GAME, NEW_QUICK_GAME,QUIT);
	private MainMenuView view;
	private final ChroGameFactory factory;
	/**
	 *  Constructeur du superviseur du menu principal.
	 * 
	 * @param factory fabrique permettant de créer une nouvelle partie de jeu.
	 */
	public MainMenuSupervisor(ChroGameFactory factory) {
		this.factory = factory;
	}

	public void setView(MainMenuView screen) {
		this.view = screen;
		this.view.setItems(items);
	}

	/**
	 * Méthode appelée par la vue quand l'utilisateur a choisi un item.
	 * 
	 * @param selected la position de l'item dans la liste reçue par la vue.
	 * */
	public void onItemSelected(int selected) {
		Objects.checkIndex(selected, items.size());
		var item = items.get(selected);
		
		if(QUIT.equals(item)) {
			this.view.onQuitConfirmed(ViewId.MAIN_MENU);
		} else {			
			if ((NEW_CLASSIC_GAME).equals(item)) {
			    factory.createClassicGame();
			}
			if ((NEW_QUICK_GAME).equals(item)) {
			    factory.createQuickGame();
			}
			this.view.goTo(ViewId.PLAY_GAME);
		}
		
	}
	
}
