package chrominox.domains;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cette class gère tout ce qui va être en rapport avec la main du joueur comme y ajouter un chromino
 * en enlever retourner une copie de la main.
 */
public class ChroHand {

	/**
	 * J'ai choisi une List pour la main car je trouve cela optimal dans le sens ou
	 * - nous avons généralement des mains qui tournent autour des 5 - 10 chrominos
	 * donc l'accès est plutôt rapide. - nous accédons aux éléments par leur indice
	 * - lorsque nous supprimons un élément de la main la list est écalée de un
	 * élément
	 * 
	 * CTT: La CTT est de O(1) mais au fur et a mesure puisque la main va varier ca
	 * sera O(n)
	 * 
	 * CST: Ca sera un tableau dynamique contenant n chrominos donc CST = O(n)
	 */
	private final List<Chromino> hand = new ArrayList<>();
	private int selectedChrominoIndex = 0;

	/**
	 * Sélectionne le chromino suivant dans la main. revient à 0 si on est au dernier
	 */
	public void selectNextChromino() {
		if (!hand.isEmpty()) {
			selectedChrominoIndex = (selectedChrominoIndex + 1) % hand.size();
		}
	}

	/**
	 * Retourne l’index du chromino actuellement sélectionné.
	 */
	public int getSelectedChrominoIndex() {
		return selectedChrominoIndex;
	}

	/**
	 * Réinitialise l'index à 0 -> le premier.
	 */
	public void resetSelectedChromino() {
		this.selectedChrominoIndex = 0;
	}

	/**
	 * Retourne le chromino sélectionné dans la main du joueur.
	 */
	public Chromino getSelectedChromino() {
		return getChrominoAt(selectedChrominoIndex);
	}


	/**
	 * Cette méthode va retourner un chromino qui se trouve a l'index indiqué dans sa main
	 * 
	 * @param index l'index du chromino qu'on veut
	 * @return un chromino se toruvant a l'index dans la hand
	 */
	private Chromino getChrominoAt(int index) {
	    return (index >= 0 && index < hand.size()) ? hand.get(index) : null;
	}

	/**
	 * Cette méthode va compter le nombre de chromino dans la main du joueur
	 * 
	 * @return un entier avec le nombre de chromino que le joueur a dans sa main
	 */
	public int count() {
	    return hand.size();
	}

	/**
	 * Cette méthode vérifie si la hand contient un chromino en paramètre
	 * 
	 * @param chromino qu'on veut comparer a la list pour vérifier si elle est dedans
	 * @return true si le chromino est dedans/ false si il n'y est pas
	 */
	public boolean contains(Chromino chromino) {
	    return hand.contains(chromino);
	}

	/**
	 * Cette méthode va retourner une copie de la hand
	 * 
	 * @return une copie immuable de la hand en List
	 */
	public List<Chromino> copyHand() {	  
	    return Collections.unmodifiableList(new ArrayList<>(hand));
	}

	

	/**
	 * Cette méthode va construire une main de n chromino en les retirant du sac de
	 * chromino (On utilise cette méthode qu'à la création du jeu au debut donc une
	 * seule fois)
	 * 
	 * @param chrominoBag Le sac de chromino duquel on veut retirer les chromino
	 * @param n           Le nombre de Chromino qu'on veut retirer du sac pour
	 *                    ajouter a la main
	 * 
	 *                    CTT: On supprime 'n' éléments de la List avec le clear
	 *                    O(n) on ajoute 'n' éléments a la List O(n) CTT = O(n) +
	 *                    O(n) = O(n)
	 * 
	 *                    CST: On stocke n éléments Donc CST = O(n)
	 */
	public void buildHand(List<Chromino> chrominoes) {
		hand.clear();
		hand.addAll(chrominoes);
	}

	/**
	 * Cette méthode va nous permettre d'avoir un chromino sans le retirer de la
	 * main
	 * 
	 * @param i La position du chromino dans la List de la main
	 * @return Un chromino qui se trouve a l'index qu'on indique en paramètre
	 * 
	 *         CTT: Accès par index a un chromino précis donc O(1)
	 * 
	 *         CST: 0
	 */
	public Chromino getChromino(int i) {
		if (i < hand.size()) {
			return hand.get(i);
		}
		return null;
	}

	/**
	 * Cette méthode va retirer de la main le chromino a l'index indiqué
	 * 
	 * @param i La position du chromino dans la List de la main
	 * @return retire le chromino de la main
	 * 
	 *         CTT: On va retirer un élément ce qui va faire décaler tout les
	 *         éléments vers la gauche Dans le meilleur des cas, on retire le
	 *         dernier chromino de la List donc O(1) Dans le pire des Cas on retire
	 *         le tout prenier chromino donc on les décale tous O(n)
	 * 
	 *         CST: On reduit la taille on ne l'augmente pas
	 */
	public Chromino removeChrominoAt(int i) {
		if (i < hand.size()) {
			return hand.remove(i);
		}
		return null;
	}

	/**
	 * Cette méthode remove un chromino de la hand
	 * 
	 * @param chromino qu'on veut retirer
	 */
	public void removeChromino(Chromino chromino) {
		hand.remove(chromino);
	}

	/**
	 * Cette méthode va ajouter un chromino a la main
	 * 
	 * @param chromino Le chromino qu'on veut ajouter a la main
	 * 
	 *                 CTT: On ajoute un chromino a la fin de la List donc O(1)
	 * 
	 *                 CST: La List croit de une place. O(1)
	 */
	public void addChromino(Chromino chromino) {
		if (chromino != null) {
			hand.add(chromino);
		}
	}

	/**
	 * Cette méthode indique si la main est vide
	 * 
	 * @return true si la main est vide / false si la main n'est pas vide
	 * 
	 *         CTT: O(1)
	 * 
	 *         CST: 0
	 */
	public boolean isEmpty() {
		return hand.isEmpty();
	}

	/**
	 * Cette méthode va nous donner la taille de la main
	 * 
	 * @return La taille de la main
	 * 
	 *         CTT: O(1)
	 * 
	 *         CST: 0
	 */
	public int size() {
		return hand.size();
	}

	/**
	 * Cette méthode va retourner un chromino qu'il y a dans sa main a l'index indiqué et le retirer de sa list
	 * 
	 * @param i l'index du chromino qu'on veut retirer
	 * @return le chromino se situant a l'index
	 */
	public Chromino getAndRemoveChrominoFromHand(int i) {
		Chromino chro = getChrominoAt(i);
		removeChrominoAt(i);
		return chro;
	}
}
