/* 
 * 1. Finding shortest paths between 2 bus stops (as input by the user), returning the list of stops
 * 	en route as well as the associated “cost”.
 *	Stops are listed in stops.txt and connections (edges) between them come from stop_times.txt and
 *	transfers.txt files. All lines in transfers.txt are edges (directed), while in stop_times.txt an edge
 *	should be added only between 2 consecutive stops with the same trip_id.
 *	eg first 3 entries in stop_times.txt are
 *	9017927, 5:25:00, 5:25:00,646,1,,0,0,
 *	9017927, 5:25:50, 5:25:50,378,2,,0,0,0.3300
 *	9017927, 5:26:28, 5:26:28,379,3,,0,0,0.5780
 *	This should add a directed edge from 646 to 378, and a directed edge from 378 to 379 (as they’re on
 *	the same trip id 9017927).
 *	Cost associated with edges should be as follows: 1 if it comes from stop_times.txt, 2 if it comes from
 *	transfers.txt with transfer type 0 (which is immediate transfer possible), and for transfer type 2 the
 *	cost is the minimum transfer time divided by 100.
 */
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