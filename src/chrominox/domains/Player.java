package chrominox.domains;

import java.util.*;

/**
 * Représente un joueur dans le jeu Chromino.
 * 
 * Chaque joueur possède : - un nom unique ; - un score (nombre de points) ; -
 * une main contenant plusieurs chrominos ; - un chromino actif (celui que le
 * joueur est en train de manipuler pour le placer sur la mosaïque) ; - un état
 * indiquant s’il a déjà pioché pendant le tour courant.
 * 
 * Cette classe offre toutes les fonctionnalités nécessaires à la gestion d’un
 * joueur : - Piocher un chromino (avec règle : une seule fois par tour) ; -
 * Sélectionner, retirer ou remettre un chromino dans sa main ; - Modifier ou
 * interroger le chromino actif ; - Déplacer ou faire pivoter le chromino actif
 * ; - Accéder aux informations générales du joueur (nom, score, main).
 * 
 *
 * ITERATION 2 - q4 - Choix de collection pour les chrominos que le joueur tient
 * en main:
 * 
 * Représente un joueur dans le jeu de Chromino. Chaque joueur possède un nom,
 * un score, une main de chrominos et un chromino actif (sélectionné). Interface
 * utilisée : List<Chromino> Implémentation : ArrayList La main contient en
 * général un petit nombre de chrominos (typiquement 5 à 10) On a besoin
 * d'accéder rapidement à un chromino par son index dans l'interface graphique :
 * get(index) :O(1) On ajoute ou retire des chrominos en fin ou au milieu :
 * add() : O(1) remove(index) : O(n - index) acceptable parce que petite liste
 * La ArrayList permet de parcourir la main efficacement dans l'affichage, le
 * choix ou la vérification de validité. Elle maximise la vitesse d’accès par
 * index et reste performante dans les cas d’ajout/retrait peu fréquents
 *
 * Une LinkedList n’aurait pas été adaptée ici car elle a une complexité en O(n)
 * pour l’accès par index
 *
 */
public class Player {

	private final String name;
	private final ChroHand chrominoHand = new ChroHand();
	private Chromino activeChromino;
	private int score = 0;
	private final String color;
	
	private final PlayerTurnState turnState = new PlayerTurnState();

	/**
	 * Constructeur de Player qui prends en parametre son nom et ses points et les
	 * initialise dans les attributs de la classe
	 * 
	 * @param name   le nom du joueur
	 * @param points le nombre de points du joueur (it. 2)
	 */
	public Player(String name, String color) {
		this.name = name;
		this.score = 0;
		this.color = color;
	}

	
	public String getColor() {
		return this.color;
	}
	
	
	
	/**
	 * Cette méthode va construire la main du joueur
	 * 
	 * @param bag le bag dans lequel on veut piocher pour construire la main
	 * @param n   le nombre de Chromino qu'on veut dans la main
	 */
	public void buildHand(List<Chromino> chrominoes) {
		chrominoHand.buildHand(chrominoes);
	}

	/**
	 * Cette méthode va retourner la taille de la main
	 * 
	 * @return la taille de la hand
	 */
	public int getHandSize() {
		return chrominoHand.size();
	}

	/**
	 * Cette méthode retourne la main du joueur
	 * 
	 * @return une copie de la main du joueur
	 */
	public List<Chromino> getHandForViewOnly() {
		return chrominoHand.copyHand();
	}

	/**
	 * Cette méthode est un accesseur pour avoir le chromino actif
	 * 
	 * @return le chromino actif (activeChromino)
	 */
	public Chromino getActiveChromino() {
		return this.activeChromino;
	}

	/**
	 * Cette méthode va rotate le chromino actif de 90 degrés
	 */
	public void rotateActiveChromino90Degres() {
	    if (activeChromino != null) {
	        activeChromino.rotate90Degrees();
	    }
	}

	/**
	 * Cette méthode va déplacer un chromino a une position entrée en parametre
	 * 
	 * @param dr delta row la nouvelle position sur la ligne
	 * @param dc delta col la nouvelle position sur la collonne
	 */
	public void moveActiveChromino(int dr, int dc) {
	    if (activeChromino != null) {
	        activeChromino.move(dr, dc); 	    
	   }
	}

	/**
	 * Cette méthode nous retourne le name du Player
	 * 
	 * @return le Name du player
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Cette méthode redéfini toString pour avoir un format du genre "Joueur 1" name
	 */
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	/**
	 * Cette méthode nous permet de modifier le chromino actif avec le chromino
	 * entré en paramètre
	 * 
	 * @param chromino Chromino qu'on veut qu'il soit actif
	 */
	public void setActiveChromino(Chromino chromino) {
		this.activeChromino = chromino;
	}


	/**
	 * Cette méthode ajoute un chromino a la main du joueur
	 * 
	 * @param chromino chromino a ajouter a la hand
	 */
	public void addChrominoToHand(Chromino chromino) {
		chrominoHand.addChromino(chromino);
	}


	/**
	 * Cette méthode va être la pour réinitialiser le tour d'un joueur comme ca
	 * quand il aura pioché et que il passera son tour et que ca reviendra a lui, il
	 * pourra à nouveau piocher
	 */
	public void resetTurn() {
		turnState.reset();
	}
	
	
	/**
	 * Cette méthode va permettre de sélectionner le chromino suivant en faisant + 1 et va revenir au 0 si on arrive au dernier
	 */
	public void selectNextChromino() {
		chrominoHand.selectNextChromino();
	}

	/**
	 * Cette méthode va nous retourner l'index du chromino qu'on a choisi en parcourant la hand
	 * 
	 * @return l'index du chromino qu'on veut
	 */
	public int getSelectedChrominoIndex() {
		return chrominoHand.getSelectedChrominoIndex();
	}

	/**
	 * Cette méthode va reset l'index a 0
	 */
	public void resetSelectedChromino() {
		chrominoHand.resetSelectedChromino();
	}
	
	
	/**
	 * Cette méthode va rendre un chromino actif pour être placé sur la mosaique
	 * 
	 * @param i index du chromino a séléctionner
	 */
	public void getChrominoFromHandToActive(int i) {
		setActiveChromino(chrominoHand.getAndRemoveChrominoFromHand(i));
	}
	
	/**
	 * Cette méthode va retourner le score actuel du player
	 * 
	 * @return le score du player
	 */
	public int getScore() {
		return this.score;
	}
	
	/**
	 * Cette méthode va ajouter le nombre de points en paramètre au score du joueur
	 * 
	 * @param points qu'on veut ajouter au player
	 */
	public void addToScore(int points) {
		this.score += points;
	}


	/**
	 * Cette méthode va reset le score du joueur a 0
	 */
	public void resetScore() {
		this.score = 0;		
	}

}
