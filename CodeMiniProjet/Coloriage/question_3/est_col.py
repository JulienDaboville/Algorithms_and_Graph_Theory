def est_col(gphe, etiq):
    # Vérifier si la taille de l'étiquetage est inférieure au nombre de sommets
    if len(etiq) < len(gphe):
        return False

    # Parcourir chaque sommet et vérifier le coloriage
    for sommet in gphe:
        if sommet not in etiq:
            # Si un sommet n'est pas colorié, renvoyer False
            return False
        couleur_sommet = etiq[sommet]
        for voisin in gphe[sommet]:
            if voisin in etiq and etiq[voisin] == couleur_sommet:
                # Si un voisin a la même couleur, renvoyer False
                return False

    # Si aucun conflit n'est trouvé, le coloriage est valide
    return True


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


etiq = {
    0: "rouge",
    1: "bleu",
    2: "vert",
    3: "rouge",
    4: "vert",
    5: "bleu",
    6: "vert",
    7: "vert",
    8: "rouge",
    9: "bleu",
}

print(est_col(gphe, etiq))
