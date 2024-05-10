def sous_graphe(gphe, sg):
    # Longueur du sous-graphe
    taille_sg = len(sg)
    # Création d'une matrice d'adjacence pour le sous-graphe
    sous_gphe = [[False] * taille_sg for _ in range(taille_sg)]

    # Remplissage de la matrice d'adjacence du sous-graphe
    for i in range(taille_sg):
        for j in range(taille_sg):
            if i != j:  # Pas de boucle sur un même sommet
                # Vérification de l'existence d'une arête dans le graphe original
                sous_gphe[i][j] = gphe[sg[i]][sg[j]]

    return sous_gphe


if __name__ == "__main__":
    # Exemple d'utilisation
    gphe = [
        [False, True, True, False, False, False],
        [True, False, True, True, False, False],
        [True, True, False, True, False, False],
        [False, True, True, False, True, True],
        [False, False, False, True, False, True],
        [False, False, False, True, True, False],
    ]

    sg = [0, 1, 2, 3]  # Les indices des sommets du sous-graphe
    sous_gphe = sous_graphe(gphe, sg)

    # Affichage du sous-graphe
    for ligne in sous_gphe:
        print(ligne)
    pass
