def voisins_non_colories(gphe, etiq, s):
    # Trouver tous les voisins non coloriés du sommet s
    return [t for t in range(len(gphe[s])) if gphe[s][t] and etiq[t] == -1]


def degre_non_colories(gphe, etiq, s):
    # Compter le nombre de voisins non coloriés du sommet s
    return len(voisins_non_colories(gphe, etiq, s))


if __name__ == "__main__":
    # Exemple d'utilisation
    gphe = [
        [False, True, True, False],  # Matrice d'adjacence pour un graphe
        [True, False, False, True],
        [True, False, False, False],
        [False, True, False, False],
    ]
    etiq = [-1, -1, 0, -1]  # -1 indique qu'un sommet n'est pas encore colorié

    # Liste des voisins non coloriés du sommet 1
    print(voisins_non_colories(gphe, etiq, 1))

    # Degré des voisins non coloriés du sommet 1
    print(degre_non_colories(gphe, etiq, 1))

    pass
