import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * 3. Searching for all trips with a given arrival time, returning full details of all trips matching the
 *	criteria (zero, one or more), sorted by trip id
 *	Arrival time should be provided by the user as hh:mm:ss. When reading in stop_times.txt file you
 *	will need to remove all invalid times, e.g., there are times in the file that start at 27/28 hours, so are
 *	learly invalid. Maximum time allowed is 23:59:59. 
 */
public class ArrivalTimes {
	public static void main(String[] args) throws FileNotFoundException {

		File stopTimes = new File("stop_times.txt");
		ArrayList<String> validStopTimes = new ArrayList<String>();

		removeInvalidTimes(stopTimes, validStopTimes);

		boolean finished = false;
		while(!finished) {
			System.out.print("Enter the time you wish to arrive (in the form hh:mm:ss):\n");

			Scanner scanner = new Scanner(System.in);
			String userInput = scanner.nextLine();

			boolean validTime = checkValidTime(userInput);

			if(validTime) {
				int count = 0;
				for(int i = 0; i<validStopTimes.size();i++) {
					String tripDetails = validStopTimes.get(i);
					String[] str = tripDetails.split(",");


					if (userInput.trim().equals(str[1].trim())) {		
						showTripDetails(str);
						count++;
					}			
				}
				if(count==0) {
					System.out.println("Sorry, no stops match your desired arrival time.");
				}

			}
			else {
				System.out.println("Please enter a valid time:");
			}
		}
	}	

	public static boolean checkValidTime(String input) {

		boolean valid;
		String arrivalTime = input;
		String[] str = arrivalTime.split(":");

		int hours = Integer.parseInt(str[0].trim());
		int minutes = Integer.parseInt(str[1].trim());
		int seconds = Integer.parseInt(str[2].trim());

		if (hours <= 23 && minutes <= 59 && seconds <= 59 &&
				hours >= 0 && minutes >= 0 && seconds >= 0){
			valid = true;
		}else {
			valid = false;
		}
		return valid;
	}


	public static void removeInvalidTimes(File filename, ArrayList<String> ArrayList) throws FileNotFoundException{

		Scanner input = new Scanner(new FileReader(filename));
		input.nextLine();
		while(input.hasNextLine())
		{
			String line = input.nextLine();
			String[] tripDetails = line.split(",");
			String arrivalTime = tripDetails[1];

			if(checkValidTime(arrivalTime)) {
				ArrayList.add(line);
			}

		}	

	}

	public static void showTripDetails(String[] str) {

		String tripID = str[0];
		String arrivalTime = str[1];
		String departureTime = str[2];
		String stopID = str[3];
		String stopSequence = str[4];
		String stopHeadsign = str[5];
		String pickupType = str[6];
		String dropOffType = str[7];
		//	String shapeDistTraveled = str[8];

		System.out.println("Trip ID: "+tripID+"\n"+
				"Arrival Time: "+arrivalTime+"\n"+
				"Departure Time: "+departureTime+"\n"+
				"Stop ID: "+stopID+"\n"+
				"Stop Sequence: "+stopSequence+"\n"+
				"Stop Headsign: "+stopHeadsign+"\n"+
				"Pickup Type: "+pickupType+"\n"+
				"Dropoff Type: "+dropOffType+"\n"
				//	"Shape Distance Travelled: "+shapeDistTraveled+"\n\n"
				);
	}

}
