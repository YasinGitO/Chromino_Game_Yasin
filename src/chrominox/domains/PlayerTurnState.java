package chrominox.domains;

/**
 * Représente l'état d'un joueur pendant un tour de jeu.
 * 
 * Cette classe encapsule les deux états essentiels à gérer pendant un tour :
 * - hasDrawn : indique si le joueur a déjà pioché un chromino.
 * - hasPlaced : indique si le joueur a déjà placé un chromino.
 * 
 * Elle permet de suivre et de restreindre les actions possibles selon les règles
 * du jeu : un joueur ne peut piocher qu'une seule fois par tour, et ne peut passer
 * que s’il a déjà tenté de jouer ou pioché.
 * 
 * Utilisée au sein de la classe Player pour contrôler les actions autorisées
 * à chaque étape d’un tour.
 */
public class PlayerTurnState {

	private boolean hasDrawn;
	private boolean hasPlaced;

	/**
	 * Initialise un nouvel état de tour de joueur avec les deux indicateurs à false.
	 */
	public PlayerTurnState() {
		this.hasDrawn = false;
		this.hasPlaced = false;
	}

	/**
	 * Indique si le joueur a déjà pioché un chromino pendant le tour.
	 * 
	 * @return true si le joueur a pioché, sinon false
	 */
	public boolean playerHasDrawned() {
		return hasDrawn;
	}

	/**
	 * Indique si le joueur a déjà placé un chromino pendant le tour.
	 * 
	 * @return true si le joueur a placé un chromino, sinon false.
	 */
	public boolean playerHasPlaced() {
		return hasPlaced;
	}

	/**
	 * Définit l’état hasDrawn.
	 * 
	 * @param drawn true si le joueur a pioché,false sinon.
	 */
	public void setDrawn(boolean drawn) {
		this.hasDrawn = drawn;
	}

	/**
	 * Définit l’état hasPlaced.
	 * 
	 * @param placed true si le joueur a placé un chromino, false sinon.
	 */
	public void setPlaced(boolean placed) {
		this.hasPlaced = placed;
	}

	/**
	 * Réinitialise l’état du tour : le joueur n’a ni pioché, ni placé de chromino.
	 */
	public void reset() {
		hasDrawn = false;
		hasPlaced = false;
	}

	/**
	 * Vérifie si le joueur peut encore piocher un chromino ce tour-ci.
	 * 
	 * @return true s’il n’a pas encore pioché, sinon false.
	 */
	public boolean canDraw() {
		return !hasDrawn;
	}

	/**
	 * Vérifie si le joueur est autorisé à passer son tour.
	 * 
	 * Selon les règles actuelles, un joueur ne peut passer que s’il a déjà pioché.
	 * 
	 * @return true si le joueur a pioché ce tour-ci, sinon false.
	 */
	public boolean canPass() {
		return hasDrawn;
	}
}
