import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

public class ShortestPath {


	public static int findVertices(String filename) {
		File file = new File(filename);
		int vertices = 0;
		try {	
			Scanner input = new Scanner(file);
			String headings = input.nextLine();
			while(input.hasNextLine()) {
				vertices++;
				input.nextLine();
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			e.printStackTrace();
		}	
		return vertices;
	}



	public static void addTransfersEdges(HashMap<Integer, Integer> map, Graph graph, String filename) throws FileNotFoundException {
		File transfersFile = new File(filename);
		Scanner input = new Scanner(transfersFile);
		String headings = input.nextLine();
		while(input.hasNextLine()) {
			String line = input.nextLine();
			String[] details = line.split(",");
			int source = Integer.parseInt(details[0]);

			int dest = Integer.parseInt(details[1]);

			int transferType = Integer.parseInt(details[2]);
			double weight = 0;
			if(transferType==0) {
				weight = 2;	
			}
			else if(transferType==2) {
				double minimumTransferTime = Double.parseDouble(details[3]);
				weight = minimumTransferTime/100;
			}
			graph.addEdge(map.get(source), map.get(dest), weight);
		}
	}


	public static void addStopTimeEdges(HashMap<Integer, Integer> map, Graph graph, String filename) throws FileNotFoundException {
		File stopTimesFile = new File(filename);
		Scanner input = new Scanner(stopTimesFile);
		String headings = input.nextLine();
		String currentLine = input.nextLine();

		while(input.hasNextLine()) {
			String nextLine = input.nextLine();
			String[] currentDetails = currentLine.split(",");
			String[] nextDetails = nextLine.split(",");

			String currentID = currentDetails[0];
			String nextID = nextDetails[0];

			if(currentID.equals(nextID)) {
				int source = Integer.parseInt(currentDetails[3]);
				int dest = Integer.parseInt(nextDetails[3]);

				graph.addEdge(map.get(source), map.get(dest), 1);
			}
			currentLine = nextLine;
		}
	}

	public static void findShortestPath(int src, int dest, Graph g, HashMap<Integer, Integer> map) {

		int srcIndex = map.get(src);
		int destIndex = map.get(dest);

		DijkstraSP dijkstraGraph = new DijkstraSP(g, srcIndex);	

		if(dijkstraGraph.hasPathTo(destIndex)) {
			double cost = dijkstraGraph.distTo(destIndex);

			Iterable<Edge> listOfStops = dijkstraGraph.pathTo(destIndex);

			System.out.println("The shortest route from "+src+" to "+ dest+ " is through the following stops: ");
			for(Edge stop: listOfStops) {
				System.out.println("Stop ID: " +getKey(map, stop.to()));
			}
			System.out.println("\nAssociated Cost: " + cost);
		}
		else {
			System.out.println("There's no path from stop \"" + src + "\" to stop \"" + dest + "\"");
		}
	}


	public static <K, V> K getKey(HashMap<K, V> map, V value) {
		for (Entry<K, V> entry : map.entrySet()) {
			if (entry.getValue().equals(value)) {
				return entry.getKey();
			}
		}
		return null;
	}

}