def glouton(gphe, num):
    n = len(gphe)  # Nombre de sommets dans le graphe
    etiq = [-1] * n  # Initialisation de l'étiquetage à -1 pour tous les sommets

    # Fonction pour trouver la plus petite couleur disponible pour un sommet donné
    def min_couleur_possible(s):
        couleur_utilisee = [False] * (n + 1)  # On a au plus d+1 couleurs
        # Marquer les couleurs utilisées par les voisins de s
        for voisin in gphe[s]:
            if etiq[voisin] != -1:
                couleur_utilisee[etiq[voisin]] = True
        # Trouver la plus petite couleur non utilisée
        for couleur in range(n + 1):
            if not couleur_utilisee[couleur]:
                return couleur

    # Appliquer l'algorithme glouton en suivant l'ordre de numérotation
    for sommet in num:
        etiq[sommet] = min_couleur_possible(sommet)

    return etiq


# Exemple d'utilisation
gphe = {
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
num1 = [1, 3, 4, 0, 2, 6, 5, 9, 8, 7]  # Un exemple d'ordre de numérotation des sommets
num2 = [0, 7, 2, 5, 4, 6, 8, 1, 3, 9]

coloriage1 = glouton(gphe, num1)
coloriage2 = glouton(gphe, num2)

print("coloriage glouton de ", num1, " :", coloriage1)
print("coloriage glouton de ", num2, " :", coloriage2)
