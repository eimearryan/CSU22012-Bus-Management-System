
public class Edge {
		int source;
		int destination;
		double weight;

		public Edge(int source, int destination, double weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
		
	
	    public int from() {
	        return source;
	    }


	    public int to() {
	        return destination;
	    }
	    
	    public double weight() {
	        return weight;
	    }
	}