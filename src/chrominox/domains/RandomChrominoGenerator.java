package chrominox.domains;

import java.util.*;

/**
 * Cette class utilise les class UniqueChrominoGenerator et CameleonChrominoGenerator pour générer les 80
 * chrominos et les melanger pour les renvoyer dans une list
 */
public class RandomChrominoGenerator implements ChrominoGenerator {

	private final UniqueChrominoGenerator uniqueChromino = new UniqueChrominoGenerator();
	private final CameleonChrominoGenerator cameleonChromino = new CameleonChrominoGenerator();

	/**
	 * Cette méthode génère les 75 Chrominos normal et les 5 Caméléons et les
	 * renvoie dans une List de Chromino Mélangée
	 * 
	 * @return allChrominoes Une List de Chromino contenant 80 chrominos mélangé.
	 * 
	 *         CTT: On avait déja analysé les autres generateChrominoes donc on ne
	 *         va analyser que le shuffle et addAll Dans le AddAll qu'on va ajouter
	 *         a une List de 80 Chrominos on aura O(80)
	 * 
	 *         Donc CTT totale = O(1) = O(75 + 5)
	 * 
	 *         CST: On a une List de 80 Chrominos (objets) O(N) = O(80) Donc O(1) La
	 *         CST est de O(1)
	 * 
	 * 
	 */
	@Override
	public List<Chromino> generateChrominoes() {
		List<Chromino> allChrominoes = new ArrayList<>();
		allChrominoes.addAll(uniqueChromino.generateChrominoes());
		allChrominoes.addAll(cameleonChromino.generateChrominoes());
		Collections.shuffle(allChrominoes);
		return allChrominoes;
	}
}
