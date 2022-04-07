import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
				System.out.println("Part 1 of project not complete");

			}

			//******* PART 2 *******\\
			else if (userInput.equals("2")) {	
				boolean finished2 = false;
				while(!finished2) {
					System.out.print("\nEnter the name of the bus stop you wish to search for:\nor 'exit' to go back:\n");
					Scanner input2 = new Scanner(System.in);
					String choice = input2.nextLine();

					
					if(choice.equals("exit")){
						break;
					}
					else
					{
						String userInput2 = choice.toUpperCase();
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
