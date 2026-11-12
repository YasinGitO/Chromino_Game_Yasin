package chrominox.domains;

import java.util.*;

/**
 * Cette class va générer les 75 Chromino basique tous unique quel que soit leur sens (en tenant compte de la symétrie)
 */
public class UniqueChrominoGenerator implements ChrominoGenerator {

	private final List<TileType> colors = List.of(TileType.CYAN, TileType.GREEN, TileType.MAGENTA, TileType.RED,
			TileType.YELLOW);

	/**
	 * Cette fonction retourne une list des 75 Chrominos basique générés. Il n'y a
	 * aucun doublon quel que soit la combinaison de 3 couleurs parmi toutes les
	 * couleurs (5) en tennant compte de la symétrie.
	 * 
	 * @return une List de Chromino unique
	 * 
	 *         CTT: Au niveau des données, - on a un N pour les couleurs de la liste
	 *         de TileType en argument. - A chaque fois on crée un nouveau Chromino
	 *         O(1) -On appelle isUnique donc O(1) -On a 3 boucles imbriqués donc
	 *         O(N^3)
	 * 
	 *         Si on calcule le tout N * O(1) * O(1) * O(N^3) = O(N^3) La CTT de
	 *         generateChrominoes est de O(N^3)
	 * 
	 *         CST: On a 75 objets de O(1) dans une Set donc on peut dire que on a
	 *         O(75) mais la CST sera de O(N)
	 * 
	 */
	@Override
	public List<Chromino> generateChrominoes() {
		Set<Chromino> uniqueChrominoes = new HashSet<>();
		for (TileType colorPart1 : colors) {
			for (TileType colorPart2 : colors) {
				for (TileType colorPart3 : colors) {
					Chromino chromino = new Chromino(colorPart1, colorPart2, colorPart3);
						uniqueChrominoes.add(chromino);
				}
			}
		}
		return new ArrayList<>(uniqueChrominoes);
	}
 

}