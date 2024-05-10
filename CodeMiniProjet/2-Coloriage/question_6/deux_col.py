def deux_col(gphe):
    def dfs(sommet, couleur):
        if etiq[sommet] != -1:
            return etiq[sommet] == couleur
        etiq[sommet] = couleur
        for voisin in gphe[sommet]:
            if not dfs(voisin, 1 - couleur):
                return False
        return True

    etiq = [-1] * len(gphe)  # Initialisation de l'étiquetage à -1
    for sommet in range(len(gphe)):
        if etiq[sommet] == -1:  # Si le sommet n'a pas encore été visité
            if not dfs(
                sommet, 0
            ):  # Commencer le parcours en profondeur avec la couleur 0
                return None  # Le graphe n'est pas 2-coloriable

    return etiq


# Exemple d'utilisation
gphe = {0: [1, 3], 1: [0, 2], 2: [1, 3], 3: [0, 2]}
coloriage = deux_col(gphe)
print(coloriage)
