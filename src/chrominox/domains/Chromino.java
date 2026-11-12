package chrominox.domains;

import java.util.*;

/**
 * Cette class représente un Chromino composé de trois tuiles (TileType)
 * alignées et associées à des coordonnées.
 * 
 * Chaque Chromino possède une orientation et peut être pivoté.
 * 
 * La classe fournit des méthodes pour : - accéder aux couleurs des tuiles, -
 * modifier leur orientation ou position, - obtenir les coordonnées occupées,
 */
public class Chromino {
	private List<CoordAndTile> tileTypesWithCoordinate = new ArrayList<>();
	private Orientation orientation = Orientation.UP;

	/**
	 * Ce constructeur va permettre la construction d'un chromino grace a 3 tilType
	 * 
	 * @param part1 Le tileType de la première case
	 * @param part2 Le tileType de la deuxieme case
	 * @param part3 Le tileType de la troisième case
	 */
	public Chromino(TileType part1, TileType part2, TileType part3) {
		this.tileTypesWithCoordinate.add(new CoordAndTile(new Coordinate(-1, 0), part1));
		this.tileTypesWithCoordinate.add(new CoordAndTile(new Coordinate(0, 0), part2));
		this.tileTypesWithCoordinate.add(new CoordAndTile(new Coordinate(1, 0), part3));
	}

	/**
	 * Cette méthode va faire faire au chromino une rotation de 90 degrés
	 */
	public void rotate90Degrees() {
		orientation = orientation.rotate90CounterClockwise();
		updateCoordinates(getCenter());
	}

	/**
	 * Cette méthode va calculer les coordonnées des extrèmités du Chromino (car le
	 * centre ne change pas de coordonée en fonction de l'orientation)
	 * 
	 * @param centre La coordonnée du centre du chromino qu'on veut calculer les
	 *               extrèmités
	 * @return Une List des 3 Coordinate du chromino
	 * 
	 */
	private List<Coordinate> updateCoordinates(Coordinate centre) {
		List<CoordAndTile> newTileTypesWithCoordinate = List.of(
				new CoordAndTile(centre.move(orientation, -1), getPart1()), new CoordAndTile(centre, getPart2()),
				new CoordAndTile(centre.move(orientation, 1), getPart3()));
		this.tileTypesWithCoordinate = newTileTypesWithCoordinate;
		return get3Coordinates();
	}

	/**
	 * Retourne la couleur de la premiere part du Chromino
	 * 
	 * @return part1 la premiere part du chromino
	 */
	public TileType getPart1() {
		return tileTypesWithCoordinate.get(0).getTileType();
	}

	/**
	 * Retourne la couleur de la deuxieme part du Chromino
	 * 
	 * @return part2 la deuxieme part du chromino
	 */
	public TileType getPart2() {
		return tileTypesWithCoordinate.get(1).getTileType();
	}

	/**
	 * Retourne la couleur de la troisieme part du Chromino
	 * 
	 * @return part3 la troisième part du chromino
	 */
	public TileType getPart3() {
		return tileTypesWithCoordinate.get(2).getTileType();
	}

	/**
	 * Cette méthode va comparer si un Chromino est egal a un autre objet quel que
	 * soit le sens car on va le retourner pour vérifier aussi.
	 * 
	 * @return true si les deux objets sont egal / false si les deux objtes sont
	 *         différents ou null
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		Chromino chromino = (Chromino) obj;

		return (getPart1() == chromino.getPart1() && getPart2() == chromino.getPart2()
				&& getPart3() == chromino.getPart3())
				|| (getPart1() == chromino.getPart3() && getPart2() == chromino.getPart2()
						&& getPart3() == chromino.getPart1());
	}

	/**
	 * Cette méthode va retourner un code de hashage d'un chromino en fonction de
	 * ses couleurs inversée (pour vérifier son unicité dans les deux sens)
	 * 
	 * @return code de hashage d'un chromino
	 */
	@Override
	public int hashCode() {
		TileType part1 = getPart1();
		TileType part3 = getPart3();
		TileType minExtremity = part1.ordinal() < part3.ordinal() ? part1 : part3;
		TileType maxExtremity = part1.ordinal() >= part3.ordinal() ? part1 : part3;
		return Objects.hash(minExtremity, getPart2(), maxExtremity);
	}

	/**
	 * Cette méthode retourne un les 3 couleurs d'un chromino dans l'ordre sous
	 * forme de String
	 * 
	 * @return chaine de caractère des 3 couleurs du chromino
	 */
	@Override
	public String toString() {
		return String.format("%s, %s, %s", getPart1(), getPart2(), getPart3());
	}

	/**
	 * Cette méthode va setter des nouvelles coordonées a un chromino
	 * 
	 * @param dr nouvelle position ligne
	 * @param dc nouvelle position collonne
	 */
	private void setCoords(int dr, int dc) {
		updateCoordinates(new Coordinate(dr, dc));

	}

	/**
	 * Cette méthode va retourner une copie défensive du tableau de CoordAndTile du
	 * chromino
	 * 
	 * @return List<CoordAndTile> une copie défensive du tableau de CoordAndTile du
	 *         chromino
	 */
	public List<CoordAndTile> getCoordAndTiles() {
		return new ArrayList<>(this.tileTypesWithCoordinate);
	}

	/**
	 * Cette méthode va retourner les 3 coordonnées occupée par les 3 tiles du
	 * chromino
	 * 
	 * @return List<Coordinate> les 3 coordonnées occupée par les 3 tiles du
	 *         chromino
	 */
	public List<Coordinate> get3Coordinates() {
		List<Coordinate> coord3 = new ArrayList<>();
		this.getCoordAndTiles().forEach(coordAndTile -> {
			coord3.add(coordAndTile.getCoord());
		});
		return coord3;
	}

	/**
	 * Cette méthode va nous permettre de retourner la coordonée de la tile centrale
	 * du chromino
	 * 
	 * @return la coordonée de la tile centrale du chromino
	 */
	private Coordinate getCenter() {
		return tileTypesWithCoordinate.get(1).getCoord();
	}

	/**
	 * Cette méthode retourne la valeur d'un chromino.
	 * 
	 * @return Si le chromino est cameleon il vaut d'office 3 points. Si il est
	 *         normal il vaut
	 */
	public int getValue() {
		Set<TileType> couleur = new HashSet<>();
		for (CoordAndTile tileType : tileTypesWithCoordinate) {
			couleur.add(tileType.getTileType());
		}
		return couleur.contains(TileType.CAMELEON) ? 3 : couleur.size();
	}

	/**
	 * Déplace le active chromino a une direction donnée
	 * 
	 * @param dr deltaRow décalle en ligne
	 * @param dc deltaCol décalle en Collone
	 */
	public void move(int dr, int dc) {

		Coordinate currentCenter = this.getCoordAndTiles().get(1).getCoord();
		int newRow = currentCenter.getRow() + dr;
		int newCol = currentCenter.getCol() + dc;
		this.setCoords(newRow, newCol);
	}
	
	/**
	 * Cette méthode retourne la TileType d'un chromino sous forme d'un string
	 * 
	 * @param i la position du tileType qu'on veut en string
	 * @return un String du tiltype d'un chromino
	 */
	public String getChrominoPartToString(int i) {
		switch (i) {
		case 0: return tileTypesWithCoordinate.get(0).getTileType().toString();
		case 1: return tileTypesWithCoordinate.get(1).getTileType().toString();
		case 2: return tileTypesWithCoordinate.get(2).getTileType().toString();
		default: throw new IndexOutOfBoundsException("Index must be 0, 1, or 2");
	}
		
	}
}
