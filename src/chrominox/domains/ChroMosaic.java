package chrominox.domains;

import java.util.*;


/**
 * Cette classe représente la mosaïque du jeu Chrominox.
 * Elle contient la logique de placement des chrominos, de vérification des contacts valides,
 * et de calcul du score associé à un placement.
 *
 * IT-3-Q1 : CONDITIONS SUR LES POINTS REMPORTÉS PAR UN JOUEUR LORS DU PLACEMENT D'UN CHROMINO
 *
 *
 *  Lorsqu’un joueur place un chromino, il remporte des points de la manière suivante :
 *    Points de base : valeur intrinsèque du chromino posé :
 *       - 1 si les 3 couleurs sont identiques
 *       - 2 si deux couleurs sont présentes
 *       - 3 si trois couleurs différentes OU chromino caméléon
 *
 *   Points bonus : pour chaque tile en contact avec une tile du chromino posé,
 *     si la couleur (ou "C") est compatible, on ajoute la valeur du chromino en contact.
 *
 *  Valeur minimale :
 *   - 4 points 
 *   Explications : 
 *   
 *     ||R|R|R||
 *   ||y|r|r||
 *   
 *   on place un chromino qui a une valeur de 2 (on ne peut pas placer un chromino ayant 
 *   une valeur de 1 adjacent à 1 chromino d'une valeur de 1 puisque tous les chrominos sont uniques). 
 *   il doit y avoir au min 2 points de contacs avec un chromino de la mosaique.
 *   S'il est placé en contact avec un chromino qui a une valeur minimale de 1, 
 *   et qu'il a le nombre de contact minimal (qui est de 2), 
 *   alors le nombre de points minimun que le jour aura se calcule 
 *   2 -> points de contacts * 1 ->la valeur du chromino contacté + 2-> la valeur du chromino posé = 4
 *
 *	Valeur maximale :
 *   - 27 points
 *
 *	Explications :		
 *	
 *   - Le chromino a potentiellement 8 points de contact (si entouré complètement),
 *     et chaque contact peut être avec un chromino de valeur 3 :
 *     → 3 (chromino placé) + 8 × 3 (voisins) = 27 points
 *     
 *     exemples :
 *     
 * 		      ||R|Y|B||
 *     ||G|Y|R||r|c|b||B|G|Y||
 * 			  ||R|G|B||
 *     
 *  Ceci est un maximum théorique, mais dans une partie réelle cela semble peut probable 
 *  d'avoir déjà 8 chrominos (tous avec une valeur de 3 points) positionnés 
 *  de manière à ce que l'on puisse mettre un chromino d'une valeur de 3 avec 8 côtés adjacents.
 *  
 * IT-3-Q2 : COMPLEXITÉ TEMPORELLE THÉORIQUE DU CALCUL DE SCORE
 *
 * Notations :
 *   - C = nombre de tile d’un chromino (toujours 3)
 *   - A = nombre de directions adjacentes par tile (haut, bas, gauche, droite = 4)
 *   - V = nombre total de chrominos déjà placés dans la mosaïque
 *
 * Description :
 *   - Pour chaque tile (C = 3), on génère jusqu’à 4 coordonnées adjacentes (C × A = 12)
 *   - Pour chacune de ces coordonnées, on effectue un containsKey (présence d’un chromino)
 *   - Si un chromino est trouvé, on récupère sa valeur (méthode `getValeur()`) et on ajoute son score
 *   - Aucune boucle sur tous les chrominos n’est requise : seuls les 12 voisins directs sont concernés
 *
 *  Toutes les opérations de vérification (containsKey, get, etc.) sont en **O(1)** avec HashMap
 *
 * Complexité globale du calcul du score :
 *   - Générer les coordonnées adjacentes : O(C × A) = O(12)
 *   - Accès et comparaison : max 12 × O(1)
 *   - Total : **O(1)** — complexité constante, indépendante du nombre total de chrominos
 *
 * Justification :
 *   - La structure de la mosaïque permet un accès direct par coordonnées
 *   - Le nombre de tile et d’adjacents est fixe et très faible
 *
 *
 *
 * IT-3-Q3 : CHOIX DE COLLECTION POUR LES VALEURS DES CHROMINOS
 *
 * Interface choisie : List<Chromino>
 * Implémentation : ArrayList<Chromino>
 *
 * Justification :
 *   - Taille de la liste fixée (~80 chrominos au total, dont 75 classiques + 5 caméléons)
 *   - Accès rapide en lecture et itération → O(1) pour get(index), O(1) amorti pour add
 *   - Nécessité fréquente de parcourir la main d’un joueur ou le sac → ArrayList est optimale
 *   - Pas besoin de suppression arbitraire fréquente, donc LinkedList inutile
 *   
 * Justification :
 * Pour calculer les points d’un chromino posé, il faut connaître la valeur des chrominos adjacents,
 * déjà placés sur la mosaïque. Ces chrominos sont stockés dans une collection permettant :
 * - un parcours séquentiel complet (pour comparer les coordonnées avec celles du chromino joué),
 * - un ajout régulier lors des placements,
 * - un accès éventuel à un index précis.
 *
 *  Opérations principales :
 * - iteration (for-each) pour détecter les chrominos adjacents → O(n)
 * - add(Chromino) à la fin de la liste après chaque pose → O(1) 
 * - get(i) pour tester un chromino à un indice donné → O(1)
 *
 *Choix de structure :
 * Le nombre total de chrominos en jeu est borné (80), ce qui rend le coût de parcours négligeable.
 * Aucune suppression n'est requise, donc une LinkedList serait inefficace (O(n) en accès indexé).
 *
 *Conclusion :
 * ArrayList<Chromino> est la meilleure option pour représenter les chrominos placés sur la mosaïque,
 * en permettant un accès rapide, une itération performante et un coût mémoire minimal.
 *
 *
 * 
 * IT-3-Q4 : CHOIX DE COLLECTION POUR LA MOSAÏQUE
 *
 *
 * Interface choisie : Map<Coordinate, Tile>
 * Implémentation : HashMap<Coordinate, Tile>
 *
 * Justification :
 * J'utilise la map pour :
 * - Ajouter un Chromino à la map dans :
 * 		- le constrcuteur, 
 * 		- la fonction placeChromino
 *   en utilisant la méthode public V put(K key, V value) de  public interface Map<K, V>
 * - Récupérer un Chromino de la map dans :
 * 		la fonction getTileTypeFromCoordinate 
 * 		get le tileType depuis une coordinate
 * 	 En utilisant la méthode V get(Object key) de public interface Map<K, V>
 * - Voir si une position est libre dans :
 * 		private boolean isPositionFree(Coordinate pos) 
 *   En utilisant la fonction  boolean containsKey(Object key); de public interface Map<K, V>
 * - Retourner une list des coordonnées dans:
 * 	  la fonction public List<Coordinate> getOccupiedCoordinates()
 *  En utilisant la fonction  Set<K> keySet(); de public interface Map<K, V>
 *  
 *  
 *  En utilisant une map, toutes les opérations essentielles d'accès aux cases de la mosaïque peuvent se faire rapidement:
 *  
 *     * containsKey(coord) pour vérifier si une case est occupée
 *     * get(coord) pour accéder à une tuile
 *     * put(coord, tile) pour placer un chromino
 *    
 *    Et pour ajouter les chrominos 
 *     
 *   - Toutes ces opérations sont en **O(1)** avec HashMap
 *   - L’ordre des chrominos n’a aucune importance → pas besoin de TreeMap
 *   - Grille potentiellement infinie → un tableau (2D array) est inadapté
 *
 * Conclusion :
 *   - HashMap est la solution la plus performante, simple et appropriée
 *   - Elle répond exactement aux besoins fonctionnels du modèle mosaïque
 *   
 */
public class ChroMosaic {
	
	private static final int CONTACT_INVALIDE = -1;
	private static final int MIN_CONTACTS = 2;

	private final Map<Coordinate, TileType> placed = new HashMap<>();
	private final List<Chromino> placedChrominos = new ArrayList<>();


	/**
	 * Ce constructeur construit la mosaique en y placant le premier chromino Cameleon
	 * 
	 * @param cameleon chromino cameleon qu'on veut placer sur la mosaique
	 */
	public ChroMosaic(Chromino cameleon) {
		cameleon.rotate90Degrees();
		for (CoordAndTile ct : cameleon.getCoordAndTiles()) {
			placed.put(ct.getCoord(), ct.getTileType());
		}
		placedChrominos.add(cameleon);
	}

	/**
	 * Cette méthode va placer un chromino sur la mosaique mais ne tient compte d'aucune regle de pose
	 * 
	 * @param chromino qu'on veut placer sur la mosaique
	 */
	public void placeChromino(Chromino chromino) {
		for (CoordAndTile ct : chromino.getCoordAndTiles()) {
			placed.put(ct.getCoord(), ct.getTileType());
		}
		placedChrominos.add(chromino);
	}
	
	/**
	 * Cette méthode va nous retourner le tiltype d'une coordinate en paramètre
	 * 
	 * @param coord de laquelle on veut le tileType
	 * @return le tileType de la coordonnée
	 */
	public TileType getTileTypeFromCoordinate(Coordinate coord) {
		return placed.get(coord);
	}

	/**
	 * Cette méthode va nous retourner le tiltype d'une coordinate en position row et col
	 * 
	 * @param row la ligne de la coordonée
	 * @param col la collone de la coordonée
	 * @return le tileType de la position
	 */
	public TileType getTileTypeAt(int row, int col) {
		return placed.get(new Coordinate(row, col));
	}


	/**
	 * Cette méthode va nous retourner une list de coordinate de toute les cases occupée
	 * 
	 * @return une list de coordinate de toute les cases occupée
	 */
	public List<Coordinate> getOccupiedCoordinates() {
		return new ArrayList<>(placed.keySet());
	}

	/**
	 * Cette méthode va nous dire si une position a une coordonée est libre ou non
	 * 
	 * @param pos position qu'on veut verifier
	 * @return true si la position est libre/ false si elle est occupée
	 */
	private boolean isPositionFree(Coordinate pos) {
		return !placed.containsKey(pos);
	}

	
	
	/**
	 * Cette méthode essaye de placer un chromino sur la mosaique,
	 * 
	 * @param chromino que l'on veut placer sur la mosaique
	 * @return un PlacementResult qui indique le succes ou l'echec de l'operation avec un message
	
	 */
	public PlacementResult tryPlace(Chromino chromino) {
		//1. D'abord on vérifie que les positions des 3 tiles du chromino sont libre
		if (!allPositionsFree(chromino)) {
			return PlacementResult.failure("Une position est déjà occupée.");
		}

		//2. Ensuite on compte le nombre de contacts valide qu'il y a entre les tiles du chromino
		// (au moins 2 contacts sont requis)
		int validContacts = countValidContacts(chromino);
		if (validContacts == CONTACT_INVALIDE) {
			return PlacementResult.failure("Contact avec une couleur incompatible.");
		}
		if (validContacts < MIN_CONTACTS) {
			return PlacementResult.failure("Le chromino doit être en contact avec au moins deux tuiles.");
		}

		//3. On place le chromino avec succes
		placeChromino(chromino);
		return PlacementResult.success();
	}

	/**
	 * Cette méthode vérifie que toutes les coordonées d'un chromino sont libre
	 * 
	 * @param chromino auquel on veut voir si les positions sont libres
	 * @return false si les une des 3 pose est occupée/ true si elles sont libres
	 */
	private boolean allPositionsFree(Chromino chromino) {
		for (CoordAndTile ct : chromino.getCoordAndTiles()) {
			if (!isPositionFree(ct.getCoord())) {
				return false;
			}
		}
		return true;
	}

	 
	/**
	 * Cette méthode va calculer le nombre de contacts valide entre le chromino et la mosaique
	 * 
	 * @param chromino qu'on veut placer sur la mosaique
	 * @return -1 si l'endroit ou on veut le poser est invalide/ totalValidContacts avec le nombre de conatcts valide si on peut le poser
	 */
	private int countValidContacts(Chromino chromino) {
		int totalValidContacts = 0;
	    for (CoordAndTile ct : chromino.getCoordAndTiles()) {
	        if (!isValidTilePlacement(ct)) {
	            return -1;
	        }
	        totalValidContacts += countCompatibleNeighbors(ct);
	    }
	    return totalValidContacts;
	}
	
	private int countCompatibleNeighbors(CoordAndTile ct) {
	    int count = 0;
	    for (Coordinate adj : ct.getAdjacentCoordinates()) {
	        TileType neighbor = placed.get(adj);
	        if (neighbor != null && isCompatible(ct.getTileType(), neighbor)) {
	            count++;
	        }
	    }
	    return count;
	}
	
	
	/**
	 * Cette méthode vérifie qu'une tile est placable
	 * 
	 * @param ct Un CoordAndTile qui va permettre de vérifier celui d'a coté
	 * @return false si elle touche rien/ true si elle est valide/compatible
	 */
	private boolean isValidTilePlacement(CoordAndTile ct) {
		for (Coordinate adj : ct.getAdjacentCoordinates()) {
	        TileType neighbor = placed.get(adj);
	        if (neighbor != null && !isCompatible(ct.getTileType(), neighbor)) {
	            return false;
	        }
	    }
	    return true;
	}

	
//	/**
//	 * Cette méthode vérifie si une tile a un voisin compatible
//	 * 
//	 * @param ct la CoordAndTile a laquelle on veut voir ses voisins
//	 * @return true si le voisin de la tile est compatble/ false si elle ne l'est pas
//	 */
//	private boolean hasCompatibleNeighbor(CoordAndTile ct) {
//	    for (Coordinate adj : getAdjacentCoordinates(ct.getCoord())) {
//	        TileType neighbor = placed.get(adj);
//	        if (neighbor != null && isCompatible(ct.getTileType(), neighbor)) {
//	            return true;
//	        }
//	    }
//	    return false;
//	}

	/**
	 * Cette méthode va retourner la List qui contient tout les chrominos qui ont déja été placé
	 * 
	 * @return une copie de la list de tout les chrominos placé sur la mosaique
	 */
	public List<Chromino> getPlacedChrominoesList(){
		return new ArrayList<>(placedChrominos);
	}
	private boolean isCompatible(TileType a, TileType b) {
	    return a == TileType.CAMELEON || b == TileType.CAMELEON || a == b;
	}
	

	/**
	 * Cette méthode permet de calculer le score qu'un Joueur obtiens lors de la
	 * pose d'un chromino
	 * 
	 * @param chromino que le joueur veut poser sur la mosaique
	 * @return le score que le joueur va obtenir en posant son chromino a cet
	 *         endroit
	 */
	public int computeScore(Chromino chromino) {
		int score = chromino.getValue();
		List<Chromino> placedChrominos = getPlacedChrominoesList();

		for (Chromino other : placedChrominos) {
			if (other.equals(chromino))
				continue;

			int contacts = countContactsWithPlacedChrominoes(chromino, other);
			score += other.getValue() * contacts;
		}

		return score;
	}

	/**
	 * Compte le nombre de contacts entre deux chrominos en réutilisant
	 * getAdjacentCoordinates
	 * 
	 * @param chromino1 celui qu'on vient de placer
	 * @param chromino2 celui qui est déjà sur le plateau
	 * @return nombre de contacts adjacents entre les deux chrominos
	 */
	private int countContactsWithPlacedChrominoes(Chromino chromino1, Chromino chromino2) {
		int contacts = 0;

		for (Coordinate coord1 : chromino1.get3Coordinates()) {
			List<Coordinate> adjacents = coord1.getAdjacentCoordinates();
			for (Coordinate coord2 : chromino2.get3Coordinates()) {
				if (adjacents.contains(coord2)) {
					contacts++;
				}
			}
		}

		return contacts;
	}
	
}
