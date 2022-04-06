import java.util.Scanner;

public class userInterface {
	public static void main(String[] args) {
		
		
		
		boolean finished = false;
		
		while(!finished) {
		System.out.print("\nPlease choose from the options below which program you would like to run or type 'exit':\n"
                + "1 - Find the shortest path between two bus stops\n"
                + "2 - Find full stop information for a given stop\n"
                + "3 - Find full trip infomration for all trips with a given arrival time\n" + "- ");
       
		Scanner input = new Scanner(System.in);
		String userInput = input.next();
		

		if (userInput.equals("1")) {
			System.out.println("option1");
			
		}
		
		else if (userInput.equals("2")) {
			System.out.println("option2");
		}
		
		else if (userInput.equals("3")) {
			System.out.println("option3");
		}
		
		else if (userInput.equals("exit")) {
			System.out.println("Have a nice day!");
			finished = true;
		}
		
		else {
			System.out.println("Invalid option\n");
		}
		
		}
		
	}
}
