import math


class Matrice:
    def __init__(self, matrice_adjacence):
        self.matrice_adjacence = matrice_adjacence
        self.V = len(matrice_adjacence)

    def coloration_deux_couleurs(self, sommets, c):
        couleurs = [-1] * self.V
        for v in sommets:
            if couleurs[v] == -1:
                couleurs[v] = c
                for i, connecte in enumerate(self.matrice_adjacence[v]):
                    if connecte and i in sommets:
                        couleurs[i] = c + 1 if couleurs[i] == -1 else couleurs[i]
        return couleurs, c + 2

    def coloration_wigderson(self):
        c = 0
        couleurs = [-1] * self.V

        for s in range(self.V):
            if couleurs[s] == -1 and sum(self.matrice_adjacence[s]) >= math.sqrt(
                self.V
            ):
                voisins = [
                    i
                    for i, connecte in enumerate(self.matrice_adjacence[s])
                    if connecte and couleurs[i] == -1
                ]
                couleurs_sous_graphe, nouveau_c = self.coloration_deux_couleurs(
                    voisins, c
                )
                c = nouveau_c
                for v in voisins:
                    couleurs[v] = couleurs_sous_graphe[v]

        # Coloration gloutonne pour les sommets restants
        for v in range(self.V):
            if couleurs[v] == -1:
                disponibles = [True] * self.V
                for i, connecte in enumerate(self.matrice_adjacence[v]):
                    if connecte and couleurs[i] != -1:
                        disponibles[couleurs[i]] = False
                cr = 0
                while cr < self.V and not disponibles[cr]:
                    cr += 1
                couleurs[v] = cr

        return couleurs


matrice_adjacence1 = [
    [False, True, True, False, False, False],
    [True, False, True, True, False, False],
    [True, True, False, True, False, False],
    [False, True, True, False, True, True],
    [False, False, False, True, False, True],
    [False, False, False, True, True, False],
]

matrice_adjacence2 = [
    [False, False, False, False, True, True, True, False, False, False],
    [False, False, False, False, False, False, True, True, True, False],
    [False, False, False, False, False, True, False, False, True, True],
    [False, False, False, False, True, False, False, True, False, True],
    [True, False, False, True, False, False, False, False, True, False],
    [True, False, True, False, False, False, True, False, False, False],
    [True, False, False, False, False, True, False, False, False, True],
    [False, True, False, True, False, True, False, False, False, False],
    [False, True, True, False, True, False, False, False, False, False],
    [False, False, True, True, False, False, True, False, False, False],
]


matrice_graphique = Matrice(matrice_adjacence1)
couleurs_graphique1 = matrice_graphique.coloration_wigderson()
print("coloration graphe1 :", couleurs_graphique1)

matrice_graphique = Matrice(matrice_adjacence2)
couleurs_graphique2 = matrice_graphique.coloration_wigderson()

print("coloration graphe2 :", couleurs_graphique2)
