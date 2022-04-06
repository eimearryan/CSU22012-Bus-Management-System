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
					System.out.print("Enter 0 if you would like to Search by full bus name or\n1 if you would like to "
							+ "search by first word in bus stop name\nor 2 if you would like to go back: \n");
					Scanner input2 = new Scanner(System.in);
					int choice = input2.nextInt();

					if (choice == 0 || choice == 1)
					{
						TST.BusStopTST(choice);
					}
					else if(choice == 2) {
						break;
					}
					else {
						System.out.print("Please enter a valid choice\n");
					}
				}  
			}

			//******* PART 3 *******\\
			else if (userInput.equals("3")) {		
				File stopTimes = new File("stop_times.txt");
				ArrayList<String> validStopTimes = new ArrayList<String>();
				ArrayList<String> desiredTripDetails = new ArrayList<String>();

				ArrivalTimes.removeInvalidTimes(stopTimes, validStopTimes);

				boolean finished3 = false;
				while(!finished3) {
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
