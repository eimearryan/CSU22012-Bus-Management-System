import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
	public static void main(String[] args) throws FileNotFoundException {



		boolean finished = false;
		while(!finished) {
			System.out.print("\nPlease choose from the options below which program you would like to run or type 'exit':\n"
					+ "1 - Find the shortest path between two bus stops\n"
					+ "2 - Find full stop information for a given stop\n"
					+ "3 - Find full trip infomration for all trips with a given arrival time\n" + "---> ");

			Scanner input = new Scanner(System.in);
			String userInput = input.next();

			//******* PART 1 *******\\
			if (userInput.equals("1")) {

				// create a new graph with the size of all bus stops
				int vertices = ShortestPath.findVertices("stops.txt");		
				Graph graph = new Graph(vertices);

				// create a hash map where the keys are the bus stops 
				HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
				int mapIndex = 0;

				File file = new File("stops.txt");
				Scanner scanner = new Scanner(file);
				String headings = scanner.nextLine();
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					String[] lineContents = line.split(",");
					int v = Integer.parseInt(lineContents[0]);
					map.put(v, mapIndex);
					mapIndex++;
				}

				// add edges to the graph
				ShortestPath.addTransfersEdges(map, graph, "transfers.txt");
				ShortestPath.addStopTimeEdges(map, graph, "stop_times.txt");


				boolean finished1 = false;
				while(!finished1) {


					// Ask user for input stops 
					System.out.print("\nEnter the first(source) stop number or 'exit' to go back:\n");
					Scanner input1 = new Scanner(System.in);
					String userInput1 = input1.next();

					if(userInput1.equals("exit")) {
						break;
					}

					else {
						int stop1=0;
						int stop2=0;

						boolean validStop1 = false;
						while(!validStop1) {	
							try {
								stop1 = Integer.parseInt(userInput1);
							}catch (NumberFormatException nfe) {
								System.out.println("This input is invalid.");
								break;
							}

							if ((map.get(stop1) != null)) {
								validStop1 = true;
							}else {
								System.out.println("This is not a stop on our system.");
								break;
							}



							boolean validStop2 = false;
							while(!validStop2) {
								System.out.print("\nEnter the second(destination) stop number:\n");
								if (input1.hasNextInt()) {
									stop2 = input1.nextInt();
									if ((map.get(stop2) != null)) {
										validStop2 = true;
									}else {
										System.out.println("This is not a stop on our system.");
									}
								}else {
									System.out.println("This input is invalid.");
								}
							}


							// use dijkstra to find the shortest path between bus stops

							ShortestPath.findShortestPath(stop1, stop2, graph, map);

						}
					}

				}
			}

			//******* PART 2 *******\\
			else if (userInput.equals("2")) {	
				boolean finished2 = false;
				while(!finished2) {
					System.out.print("\nEnter the name of the bus stop you wish to search for:\nor 'exit' to go back:\n");
					Scanner input2 = new Scanner(System.in);
					String userInput2 = input2.nextLine();


					if(userInput2.equals("exit")){
						break;
					}
					else
					{
						userInput2 = userInput2.toUpperCase();
						TST.BusStopTST(userInput2);
					}

				}  
			}

			//******* PART 3 *******\\
			else if (userInput.equals("3")) {		
				File stopTimes = new File("stop_times.txt");


				boolean finished3 = false;
				while(!finished) {
					ArrayList<String> validStopTimes = new ArrayList<String>();
					ArrayList<String> desiredTripDetails = new ArrayList<String>();

					ArrivalTimes.removeInvalidTimes(stopTimes, validStopTimes);

					System.out.print("\nEnter the time you wish to arrive (in the form hh:mm:ss)\n"
							+ "or 'exit' to go back:");

					Scanner scanner = new Scanner(System.in);
					String userInput3 = scanner.nextLine();


					if (userInput3.equals("exit")) {
						break;
					}
					boolean validTime = ArrivalTimes.checkValidTime(userInput3);

					if(validTime) {
						int count = 0;
						for(int i = 0; i<validStopTimes.size();i++) {
							String tripDetails = validStopTimes.get(i);
							String[] str = tripDetails.split(",");


							if (userInput3.trim().equals(str[1].trim())) {		
								desiredTripDetails.add(tripDetails);
								count++;
							}		
						}
						if(count==0) {
							System.out.println("\nSorry, no stops match your desired arrival time.");
						}else {
							ArrivalTimes.showTripDetails(desiredTripDetails);
						}
					}
					else {
						System.out.println("\nThis is an invalid time.");
					}
				}	
			}


			//******* EXIT PROJECT *******\\
			else if (userInput.equals("exit")) {
				System.out.println("Have a nice day!");
				finished = true;
			}

			//******* ERROR *******\\
			else {
				System.out.println("Invalid option\n");
			}

		}

	}
}
