def min_couleur_possible(gphe, etiq, s):
    n = len(gphe)  # Le nombre de sommets dans le graphe
    couleur_utilisee = [False] * n  # Un tableau pour suivre les couleurs utilisées

    # Marquer les couleurs des voisins de s
    for voisin in gphe[s]:
        if etiq[voisin] != -1:  # Si le voisin a une couleur attribuée
            couleur_utilisee[etiq[voisin]] = True

    # Trouver la plus petite couleur non utilisée
    for couleur in range(n):
        if not couleur_utilisee[couleur]:
            return couleur

    # Si toutes les couleurs jusqu'à n-1 sont utilisées, renvoyer n
    return n


# Exemple d'utilisation
gphe = {0: [1, 2], 1: [0, 2], 2: [0, 1]}
etiq = [-1, 0, 1]  # Initialement, aucun sommet n'a de couleur
s = 0  # On veut trouver la plus petite couleur pour le sommet 0
print(min_couleur_possible(gphe, etiq, s))
