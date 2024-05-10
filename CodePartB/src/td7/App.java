package td7;

//import MainApp.WeightedGraph.Edge;
//import MainApp.WeightedGraph.Graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.HashSet;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.RenderingHints;
import javax.swing.JComponent;
import javax.swing.JFrame;

import td7.WeightedGraph.Graph;
import td7.WeightedGraph.Edge;
import td7.WeightedGraph.Vertex;

//Classe pour g�rer l'affichage
class Board extends JComponent {
	private static final long serialVersionUID = 1L;
	Graph graph;
	int pixelSize;
	int ncols;
	int nlines;
	HashMap<Integer, String> colors;
	int start;
	int end;
	double max_distance;
	int current;
	LinkedList<Integer> path;

	public Board(Graph graph, int pixelSize, int ncols, int nlines, HashMap<Integer, String> colors, int start,
			int end) {
		super();
		this.graph = graph;
		this.pixelSize = pixelSize;
		this.ncols = ncols;
		this.nlines = nlines;
		this.colors = colors;
		this.start = start;
		this.end = end;
		this.max_distance = ncols * nlines;
		this.current = -1;
		this.path = null;
	}

	// Mise � jour de l'affichage
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Ugly clear of the frame
		g2.setColor(Color.cyan);
		g2.fill(new Rectangle2D.Double(0, 0, this.ncols * this.pixelSize, this.nlines * this.pixelSize));

		int num_case = 0;
		for (WeightedGraph.Vertex v : this.graph.vertexlist) {
			double type = v.indivTime;
			int i = num_case / this.ncols;
			int j = num_case % this.ncols;

			if (colors.get((int) type).equals("green"))
				g2.setPaint(Color.green);
			if (colors.get((int) type).equals("gray"))
				g2.setPaint(Color.gray);
			if (colors.get((int) type).equals("blue"))
				g2.setPaint(Color.blue);
			if (colors.get((int) type).equals("yellow"))
				g2.setPaint(Color.yellow);
			g2.fill(new Rectangle2D.Double(j * this.pixelSize, i * this.pixelSize, this.pixelSize, this.pixelSize));

			if (num_case == this.current) {
				g2.setPaint(Color.red);
				g2.draw(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2,
						i * this.pixelSize + this.pixelSize / 2, 6, 6));
			}
			if (num_case == this.start) {
				g2.setPaint(Color.white);
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2,
						i * this.pixelSize + this.pixelSize / 2, 4, 4));

			}
			if (num_case == this.end) {
				g2.setPaint(Color.black);
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2,
						i * this.pixelSize + this.pixelSize / 2, 4, 4));
			}

			num_case += 1;
		}

		num_case = 0;
		for (WeightedGraph.Vertex v : this.graph.vertexlist) {
			int i = num_case / this.ncols;
			int j = num_case % this.ncols;
			if (v.timeFromSource < Double.POSITIVE_INFINITY) {
				float g_value = (float) (1 - v.timeFromSource / this.max_distance);
				if (g_value < 0)
					g_value = 0;
				g2.setPaint(new Color(g_value, g_value, g_value));
				g2.fill(new Ellipse2D.Double(j * this.pixelSize + this.pixelSize / 2,
						i * this.pixelSize + this.pixelSize / 2, 4, 4));
				WeightedGraph.Vertex previous = v.prev;
				if (previous != null) {
					int i2 = previous.num / this.ncols;
					int j2 = previous.num % this.ncols;
					g2.setPaint(Color.black);
					g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2,
							i * this.pixelSize + this.pixelSize / 2, j2 * this.pixelSize + this.pixelSize / 2,
							i2 * this.pixelSize + this.pixelSize / 2));
				}
			}

			num_case += 1;
		}

		int prev = -1;
		if (this.path != null) {
			g2.setStroke(new BasicStroke(3.0f));
			for (int cur : this.path) {
				if (prev != -1) {
					g2.setPaint(Color.red);
					int i = prev / this.ncols;
					int j = prev % this.ncols;
					int i2 = cur / this.ncols;
					int j2 = cur % this.ncols;
					g2.draw(new Line2D.Double(j * this.pixelSize + this.pixelSize / 2,
							i * this.pixelSize + this.pixelSize / 2, j2 * this.pixelSize + this.pixelSize / 2,
							i2 * this.pixelSize + this.pixelSize / 2));
				}
				prev = cur;
			}
		}
	}

	// Mise � jour du graphe (� appeler avant de mettre � jour l'affichage)
	public void update(Graph graph, int current) {
		this.graph = graph;
		this.current = current;
		repaint();
	}

	// Indiquer le chemin (pour affichage)
	public void addPath(Graph graph, LinkedList<Integer> path) {
		this.graph = graph;
		this.path = path;
		this.current = -1;
		repaint();
	}
}

//Classe principale. C'est ici que vous devez faire les modifications
public class App {

	// Initialise l'affichage
	private static void drawBoard(Board board, int nlines, int ncols, int pixelSize) {
		JFrame window = new JFrame("Plus court chemin");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, ncols * pixelSize + 20, nlines * pixelSize + 40);
		window.getContentPane().add(board);
		window.setVisible(true);
	}

	// Méthode pour calculer l'heuristique de la distance euclidienne

	private static double calculateEuclideanHeuristic(Vertex current, Vertex destination, int ncols) {
		int currentX = current.num / ncols;
		int currentY = current.num % ncols;
		int destinationX = destination.num / ncols;
		int destinationY = destination.num % ncols;
		return Math.sqrt(Math.pow(currentX - destinationX, 2) + Math.pow(currentY - destinationY, 2));
	}

	// Méthode pour calculer l'heuristique de Manhattan
	private static double calculateManhattanHeuristic(Vertex current, Vertex destination, int ncols) {
		int currentX = current.num / ncols;
		int currentY = current.num % ncols;
		int destinationX = destination.num / ncols;
		int destinationY = destination.num % ncols;
		return Math.abs(currentX - destinationX) + Math.abs(currentY - destinationY) + 3;
	}

	// M�thode A*
	// graph: le graphe repr�sentant la carte
	// start: un entier repr�sentant la case de d�part
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// end: un entier repr�sentant la case d'arriv�e
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// ncols: le nombre de colonnes dans la carte
	// numberV: le nombre de cases dans la carte
	// board: l'affichage
	// retourne une liste d'entiers correspondant au chemin.

	private static LinkedList<Integer> AStar(Graph graph, int start, int end, int ncols, int numberV, Board board) {
		long startTime = System.nanoTime();

		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0;

		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (int i = 0; i < graph.num_v; i++) {
			to_visit.add(graph.vertexlist.get(i).num);
		}

		for (int v = 0; v < graph.num_v; v++) {
			graph.vertexlist.get(v).heuristic = calculateEuclideanHeuristic(graph.vertexlist.get(v),
					graph.vertexlist.get(end), ncols);
		}

		while (!to_visit.isEmpty()) {
			int min_v = 0;
			double dist_min = Double.POSITIVE_INFINITY;
			for (int v = 0; v < graph.num_v; v++) {
				if (to_visit.contains(graph.vertexlist.get(v).num)
						&& graph.vertexlist.get(v).heuristic + graph.vertexlist.get(v).timeFromSource < dist_min) {
					min_v = v;
					dist_min = graph.vertexlist.get(v).heuristic + graph.vertexlist.get(v).timeFromSource;
				}
			}

			to_visit.remove(min_v);
			number_tries++;

			// Condition pour arrêter l'algorithme si le nœud de destination est atteint
			if (min_v == end) {
				break;
			}

			for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
				Edge edge = graph.vertexlist.get(min_v).adjacencylist.get(i);
				int to_try = edge.destination;

				double newCost = graph.vertexlist.get(min_v).timeFromSource + edge.weight;
				if (newCost < graph.vertexlist.get(to_try).timeFromSource) {
					graph.vertexlist.get(to_try).timeFromSource = newCost;
					graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v);
				}
			}

			try {
				board.update(graph, min_v);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}
		}

		System.out.println("Done! Using A*:");
		System.out.println("	Number of nodes explored: " + number_tries);
		System.out.println("	Total time of the path: " + graph.vertexlist.get(end).timeFromSource);

		LinkedList<Integer> path = new LinkedList<Integer>();
		int current = end;
		while (current != start) {
			path.addFirst(current);
			current = graph.vertexlist.get(current).prev.num;
		}
		path.addFirst(start);

		board.addPath(graph, path);
		long endTime = System.nanoTime();
		double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
		System.out.println("A* - Temps d'exécution: " + durationSeconds + " secondes");

		return path;
	}

	private static int findMinDistVertex(HashSet<Integer> to_visit, Graph graph) {
		int min_v = -1;
		double minDist = Double.POSITIVE_INFINITY;

		for (int v : to_visit) {
			if (graph.vertexlist.get(v).timeFromSource < minDist) {
				min_v = v;
				minDist = graph.vertexlist.get(v).timeFromSource;
			}
		}

		return min_v;
	}

	// M�thode Dijkstra
	// graph: le graphe repr�sentant la carte
	// start: un entier repr�sentant la case de d�part
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// end: un entier repr�sentant la case d'arriv�e
	// (entier unique correspondant � la case obtenue dans le sens de la lecture)
	// numberV: le nombre de cases dans la carte
	// board: l'affichage
	// retourne une liste d'entiers correspondant au chemin.
	private static LinkedList<Integer> Dijkstra(Graph graph, int start, int end, int numberV, Board board) {
		long startTime = System.nanoTime();
		// int nodesExplored = 0;
		graph.vertexlist.get(start).timeFromSource = 0;
		int number_tries = 0;

		// TODO: mettre tous les noeuds du graphe dans la liste des noeuds � visiter:
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for (int i = 0; i < graph.num_v; i++) {
			to_visit.add(graph.vertexlist.get(i).num);
		}

		while (!to_visit.isEmpty()) {
			{
				// TODO: trouver le noeud min_v parmis tous les noeuds v ayant la distance
				// temporaire
				// graph.vertexlist.get(v).timeFromSource minimale.
				int min_v = findMinDistVertex(to_visit, graph);
				// Partie où vous explorez les nœuds
				// nodesExplored++;

				// On l'enl�ve des noeuds � visiter
				// get vertex with min dist
				to_visit.remove(min_v);
				number_tries += 1;

				// TODO: pour tous ses voisins, on v�rifie si on est plus rapide en passant par
				// ce noeud.
				for (int i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
					Edge edge = graph.vertexlist.get(min_v).adjacencylist.get(i);
					int to_try = edge.destination;

					// Mise à jour du coût temporaire si le nouveau chemin est plus court
					double newCost = graph.vertexlist.get(min_v).timeFromSource + edge.weight;
					if (newCost < graph.vertexlist.get(to_try).timeFromSource) {
						graph.vertexlist.get(to_try).timeFromSource = newCost;
						graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v); // Sauvegarde du prédécesseur
					}
				}
				// On met � jour l'affichage
				try {
					board.update(graph, min_v);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					System.out.println("stop");
				}

			}
		}

		System.out.println("Done! Using Dijkstra:");
		System.out.println("	Number of nodes explored: " + number_tries);
		// System.out.println(" Total time of the path: " +
		// graph.vertexlist.get(end).timeFromSource);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addFirst(end);
		// TODO: remplir la liste path avec le chemin
		int current = end;

		while (current != start) {
			path.addFirst(current);
			current = graph.vertexlist.get(current).prev.num;
		}
		board.addPath(graph, path);
		long endTime = System.nanoTime();
		double durationSeconds = (endTime - startTime) / 1_000_000_000.0;
		System.out.println("Dijkstra - Temps d'exécution: " + durationSeconds + " secondes");
		return path;
	}

	// M�thode principale
	public static void main(String[] args) {
		// Lecture de la carte et cr�ation du graphe
		try {
			// TODO: obtenir le fichier qui d�crit la carte
			File myObj = new File(args[0]);
			Scanner myReader = new Scanner(myObj);
			String data = "";
			// On ignore les deux premi�res lignes
			for (int i = 0; i < 3; i++)
				data = myReader.nextLine();

			// Lecture du nombre de lignes
			int nlines = Integer.parseInt(data.split("=")[1]);
			System.out.println("nb lignes :" + nlines);
			// Et du nombre de colonnes
			data = myReader.nextLine();
			System.out.println("nb colonnes :" + data);

			int ncols = Integer.parseInt(data.split("=")[1]);

			// Initialisation du graphe
			Graph graph = new Graph();

			HashMap<String, Integer> groundTypes = new HashMap<String, Integer>();
			HashMap<Integer, String> groundColor = new HashMap<Integer, String>();
			data = myReader.nextLine();
			data = myReader.nextLine();
			// Lire les diff�rents types de cases
			while (!data.equals("==Graph==")) {
				String name = data.split("=")[0];
				int time = Integer.parseInt(data.split("=")[1]);
				data = myReader.nextLine();
				String color = data;
				groundTypes.put(name, time);
				groundColor.put(time, color);
				data = myReader.nextLine();
			}

			// On ajoute les sommets dans le graphe (avec le bon type)
			for (int line = 0; line < nlines; line++) {
				data = myReader.nextLine();
				for (int col = 0; col < ncols; col++) {
					graph.addVertex(groundTypes.get(String.valueOf(data.charAt(col))));
				}
			}

			// Ajouter les arêtes (Pour heuristique :Division Euclidienne)
			for (int line = 0; line < nlines; line++) {
				for (int col = 0; col < ncols; col++) {
					int source = line * ncols + col;

					// Ajouter l'arête vers le haut-gauche (diagonale)
					if (line > 0 && col > 0) {
						int dest = (line - 1) * ncols + col - 1;
						graph.addEdge(source, dest, true); // Diagonale
					}
					// Ajouter l'arête vers le haut
					if (line > 0) {
						int dest = (line - 1) * ncols + col;
						graph.addEdge(source, dest, false); // Non diagonale
					}
					// Ajouter l'arête vers le haut-droite (diagonale)
					if (line > 0 && col < ncols - 1) {
						int dest = (line - 1) * ncols + col + 1;
						graph.addEdge(source, dest, true); // Diagonale
					}
					// Ajouter l'arête vers la gauche
					if (col > 0) {
						int dest = line * ncols + col - 1;
						graph.addEdge(source, dest, false); // Non diagonale
					}
					// Ajouter l'arête vers la droite
					if (col < ncols - 1) {
						int dest = line * ncols + col + 1;
						graph.addEdge(source, dest, false); // Non diagonale
					}
					// Ajouter l'arête vers le bas-gauche (diagonale)
					if (line < nlines - 1 && col > 0) {
						int dest = (line + 1) * ncols + col - 1;
						graph.addEdge(source, dest, true); // Diagonale
					}
					// Ajouter l'arête vers le bas
					if (line < nlines - 1) {
						int dest = (line + 1) * ncols + col;
						graph.addEdge(source, dest, false); // Non diagonale
					}
					// Ajouter l'arête vers le bas-droite (diagonale)
					if (line < nlines - 1 && col < ncols - 1) {
						int dest = (line + 1) * ncols + col + 1;
						graph.addEdge(source, dest, true); // Diagonale
					}
				}
			}

			// Ajout Arete pour heuristique Manhattan

			// Ajouter les arêtes
			/*
			 * for (int line = 0; line < nlines; line++) { for (int col = 0; col < ncols;
			 * col++) { int source = line * ncols + col;
			 * 
			 * // Ajouter l'arête vers le haut if (line > 0) { int dest = (line - 1) * ncols
			 * + col; graph.addEdge(source, dest); // Non diagonale }
			 * 
			 * // Ajouter l'arête vers la gauche if (col > 0) { int dest = line * ncols +
			 * col - 1; graph.addEdge(source, dest); // Non diagonale }
			 * 
			 * // Ajouter l'arête vers la droite if (col < ncols - 1) { int dest = line *
			 * ncols + col + 1; graph.addEdge(source, dest); // Non diagonale }
			 * 
			 * // Ajouter l'arête vers le bas if (line < nlines - 1) { int dest = (line + 1)
			 * * ncols + col; graph.addEdge(source, dest); // Non diagonale } } }
			 */

			// On obtient les noeuds de d�part et d'arriv�
			data = myReader.nextLine();
			data = myReader.nextLine();
			int startV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols
					+ Integer.parseInt(data.split("=")[1].split(",")[1]);
			data = myReader.nextLine();
			int endV = Integer.parseInt(data.split("=")[1].split(",")[0]) * ncols
					+ Integer.parseInt(data.split("=")[1].split(",")[1]);

			myReader.close();

			// A changer pour avoir un affichage plus ou moins grand
			int pixelSize = 14;
			Board board = new Board(graph, pixelSize, ncols, nlines, groundColor, startV, endV);
			drawBoard(board, nlines, ncols, pixelSize);
			board.repaint();

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}

			// On appelle Dijkstra
			LinkedList<Integer> path;
			System.out.println("Heuristique Division Euclidienne");
			System.out.println("(0) Dijkstra ou (1) A*");
			Scanner lc = new Scanner(System.in);

			int choix = lc.nextInt();

			if (choix == 1) {
				int numberV = nlines * ncols;
				path = AStar(graph, startV, endV, nlines * ncols, numberV, board);
			} else {
				path = Dijkstra(graph, startV, endV, nlines * ncols, board);
			}

			Vertex endVertex = graph.vertexlist.get(endV);

			// Afficher le poids total du chemin
			// System.out.println("Poids total du chemin : " + totalPathWeight);
			System.out.println("Poids total du chemin : " + endVertex.timeFromSource);

			// TODO: laisser le choix entre Dijkstra et A*

			// �criture du chemin dans un fichier de sortie
			try {
				File file = new File("out.txt");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);

				for (int i : path) { // dans path se trouve les numero des sommets ajoute au chemin
					bw.write(String.valueOf(i));
					bw.write('\n');
				}
				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}