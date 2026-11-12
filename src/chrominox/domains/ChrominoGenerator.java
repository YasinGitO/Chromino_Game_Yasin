package chrominox.domains;

import java.util.List;

/**
 * Décrit comment générer une liste de chrominos.
 * <p>
 * Nous avons choisi une liste pour préserver la position occupée par chaque chrominos.
 * </p>
 * <p>
 * Notre implémentation fournit une liste de chromino dans un ordre précis.
 * </p>
 * <p>
 * Vous devrez fournir une second implémentation pour générer les chrominos dans un ordre aléatoire.
 * </p> 
 * */
public interface ChrominoGenerator {
	/**
	 * Retourne une liste de 80 nouveaux chrominos.
	 * 
	 * CTT:
	 * Pour générer les 75 Chrominos:
	 * Au niveau des données,
	 * - on a un N pour les 5 couleurs pososible de la liste de TileType.
	 * - On génère toutes les combinaison de 3 couleur avec répètition et un ordre significatif
	 * - A chaque fois on crée un nouveau Chromino O(1)
	 * -On a 3 boucles imbriqués sur N éléments (5 elements) donc O(N^3)
	 * 
	 * 
	 * Pour générer les 5 Chrominos Cameleons:
	 * 
	 * - Cette boucle ne garenti pas de générer les 5 chrominos du premier coup, car on 
	 *   utilise la methode generateCameleon qui appele getRandomColor 2 fois O(1)
	 *   et cette méthode se base sur l'Aléatoire pour obtenir des chrominos .
	 * 
	 * - Le nombre total de combinaison possible est de 5 * 5 = 25 puisque nous ne changeont
	 *   jamais de milieu il y a juste 2 valeurs
	 * - Vu qu'il y a 25 combinaison possible et que nous n'en prennons que 5 on a O(1)
	 *  
	 * 
	 * FUSION ET MELANGE (shuffle et addAll):
	 * 
	 * - Dans le AddAll qu'on va ajouter a une List de 80 Chrominos on aura O(80)
	 *   Donc CTT totale = O(1) = O(75 + 5)
	 * - shuffle O(N) donc ici aussi O(1)
	 * 
	 * CST:
	 * 
	 * On a une List de 80 Chrominos (objets) O(N) = O(80) Donc O(1)
	 * La CST est de O(1)
	 * 
	 * 
	 * Postcondition de la génération des chrominos:
	 * Pour générer les 80 chrominos on va séparer cela en 2 etapes. 
	 * Les chrominos basique et les chrominos cameleon.
	 * 
	 * les 75 chrominos basique:
	 * Pour générer les 75 couleurs on a une restriction, Le chromino doit etre unique dans les deux sens.
	 * En ayant que 3 cases et 5 couleurs le choix est donc Limité et on ne peux en générer que 75 .
	 * Si on calcule: - 5 chrominos qui auront trois fois la meme couleur  
	 * 				  - Quand on veut que deux fois la même couleur dans le même chromino on fera donc
	 * 					5 * 4 et on va / 2 car on va éliminer toutes les possibilités de quand on va le retourner
	 * 					ensuite on va le multiplier par 3 car 3 permutations sons valide. donc 30
	 * 				  - Quand on veux 3 couleurs différentes on va garder la même couleur en premier et on va alterner les deux 
	 * 					autres couleurs jusqua avoir fait les combinaisons possible en enlevant la possibilité d'avoir 2 fois la même couleur
	 * 					et on passe a la couleur suivante et ainsi de suite donc 5 * 2 pour les autes couleurs et on multiplie par 4
	 * 					pour toutes les possibilités. donc 40
	 * 
	 * Si on additionne les 3 cela fait 40 + 30 + 5 = 75
	 * 
	 * 
	 * */
	List<Chromino> generateChrominoes();
	
}
