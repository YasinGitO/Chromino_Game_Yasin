package chrominox.domains;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette class va permettre de représenter le sac de pioche contenant tout les 
 * Chrominos du jeu (80 chromino en tout)
 * 
 */
public class ChroBag {
	
	/**
	 * Cette List de Chromino va stocker tout les 80 chrominos (75 normaux et 5 caméléons)
	 * Nous savons donc déja que cette List va prendre 80 places donc sa CTT sera de O(1)
	 * J'ai choisi cette Collection parce que pour accéder plus rapidement aux éléments j'utilise directement
	 * leur index donc je peux prendre le Chromino sans parcourir la List
	 * Et les interactions avec la list se font en O(1)
	 * Mais le pire des cas restera quand meme qu'on retire un élément a la place 0 car
	 * on devra décaer tout les éléments de la List d'une place O(n - index)
	 * Et le meilleur des cas sera quand on prendra le dernier élément de la List
	 * 
	 */
	private final List<Chromino> chrominoBag;
	
	/**
	 * Cette méthode va servir a initialiser le sac avec un générateur de chromino 
	 * 
	 * @param generator le générateur resposable de créer les 80 chrominos
	 */
	public ChroBag(List<Chromino> chrominoes) {
		this.chrominoBag = new ArrayList<>(chrominoes);
	}

	/**
	 * Retire  Chromino du sac de Chrominos (ChrominoBag)
	 * 
	 * @return Un chromino aléatoire du sac de chromino
	 *  
	 */
	public Chromino retirerUnSeulChromino() {
		if(chrominoBag.isEmpty()) {
			return null;
		}
		Chromino chromino = chrominoBag.get(0);
		chrominoBag.remove(0);
		return chromino;
	}
	
		
	/**
	 * Retire 'n' Chromino du sac de Chrominos (ChrominoBag)
	 * 
	 * @param n le nombre de chrominos qu'on veut retirer
	 * @return Une List avec le nombre de Chrominos retirés
	 * 
	 */
	public List<Chromino> retirerNChrominos(int n) {
		List<Chromino> chrominoRetire = new ArrayList<>();
		for(int i = 0; i < n; i++) {
			Chromino chromino = chrominoBag.get(0);
			chrominoRetire.add(chromino);
			chrominoBag.remove(0);
		}
		return chrominoRetire; 
	}
	
	
	
	
	/**
	 * Cette fonction permet de voir le premier Chromino Cameleon du ChroBag sans le retirer
	 * 
	 * @return Un Chromino cameleon
	 * 
	 */
	public Chromino seeFirstCameleon() {
		Chromino referenceCameleon = new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.GREEN);
		for(Chromino chromino : chrominoBag) {
			if(chromino.getPart2().equals(referenceCameleon.getPart2())) {
				return chromino;
			}
		}
		return referenceCameleon;
	}
	
	
	/**
	 * Cette fonction permet d'obtenir le premier Chromino Cameleon de la List
	 * 
	 * @return un Chromino Cameleon
	 * 
	 */
	public Chromino getFirstCameleon() {
		Chromino referenceCameleon = new Chromino(TileType.CYAN, TileType.CAMELEON, TileType.GREEN);
		
		for(Chromino chromino : chrominoBag) {
			if(chromino.getPart2().equals(referenceCameleon.getPart2())) {
				chrominoBag.remove(chromino);
				return chromino;
			}
		}
		return referenceCameleon;
	}


	/**
	 * Cette méthode vérifie si le bag est vide
	 * 
	 * @return true si le bag est vide/ false si l'inverse
	 */
	public boolean isEmpty() {
		return this.chrominoBag.isEmpty();
	}
	
	
	
	
}
