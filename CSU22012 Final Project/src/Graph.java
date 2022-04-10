import java.util.ArrayList;

public class Graph {
	int vertices;
	int edges;
	ArrayList<Edge> [] adjacencylist;

	Graph(int vertices) {
		this.vertices = vertices;
		this.edges = 0;
		adjacencylist = new ArrayList[vertices];
		//initialize adjacency lists for all the vertices
		for (int i = 0; i <vertices ; i++) {
			adjacencylist[i] = new ArrayList<>();
		}

	}

	public void addEdge(int source, int destination, double weight) {
		Edge edge = new Edge(source, destination, weight);
		adjacencylist[source].add(edge); 
		edges++;
	}


	public int V() {
		return vertices;
	}


	public int E() {
		return edges;
	}

	public Iterable<Edge> adj(int v) {
		return adjacencylist[v];
	}
}
