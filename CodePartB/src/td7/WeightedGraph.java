package td7;

import java.util.LinkedList;
import java.util.ArrayList;

// Classe d�finissant un graphe pond�r�.
public class WeightedGraph {
	// Sous-classe pour une arr�te.
	static class Edge {
		int source; // sommet 1
		int destination;// somet 2
		double weight;

		public Edge(int source, int destination, double weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
	}

	// Sous-classe pour un sommet.
	static class Vertex {
		double indivTime;
		double timeFromSource;
		double heuristic;
		Vertex prev;
		LinkedList<Edge> adjacencylist;
		int num;

		public Vertex(int num) {// Sommet
			this.indivTime = Double.POSITIVE_INFINITY;// infini
			this.timeFromSource = Double.POSITIVE_INFINITY;// infini
			this.heuristic = -1;
			this.prev = null;
			this.adjacencylist = new LinkedList<Edge>();
			this.num = num;
		}

		public void addNeighbor(Edge e) {
			this.adjacencylist.addFirst(e);
		}
	}

	// Sous-classe pour le graphe.
	static class Graph {
		ArrayList<Vertex> vertexlist; // liste des sommets
		int num_v = 0;

		Graph() {
			vertexlist = new ArrayList<Vertex>();
		}

		public void addVertex(double indivTime) {
			Vertex v = new Vertex(num_v);
			v.indivTime = indivTime;
			vertexlist.add(v); // ajout du sommet dans la liste
			num_v = num_v + 1;
			// System.out.println("Sommet ajouté : " + v.num);

		}
//Pour l'heurstique Manhattan (pas de mouveemtns diagonaux)
/*		public void addEdge(int source, int destination) {
			
			
		    double baseWeight = (vertexlist.get(source).indivTime + vertexlist.get(destination).indivTime) / 2;
		    Edge edge = new Edge(source, destination, baseWeight);
		    vertexlist.get(source).addNeighbor(edge); // Pour un graphe orienté
		}*/

		//Pour l'heurstique Division Euclidienne (mouvements diagonaux 
		public void addEdge(int source, int destination, boolean isDiagonal) {
			double baseWeight = (vertexlist.get(source).indivTime + vertexlist.get(destination).indivTime) / 2;
			double weight = isDiagonal ? baseWeight * Math.sqrt(2) : baseWeight;
			Edge edge = new Edge(source, destination, weight);
			vertexlist.get(source).addNeighbor(edge); // Pour un graphe orienté
		}

		public void printGraph() {
			for (int v = 0; v < num_v; v++) {
				System.out.println("Liste d'adjacence du sommet " + v);
				System.out.print("tête");
				for (Edge edge : vertexlist.get(v).adjacencylist) {
					System.out.print(" -> " + edge.destination + " (poids : " + edge.weight + ")");
				}
				System.out.println("\n");
			}
		}

	}

	// Test de la classe.
	public static void main(String[] args) {
/*		int vertices = 6;
		Graph graph = new Graph();// graphe oriente
		graph.addVertex(10);
		graph.addVertex(10);
		graph.addVertex(10);
		graph.addVertex(10);
		graph.addVertex(10);
		graph.addVertex(10);
		graph.addEgde(0, 1, 4);
		graph.addEgde(0, 2, 3);
		graph.addEgde(1, 3, 2);
		graph.addEgde(1, 2, 5);
		graph.addEgde(2, 3, 7);
		graph.addEgde(3, 4, 2);
		graph.addEgde(4, 0, 4);
		graph.addEgde(4, 1, 4);
		graph.addEgde(4, 5, 6);
		graph.printGraph();*/
	}
}
