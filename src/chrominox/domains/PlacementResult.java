package chrominox.domains;

/**
 * Représente le résultat d'une tentative de placement d'un chromino. Contient
 * un indicateur de succès et un message explicatif.
 */
public class PlacementResult {

	private final boolean succes;
	private final String message;

	/**
	 * Constructeur privé pour forcer l'utilisation des méthodes statiques.
	 */
	private PlacementResult(boolean success, String message) {
		this.succes = success;
		this.message = message;
	}

	private PlacementResult(boolean success) {
		this.succes = success;
		this.message = null;
	}

	/**
	 * Fabrique un PlacementResult représentant un succès.
	 */
	public static PlacementResult success() {
		return new PlacementResult(true);
	}

	/**
	 * Fabrique un PlacementResult représentant un échec avec une raison.
	 */
	public static PlacementResult failure(String reason) {
		return new PlacementResult(false, reason);
	}

	/**
	 * @return true si le placement est un succès, false sinon.
	 */
	public boolean isSuccess() {
		return succes;
	}

	/**
	 * @return le message explicatif (succès ou échec).
	 */
	public String getMessage() {
		return message;
	}
}