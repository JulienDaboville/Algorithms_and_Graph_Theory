def non_colories(gphe, etiq):
    # Retourne la liste des sommets non coloriés
    return [s for s in range(len(etiq)) if etiq[s] == -1]


# Exemple d'utilisation
gphe = [
    [False, True, True, False],  # Matrice d'adjacence pour un graphe
    [True, False, False, True],
    [True, False, False, False],
    [False, True, False, False],
]
etiq = [-1, 0, -1, -1]  # -1 indique qu'un sommet n'est pas encore colorié

# Liste des sommets non coloriés
print(non_colories(gphe, etiq))
