package chrominox.domains;

/**
 * Cette classe va servir a gérer tout ce qui aura un rapport avec l'orientation du chromino pour pouvoir 
 * avoir les positions qu'un chromino sera quand il sera dans une telle ou telle orientation
 * 
 */
public enum Orientation {

	UP(-1, 0), RIGHT(0, 1), DOWN(1, 0), LEFT(0, -1);

	private final int dRow;
	private final int dCol;

	/**
	 * Ce constructeur va initialiser les positions en paramètres avec les attributs de la classe
	 * 
	 * @param dRow la ligne
	 * @param dCol la collone
	 */
	Orientation(int dRow, int dCol) {
		this.dRow = dRow;
		this.dCol = dCol;
	}

	/**
	 * Cette méthode va retourner le deltaRow 
	 * 
	 * @return deltaRow
	 */
	public int getDRow() {
		return dRow;
	}

	/**
	 * Cette méthode va retourner le delta Collonne
	 * 
	 * @return delta Collone
	 */
	public int getDCol() {
		return dCol;
	}

	/**
	 * Cette méthode retourne l'orientation obtenue après une rotation de 90 degres dans le sens horrraire
	 * 
	 * @return une orientation apres une rotation de 90 degres
	 */
	public Orientation rotate90Clockwise() {
		return switch (this) {
		case UP -> RIGHT;
		case RIGHT -> DOWN;
		case DOWN -> LEFT;
		case LEFT -> UP;
		};
	}

	/**
	 * Cette méthode retourne l'orientation obtenue apreès une rotation de 90 degres dans le sens anti-horraire
	 * 
	 * @return une orientation apres une rotation de 90 degres dans le sens anti-horraire
	 */
	public Orientation rotate90CounterClockwise() {
		return switch (this) {
		case UP -> LEFT;
		case LEFT -> DOWN;
		case DOWN -> RIGHT;
		case RIGHT -> UP;
		};
	}

	/**
	 * Cette méthode retourne une orientation opposée (a 180 degres)
	 *  
	 * @return l'orientation opposée a l'actuelle
	 */
	public Orientation opposite() {
		return switch (this) {
		case UP -> DOWN;
		case DOWN -> UP;
		case LEFT -> RIGHT;
		case RIGHT -> LEFT;
		};
	}
}
