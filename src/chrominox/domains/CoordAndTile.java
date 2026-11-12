package chrominox.domains;

import java.util.List;

/**
 * Cette class représente une assossiation enter une Coordinate et une tilType
 */
public class CoordAndTile {
	private final Coordinate coord;
	private final TileType tile;

	/**
	 * Constructeur principal de la classe 
	 * 
	 * @param coord qu'on associe a une tile
	 * @param tile qui se trouve a une coordinate
	 */
	public CoordAndTile(Coordinate coord, TileType tile) {
		this.coord = coord;
		this.tile = tile;
	}
	
	/**
	 * Retourne la coordonée associée a cette tilz
	 * 
	 * @return la coordonée 
	 */
	public Coordinate getCoord() {
		return this.coord;
	}
	
	/**
	 * retourne le tileType
	 * 
	 * @return le tileType
	 */
	public TileType getTileType() {
		return this.tile;
	}
	
	/**
	 * Retourne l'indice de la collonne de la coordonnee
	 * 
	 * @return retourne la collonne
	 */
	public int getCol() {
		return this.coord.getCol();
	}
	
	/**
	 * Retourne l'indice de la ligne de la coordonnée
	 * 
	 * @return return la ligne
	 */
	public int getRow() {
		return this.coord.getRow();
	}
	
	/**
	 * Retourne le nom de la couleur sous forme de chaine
	 * 
	 * @return le nom de la coleur
	 */
	public String getColorName() {
		return tile.toString();
	}
	
	/**
	 * Modifie la collonne de la coordonée
	 * 
	 * @param newCol la nouvelle valeur de la collonne
	 */
	public void setCol(int newCol) {
		this.coord.setCol(newCol);
	}
	
	/**
	 * Modifie la ligne de la collonne 
	 * 
	 * @param newRow la nouvelle valeur de la ligne
	 */
	public void setRow(int newRow) {
		this.coord.setRow(newRow);
	}

	/**
	 * Cette méthode renvoie les coordonées adjacentes a une coordonnée
	 * 
	 * @return une List des coordonées adjacentes a une coordonnée
	 */
	public List<Coordinate> getAdjacentCoordinates(){
		return coord.getAdjacentCoordinates();
	}

	

	
	
}
