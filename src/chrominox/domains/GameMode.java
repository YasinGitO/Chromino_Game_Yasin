package chrominox.domains;

/**
 * Cette class va énumérer tout les modes de jeu qu'il y a dans le jeu chromino et va leur attribuer le nombre de 
 * Chromino avec lesquels les joueurs commencent la partie
 * 
 */
public enum GameMode {

	QUICK_GAME(5), CLASSIC_GAME(10);
	
	private final int nbChrominoesInHand;
	
	private GameMode(int nbChrominoesInHand) {
		this.nbChrominoesInHand = nbChrominoesInHand;
	}
	
	public int getNbChrominoesInHand() {
		return nbChrominoesInHand;
	}
	
}
