package tp9;


import java.util.*;

public class ComparaisonDePerformances {
    public static void main(String[] args) {
        // Création d'un ensemble d'entiers aléatoires
        List<Integer> nombresAleatoires = new ArrayList<>();
        Random aleatoire = new Random();
        int numElements = 1000000; // Nombre d'éléments dans l'ensemble

        for (int i = 0; i < numElements; i++) {
            nombresAleatoires.add(aleatoire.nextInt(1000000)); // Nombres aléatoires de 0 à 999999
        }

        // Utilisation de Quicksort pour trier l'ensemble
        long heureDebut = System.currentTimeMillis();
        Collections.sort(nombresAleatoires);
        long heureFin = System.currentTimeMillis();

        System.out.println("Tri avec Quicksort a pris " + (heureFin - heureDebut) + " millisecondes.");

        // Utilisation de PriorityQueue pour extraire le minimum
        PriorityQueue<Integer> tasMin = new PriorityQueue<>(nombresAleatoires);
        heureDebut = System.currentTimeMillis();
        while (!tasMin.isEmpty()) {
            tasMin.poll(); // Extrait le minimum à chaque itération
        }
        heureFin = System.currentTimeMillis();

        System.out.println("Extraction du minimum avec PriorityQueue a pris " + (heureFin - heureDebut) + " millisecondes.");

        // Utilisation de PriorityQueue avec comparateur inversé pour extraire le maximum
        PriorityQueue<Integer> tasMax = new PriorityQueue<>(Collections.reverseOrder());
        tasMax.addAll(nombresAleatoires);
        heureDebut = System.currentTimeMillis();
        while (!tasMax.isEmpty()) {
            tasMax.poll(); // Extrait le maximum à chaque itération
        }
        heureFin = System.currentTimeMillis();

        System.out.println("Extraction du maximum avec PriorityQueue (comparateur inversé) a pris " + (heureFin - heureDebut) + " millisecondes.");
    }
}