/*
 * Adapted from https://algs4.cs.princeton.edu
 *	@author Robert Sedgewick
 * 	@author Kevin Wayne
 */

public class DijkstraSP {
	private double[] distTo;          
	private Edge[] edgeTo;    
	private IndexMinPQ<Double> pq;    

	/**
	 * Computes a shortest-paths tree from the source vertex {@code s} to every other
	 * vertex in the edge-weighted digraph {@code G}.
	 *
	 * @param  G the edge-weighted digraph
	 * @param  s the source vertex
	 * @return 
	 */
	public DijkstraSP(Graph G, int s) {
		distTo = new double[G.V()];
		edgeTo = new Edge[G.V()];
		for (int v = 0; v < G.V(); v++)
			distTo[v] = Double.POSITIVE_INFINITY;
		distTo[s] = 0.0;

		// relax vertices in order of distance from s
		pq = new IndexMinPQ<Double>(G.V());
		pq.insert(s, distTo[s]);
		while (!pq.isEmpty()) {
			int v = pq.delMin();
			for (Edge e : G.adj(v))
				relax(e);
		}
	}


	// relax edge e and update pq if changed
	private void relax(Edge e) {
		int v = e.from(), w = e.to();
		if (distTo[w] > distTo[v] + e.weight()) {
			distTo[w] = distTo[v] + e.weight();
			edgeTo[w] = e;
			if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
			else                pq.insert(w, distTo[w]);
		}
	}

	/**
	 * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
	 * @param  v the destination vertex
	 * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
	 *         {@code Double.POSITIVE_INFINITY} if no such path
	 */
	public double distTo(int v) {
		return distTo[v];
	}

	
	
	/**
	 * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
	 *
	 * @param  v the destination vertex
	 * @return {@code true} if there is a path from the source vertex
	 *         {@code s} to vertex {@code v}; {@code false} otherwise
	 */
	public boolean hasPathTo(int v) {
		return distTo[v] < Double.POSITIVE_INFINITY;
	}

	
	
    /**
     * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
     *
     * @param  v the destination vertex
     * @return a shortest path from the source vertex {@code s} to vertex {@code v}
     *         as an iterable of edges, and {@code null} if no such path
     * @throws IllegalArgumentException unless {@code 0 <= v < V}
     */
    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<Edge>();
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }
    
    // throw an IllegalArgumentException unless {@code 0 <= v < V}
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V-1));
    }
}
