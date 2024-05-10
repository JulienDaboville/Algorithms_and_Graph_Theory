def tri_degre(graph):
    """
    Trie les sommets d'un graphe dans l'ordre décroissant de leurs degrés.

    :param graph: Un dictionnaire représentant le graphe sous forme de liste d'adjacence.
    :return: Une liste de sommets triés dans l'ordre décroissant de leurs degrés.
    """
    # Calculer le degré de chaque sommet (nombre de voisins)
    degrees = {sommet: len(voisins) for sommet, voisins in graph.items()}

    # Trier les sommets en fonction de leurs degrés dans l'ordre décroissant
    sommets_tries = sorted(degrees, key=degrees.get, reverse=True)

    return sommets_tries


def welsh_powell(graph):
    """
    Implémenter l'algorithme de coloration de Welsh-Powell.

    :param graph: Un dictionnaire représentant le graphe sous forme de liste d'adjacence.
    :return: Un dictionnaire où les clés sont les sommets et les valeurs sont les couleurs assignées.
    """
    # Obtenir les sommets triés par degré dans l'ordre décroissant
    sommets_tries = tri_degre(graph)

    # Initialiser le dictionnaire de résultat avec tous les sommets non colorés
    coloration = {sommet: -1 for sommet in graph}

    # Initialiser l'indice de couleur
    couleur = 0

    # Colorer les sommets dans l'ordre des sommets triés
    for sommet in sommets_tries:
        if coloration[sommet] == -1:
            # Assigner une nouvelle couleur au sommet actuel
            coloration[sommet] = couleur
            # Parcourir les sommets restants
            for autre in sommets_tries:
                # Si l'autre sommet n'est pas coloré et n'est pas voisin du sommet actuel
                if coloration[autre] == -1 and autre not in graph[sommet]:
                    # Vérifier si aucun voisin de 'autre' n'a la même couleur
                    if not any(
                        coloration[voisin] == couleur for voisin in graph[autre]
                    ):
                        coloration[autre] = couleur
            # Passer à la couleur suivante
            couleur += 1

    return coloration


# Exemple de graphe
graphe = {
    0: [4, 5, 6],
    1: [8, 7, 6],
    2: [5, 8, 9],
    3: [4, 7, 9],
    4: [0, 3, 8],
    5: [0, 2, 7],
    6: [0, 1, 9],
    7: [1, 3, 5],
    8: [1, 2, 4],
    9: [2, 3, 6],
}

# Tester l'algorithme de Welsh-Powell
coloration_welsh_powell = welsh_powell(graphe)
print(coloration_welsh_powell)
