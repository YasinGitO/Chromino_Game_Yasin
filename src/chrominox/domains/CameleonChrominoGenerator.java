package chrominox.domains;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Cette class sert a générer les 5 chrominos cameleons
 * 
 */
public class CameleonChrominoGenerator implements ChrominoGenerator {

	private final List<TileType> colors = List.of(TileType.CYAN, TileType.GREEN, TileType.MAGENTA, TileType.RED,
			TileType.YELLOW);

	/**
	 * Cette méthode génère 5 Chromoino caméléon
	 * 
	 * @return une list<Chromino> avec tout les chromino cameleons
	 * 
	 *         CTT: Cette boucle ne garenti pas de générer les 5 chrominos du
	 *         premier coup, car on utilise la methode generateCameleon qui appele
	 *         getRandomColor 2 fois O(1) et cette méthode se base sur l'Aléatoire
	 *         pour obtenir des chrominos .
	 * 
	 *         Le nombre total de combinaison possible est de 5 * 5 = 25 puisque
	 *         nous ne changeont jamais de milieu il y a juste 2 valeurs En moyenne
	 *         pour avoir 5 cameleons on aura O(k) essais
	 * 
	 *         Donc dans le meilleur des cas on aura: - 0(1) si on arrive a générer
	 *         tout les camaleons différents d'un coup.
	 * 
	 *         Dans le pire des cas on aura: - O(k)
	 * 
	 *         CST: -Chaque Chromino est O(1) -La list contient 5 objets O(1)
	 * 
	 *         La CST est de O(1)
	 */
	@Override
	public List<Chromino> generateChrominoes() {
		Set<Chromino> cameleonChrominoes = new HashSet<>();
		int size = colors.size();
		for (int i = 0; i < 5; i++) {
			TileType c1 = colors.get(i);
			TileType c2 = colors.get((i + 1) % size);
			Chromino chromino = new Chromino(c1, TileType.CAMELEON, c2);
			cameleonChrominoes.add(chromino);
		}
		return new ArrayList<>(cameleonChrominoes);
	}

}
