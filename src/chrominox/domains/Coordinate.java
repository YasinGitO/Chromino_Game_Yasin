package chrominox.domains;

import java.util.List;
import java.util.Objects;

/**
 * Cette class va gerer tout ce qui va être en rapport avec les coordonees pour qu'on puisse les utiliser correctement 
 * sur la mosaique
 */
public class Coordinate {
    private int row;
    private int col;

    /**
     * Constructeur de la class Coordinate. 
     * Ce Constructeur initialise simplement deux champs
     * 
     * @param row
     * @param col
     * 
     * CTT:
     * O(1)
     * 
     * CST:
     * On crée un objet a partir de deux champs O(1)
     */
    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }
    
    
    /**
     * Accesseur pour récupérer le row (ligne) d'une Coordinate
     * 
     * @return Le row de la Coordinate
     * 
     * CTT:
     * O(1)
     * 
     * CST:
     * aucun nouvel objet
     */
    public int getRow() {
    	return this.row;
    }
    
    /**
     * Accesseur pour récupérer le col (collone) d'une Coordinate
     * 
     * @return Le col de la Coordinate
     * 
     * CTT:
     * O(1)
     * 
     * CST:
     * aucun nouvel objet
     */
    public int getCol() {
    	return this.col;
    }
    
    /**
     * Modifie le champ row de la Coordonée 
     * 
     * @param newRow le nouveau row avec lequel on veut remplacer le champ row actuel
     * 
     * CTT:
     * C(1)
     * 
     * CST:
     * aucun nouvel Objet
     */
    public void setRow(int newRow) {
    	this.row = newRow;
    }
    
    /**
     * Modifie le champ col de la Coordonée 
     * 
     * @param newRow le nouveau colo avec lequel on veut remplacer le champ col actuel
     * 
     * CTT:
     * C(1)
     * 
     * CST:
     * aucun nouvel Objet
     */
    public void setCol(int newCol) {
    	this.col = newCol;
    }
    
    
    /**
     * Cette méthode va comparer deux objets pour voir si ce sont les mêmes
     * 
     * @param objet avec lequel on veut comparer le premier objet
     * @return true si les deux objets sont egaux / false si l'obet est null ou s'il ne sont pas égaux
     * 
     * CTT: 
     * O(1) on compare juste 2 entiers
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        Coordinate other = (Coordinate) obj;
        return row == other.getRow() && col == other.getCol();
    }

    /**
     * Cette méthode calcule le code de hashage de l'objet basé sur ses Coordinate (row, col)
     * 
     * @return Le code de hashage
     * 
     * CTT:
     * O(1)
     * 
     * CST:
     * utilisation du hashage est de O(1)
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
    
    /**
     * Cette méthode va créer un String pour afficher les Coordinate
     * 
     * @return les coordonnées sous forme de row, col
     * 
     * CTT:
     * O(1)
     * 
     * CST:
     * Création d'un objet String O(1)
     */
    @Override
    public String toString() {
    	return String.format("%d, %d", getRow(),getCol());
    }
    
    /**
     * Cette méthode va retourner des nouvelles coordonées en additionnant aux coordonées actuelles 
     * le nombre de pas qu'elle va faire 
     * 
     * @param direction
     * @param steps
     * @return
     */
    public Coordinate move(Orientation direction, int steps) {
        return new Coordinate(
                this.row + direction.getDRow() * steps,
                this.col + direction.getDCol() * steps
            );
    
    }


	/**
	 * Cette méthode permet d'obtenir toute les coordonées adjacentes a une posiiton
	 * 
	 * @return une List des 4 coordinates autour d'une position 
	 */
	public List<Coordinate> getAdjacentCoordinates() {
		return List.of(new Coordinate(getRow() - 1, getCol()), new Coordinate(getRow() + 1, getCol()),
				new Coordinate(getRow(), getCol() - 1), new Coordinate(getRow(), getCol() + 1));
	}
    
}