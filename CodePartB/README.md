# TP7 et TP8 : Trouver le plus court chemin

## Vue d'Ensemble
Les travaux pratiques TP7 et TP8 consistent en des packages Java destinés à identifier le chemin le plus court sur une carte en 2D. Ils mettent en œuvre les algorithmes de Dijkstra et A* et évaluent leur efficacité sur diverses cartographies.

## Modules

### WeightedGraph.java
Ce fichier définit une structure pour un graphe pondéré, incluant :
- Edge : Une sous-classe représentant une arête.
- Vertex : Une sous-classe symbolisant un sommet.
- Graph : Une sous-classe désignant le graphe, dotée de fonctions pour ajouter des sommets et des arêtes.

### App.java
Ce fichier principal contient la logique des algorithmes et la gestion de l'interface utilisateur. Ses éléments clés sont :
- La fonction main : Pour charger une carte depuis un fichier texte, la convertir en graphe, et initier la recherche du chemin le plus court.
Les fonctions Dijkstra et AStar : Pour déterminer le chemin entre les points de départ et d'arrivée. L'algorithme A* utilise des heuristiques, telles que Manhattan ou Euclidienne, pour optimiser la recherche de chemin.- La fonction drawBoard et la classe Board : Pour gérer l'interface utilisateur.

## Fonctionnalités
Le programme charge une carte depuis un fichier texte et la transforme en un graphe pondéré. L’utilisateur peut opter pour l'algorithme de Dijkstra ou A* afin de trouver le chemin le plus court. L'interface graphique illustre les étapes des algorithmes et le chemin sélectionné.

## Format des Cartes
Les graphes sont fournis via des fichiers texte comprenant :
- Des métadonnées, dimensions, types de terrains (temps de traversée et couleur associée).
- Une représentation visuelle des terrains.
- Les coordonnées des points de départ et d'arrivée.



## Environnement de Développement
- Java JDK 11 ou ultérieur.
- Eclipse IDE ou tout autre environnement de développement Java.
- Pour la compilation et l'exécution en ligne de commande, utilisez javac et java.



## Exécution du Programme

Pour exécuter le programme dans Eclipse :

1. Ouvrez le Projet :
   - Allez dans File > Open Projects from File System....
   - Sélectionnez le répertoire contenant le projet et cliquez sur Finish.

2. Ouvrez App.java :
   - Localisez le fichier dans l'explorateur de projet et ouvrez-le.

3. Lancez le Programme :
   - Faites un clic droit sur le fichier dans l'éditeur et sélectionnez Run As > Java Application.

4. Configurez les Arguments du Programme :
   - Allez dans Run > Run Configurations....
   - Dans l'onglet (x)= Arguments, entrez le chemin vers le fichier de carte dans Program arguments.

 

Cette addition fournit des instructions claires sur la manière d'ouvrir le projet dans Eclipse, étape essentielle avant de pouvoir exécuter App.java.

Pour lancer le programme en ligne de commande :


java -cp bin td7.App <chemin_vers_fichier_carte>


Remplacez <chemin_vers_fichier_carte> par le chemin du fichier de carte.

- Choisissez l'algorithme désiré (Dijkstra ou A*).
- Le programme calculera et affichera le e plus court chemin l.
