import java.util.*;
import java.util.Random.*;

/**
* Class Description: Solve demand paging problems using FIFO, OPT, LRU, and LFU.
* @Author Nicholas Siebenlist
* @Since 04.28.20
* CMSC 412
*/  

public class Main {
	static int physicalFrames; //tracks number of physical frames
	static final int VIRTUAL_FRAMES = 10; //limit on virtual frame
	static String referenceString = ""; //tracks active reference string
	static boolean validString = false; //tracks if a valid reference string is active
	static Scanner scanner = new Scanner(System.in); //scanner for user inputs
	static int referenceLength; //holds the length of reference string
	static String[][] table; //solution table
	static String[] currentFrames; //tracks which virtual frames are currently stored in physical frames

	public static void main(String[] args) {
		boolean validFrames = false; //input validation for number of physical frames
		while(!validFrames) {
			try {
				System.out.println("Enter the number of physical frames for this simulation:");
				physicalFrames = scanner.nextInt();
				scanner.nextLine();
				if(physicalFrames<1 || physicalFrames>7) { //input validation
					System.out.println("Physical frames must be between 1-7.");
				}
				else {
					validFrames = true;
				}
			}
			catch(Exception e) {
				System.out.println("Not a valid entry.");
				scanner.nextLine(); //clears scanner if non-numeric input
			}
		}

		int menuChoice = -1;
		while(menuChoice!=0) { //loops until user selects exit
			System.out.println("\nPlease make a selection from the following menu:\n");
			System.out.println("0 - Exit");
			System.out.println("1 - Read reference string");
			System.out.println("2 - Generate reference string");
			System.out.println("3 - Display current reference string");
			System.out.println("4 - Simulate FIFO");
			System.out.println("5 - Simulate OPT");
			System.out.println("6 - Simulate LRU");
			System.out.println("7 - Simulate LFU");

			try {
				menuChoice = scanner.nextInt();
				scanner.nextLine(); //clears scanner input
				switch(menuChoice) { //switch statement that calls function appropriate to selection
					case 0:
						System.out.println("Exiting the program.");
						break;
					case 1: 
						readString();
						break;
					case 2: 
						generateString();
						break;
					case 3: 
						displayString();
						break;
					case 4: 
						FIFO();
						break;
					case 5: 
						OPT();
						break;
					case 6: 
						LRU();
						break;
					case 7: 
						LFU();
						break;
					default: 
						System.out.println("Not a valid menu choice, please try again.");
						break;
				}
			}
			catch(Exception e) {
				System.out.println("Invalid input, please try again.");
				scanner.nextLine();
			}
		}
	}

	/**
	* Reads and validates reference string input by user
	*/
	public static void readString() {
		boolean allValid = true;
		String formattedString = "";
		try {
			System.out.println("Enter the reference string:"); //prompts user for reference string
			String tempString = scanner.nextLine();
			String[] tokens = tempString.split(" "); //splits reference string on spacing so each number can be validated
			referenceLength = tokens.length;
			for(int i=0; i<tokens.length; i++) {
				int tempInt = Integer.parseInt(tokens[i]);
				if(tempInt<0 || tempInt >9) { //checks virtual frames are acceptable numbers
					allValid = false;
				}
				formattedString = formattedString + tokens[i] + " "; 
				//updates formatted string if virtual frame is valid
			}
			if(allValid) {
				validString = true; //sets reference string to active which allows algorithms to proceed
				referenceString = formattedString; //updates reference string
			}
			else {
				System.out.println("An invalid virtual frame was referenced."); //error message
				validString = false;
			}
		}
		catch(Exception e) {
			System.out.println("Reference string invalid."); //error message
			validString = false; //reference string is not valid or active
		}
	}

	/**
	* Generates a random reference string given the length of the string by the user
	*/
	public static void generateString() {
		boolean validLength = false; //input validation for length
		int length = -1;
		String tempString = "";
		int tempInt = 0;
		while(!validLength) {
			try{
				System.out.println("Please enter the length of the randomly generated reference string: ");
				length = scanner.nextInt();
				if(length > 0) {
					validLength = true;
					Random random = new Random(); //creates a random for number generation
					for(int i=0; i<length; i++) {
						tempInt = random.nextInt(VIRTUAL_FRAMES); //generates number between 0-9 inclusive
						tempString = tempString + Integer.toString(tempInt) + " ";
					}
					referenceString = tempString; //reference string is updated
					validString = true; //reference string is valid and active
					referenceLength = length;
				}
				else {
					System.out.println("length must be greater than 0."); //error message
				}
			}
			catch(Exception e) {
				System.out.println("Not a valid input, please try again.");
				validString = false; //reference string no longer active or valid after error
				scanner.nextLine();
			}
		}
	}

	/**
	* Displays reference string for user if active, otherwise gives error message
	*/
	public static void displayString() {
		if(validString) {
			System.out.println("Current reference string: " + referenceString);
		}
		else {
			System.out.println("No valid reference string currently available.");
		}
	}

	/**
	* Solves demand paging with FIFO algorithm and displays solution at start, middle, and end
	*/
	public static void FIFO() {
		table = new String[physicalFrames+2][referenceLength]; //creates solution table
		init(); //initializes solution table
		String[] tokens = referenceString.split(" "); //splits reference string into virtual frames
		currentFrames = new String[physicalFrames];
		int count=1;
		int column=1;
		currentFrames[0]=tokens[0];
		table[0][0] = tokens[0]; //first solution is always the first virtual frame
		table[physicalFrames][0] = "F";
		while(count < physicalFrames) { //performs frame pushing until all physical frames are filled
			String tempString = tokens[column];
			boolean newFrame = true;			
			for(int i=0; i<currentFrames.length; i++) {
				if(tempString.equals(currentFrames[i])) { //checks if virtual frame in reference string is already in physical frames
					newFrame = false;
				}
			}
			if(newFrame) {
				table[0][column]=tokens[column];
				for(int j=1; j<physicalFrames; j++) {
					table[j][column] = table[j-1][column-1]; //shifts physical frame forwards 1
				}
				table[physicalFrames][column]="F";
				currentFrames[count]=tokens[column];
				count++; //tracks when physical frames are filled
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column when there is no fault
				}
			}
			column++;
		}

		display();
		System.out.println();

		for(int i=column; i<referenceLength; i++) { //loops through rest of reference string
			if(i==(referenceLength+physicalFrames)/2) { //intermediate solution printing
				display();
				System.out.println();
			}
			boolean replace = true;
			String tempString = tokens[i];
			for(int j=0; j<currentFrames.length; j++) {
				if(tempString.equals(currentFrames[j])) { //checks if virtual frame in reference string is already in physical frames
					replace = false;
				}
			}
			if(replace) {
				table[physicalFrames][i]= "F"; //updates fault row
				table[physicalFrames+1][i] = table[physicalFrames-1][i-1]; //shifts physical frame in solution set
				currentFrames[0] = tokens[i];
				table[0][i] = tokens[i];
				for(int j=1; j<physicalFrames; j++) {
					table[j][i] = table[j-1][i-1]; //shifter physical frame in solution set
					currentFrames[j] = table[j][i]; //updates active virtual frame tracking
				}
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1]; //copies last column when no fault
				}
			}	
		}

		display();

		int faultCount = 0; //tracks how many faults in each solution
		for(int j=0; j<referenceLength; j++) {
			if(table[physicalFrames][j].equals("F")) {
				faultCount++;
			}
		}
		System.out.format("\nThe total number of faults for this algorithm is: %d\n", faultCount);
	}

	/**
	* Solves demand paging with FIFO algorithm and displays solution at start, middle, and end
	*/
	public static void OPT() {
		table = new String[physicalFrames+2][referenceLength]; //sets size of solution table
		init(); //initializes solution table
		String[] tokens = referenceString.split(" "); //splits reference string into virtual frames
		currentFrames = new String[physicalFrames]; //tracks active virtual frames
		int count=1;
		int column=1;
		currentFrames[0]=tokens[0];
		table[0][0] = tokens[0];
		table[physicalFrames][0] = "F";
		while(count < physicalFrames) { //sets initial physical frames
			String tempString = tokens[column];
			boolean newFrame = true;			
			for(int i=0; i<currentFrames.length; i++) {
				if(tempString.equals(currentFrames[i])) { //checks if virtual frame in reference string is already in physical frames
					newFrame = false;
				}
			}
			if(newFrame) {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column
				}
				table[count][column]=tokens[column]; //adds new virtual frame to open physical frame
				table[physicalFrames][column]="F";
				count++;
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column only when no fault
				}
			}
			column++;
		}
		display();
		System.out.println();

		for(int i=column; i<referenceLength; i++) {
			if(i==(referenceLength+physicalFrames)/2) { //intermediate solution display
				display();
				System.out.println();
			}
			boolean replace = true;
			String tempString = tokens[i];
			for(int j=0; j<physicalFrames; j++) {
				if(tempString.equals(table[j][i-1])) { //checks if virtual frame in reference string is already in physical frames
					replace = false;
				}
			}

			int replaceFrame = 0;
			int colTracker = -1;
			boolean solved = false;
			int[] replaceTracker = new int[physicalFrames]; //tracks when active virtual frames next show up
			for(int x=0; x<replaceTracker.length; x++) {
				replaceTracker[x] = -1;
			}

			for(int j=0; j<physicalFrames; j++) {
				for(int k=i+1; k<referenceLength; k++) { 
					if(table[j][i-1].equals(tokens[k])) { //checks reference string for next instance of virtual frame
						if(replaceTracker[j]==-1) { //when first instance is found, update
							replaceTracker[j]=k; //updates tracker position
						}
					}
				}
			}
			
			for(int y=0; y<physicalFrames; y++) {
				if(!solved) {
					if(replaceTracker[y]==-1) { //virtual frames with no next instance take priority
						solved = true; //once this is found, the fault is found
						replaceFrame = y;
					}
					if(replaceTracker[y] > colTracker) { //if all virtual frames are seen again, the highest column determines fault
						replaceFrame = y;
						colTracker = replaceTracker[y];
					}
				}
			}

			if(replace) {
				table[physicalFrames][i]= "F";
				table[physicalFrames+1][i] = table[replaceFrame][i-1]; //copies last column
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1];
				}
				table[replaceFrame][i] = tokens[i]; //replaces fault row
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1]; //copies last column only when no fault
				}
			}	
		}

		display();

		int faultCount = 0;
		for(int j=0; j<referenceLength; j++) {
			if(table[physicalFrames][j].equals("F")) {
				faultCount++; //tracks total faults
			}
		}
		System.out.format("\nThe total number of faults for this algorithm is: %d\n", faultCount);
	}

	/**
	* Solves demand paging with FIFO algorithm and displays solution at start, middle, and end
	*/
	public static void LRU() {
		table = new String[physicalFrames+2][referenceLength]; //sets size of solution table
		init(); //initializes solution table
		String[] tokens = referenceString.split(" "); //splits reference string into virtual frames
		currentFrames = new String[physicalFrames]; //tracks active virtual frames
		int count=1;
		int column=1;
		currentFrames[0]=tokens[0];
		table[0][0] = tokens[0];
		table[physicalFrames][0] = "F";
		while(count < physicalFrames) { //sets initial physical frames
			String tempString = tokens[column];
			boolean newFrame = true;			
			for(int i=0; i<currentFrames.length; i++) {
				if(tempString.equals(currentFrames[i])) { //checks if virtual frame in reference string is already in physical frames
					newFrame = false;
				}
			}
			if(newFrame) {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column
				}
				table[count][column]=tokens[column]; //adds new virtual frame to open physical frame
				table[physicalFrames][column]="F";
				count++;
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column only when no fault
				}
			}
			column++;
		}
		display();
		System.out.println();

		for(int i=column; i<referenceLength; i++) {
			if(i==(referenceLength+physicalFrames)/2) { //intermediate solution display
				display();
				System.out.println();
			}
			boolean replace = true;
			String tempString = tokens[i];
			for(int j=0; j<physicalFrames; j++) {
				if(tempString.equals(table[j][i-1])) { //checks if virtual frame in reference string is already in physical frames
					replace = false;
				}
			}

			int replaceFrame = 0;
			int[] replaceTracker = new int[physicalFrames]; //look backwards tracker

			for(int j=0; j<physicalFrames; j++) {
				for(int k=0; k<i; k++) {
					if(table[j][i-1].equals(tokens[k])) { //finds when each virtual frame was last used
						replaceTracker[j]=k;
					}
				}
			}
			
			int colTracker = replaceTracker[0];
			for(int y=1; y<physicalFrames; y++) {
				if(replaceTracker[y] < colTracker) { //finds the virtual frame with lowest last column used number
					replaceFrame = y; //sets which frame will fault
					colTracker = replaceTracker[y];
				}
			}

			if(replace) {
				table[physicalFrames][i]= "F";
				table[physicalFrames+1][i] = table[replaceFrame][i-1]; //copies last column
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1];
				}
				table[replaceFrame][i] = tokens[i]; //replaces fault row
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1]; //copies last column only when no fault
				}
			}	
		}

		display();

		int faultCount = 0;
		for(int j=0; j<referenceLength; j++) {
			if(table[physicalFrames][j].equals("F")) {
				faultCount++; //tracks total faults
			}
		}
		System.out.format("\nThe total number of faults for this algorithm is: %d\n", faultCount);
	}

	/**
	* Solves demand paging with FIFO algorithm and displays solution at start, middle, and end
	*/
	public static void LFU() {
		table = new String[physicalFrames+2][referenceLength]; //sets size of solution table
		init(); //initializes solution table
		String[] tokens = referenceString.split(" "); //splits reference string into virtual frames
		currentFrames = new String[physicalFrames]; //tracks active virtual frames
		int count=1;
		int column=1;
		currentFrames[0]=tokens[0];
		table[0][0] = tokens[0];
		table[physicalFrames][0] = "F";
		while(count < physicalFrames) { //sets initial physical frames
			String tempString = tokens[column];
			boolean newFrame = true;			
			for(int i=0; i<currentFrames.length; i++) {
				if(tempString.equals(currentFrames[i])) { //checks if virtual frame in reference string is already in physical frames
					newFrame = false;
				}
			}
			if(newFrame) {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column
				}
				table[count][column]=tokens[column]; //adds new virtual frame to open physical frame
				table[physicalFrames][column]="F";
				count++;
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][column] = table[k][column-1]; //copies last column only when no fault
				}
			}
			column++;
		}
		display();
		System.out.println();

		for(int i=column; i<referenceLength; i++) {
			if(i==(referenceLength+physicalFrames)/2) { //intermediate solution display
				display();
				System.out.println();
			}
			boolean replace = true;
			String tempString = tokens[i];
			for(int j=0; j<physicalFrames; j++) {
				if(tempString.equals(table[j][i-1])) { //checks if virtual frame in reference string is already in physical frames
					replace = false;
				}
			}

			int replaceFrame = 0;
			int[] replaceTracker = new int[physicalFrames]; //tracks the number of instances each virtual frame has already been used
			for(int j=0; j<replaceTracker.length; j++) {
				replaceTracker[j]=0;
			}

			for(int j=0; j<physicalFrames; j++) {
				for(int k=0; k<i; k++) { //look backwards loop
					if(table[j][i-1].equals(tokens[k])) {
						replaceTracker[j]=replaceTracker[j]+1; //if current virtual frame is found, tally is increased
					}
				}
			}
			
			int colTracker = replaceTracker[0];
			for(int y=1; y<physicalFrames; y++) {
				if(replaceTracker[y] < colTracker) { //loops through instance count for each virtual frame
					replaceFrame = y; //determines lowest instance count and sets as fault frame
					colTracker = replaceTracker[y];
				}
			}

			if(replace) {
				table[physicalFrames][i]= "F";
				table[physicalFrames+1][i] = table[replaceFrame][i-1]; //copies last column
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1];
				}
				table[replaceFrame][i] = tokens[i]; //replaces fault row
			}
			else {
				for(int k=0; k<physicalFrames; k++) {
					table[k][i] = table[k][i-1]; //copies last column only when no fault
				}
			}	
		}

		display();

		int faultCount = 0;
		for(int j=0; j<referenceLength; j++) {
			if(table[physicalFrames][j].equals("F")) {
				faultCount++; //tracks total faults
			}
		}
		System.out.format("\nThe total number of faults for this algorithm is: %d\n", faultCount);
	}

	/**
	* formats and displays solution in its current state, allowing display for partial solutions
	*/
	public static void display() {
		try {
			System.out.print("|Reference String | ");
			String[] tokens = referenceString.split(" ");
			for(int i=0; i<referenceLength; i++) {
				System.out.print(tokens[i] + " | ");
			}
			for(int i=0; i<physicalFrames; i++) {
				System.out.format("\n|Physical Frame %d | ", i);
				for(int j=0; j<referenceLength; j++) {
					System.out.print(table[i][j] + " | ");
				}
			}
			System.out.print("\n|Page Faults      | ");
			for(int i=0; i<referenceLength; i++) {
				System.out.print(table[physicalFrames][i] + " | ");
			}
			System.out.print("\n|Victim Frame     | ");
			for(int i=0; i<referenceLength; i++) {
				System.out.print(table[physicalFrames+1][i] + " | ");
			}
			System.out.println();
		}
		catch(Exception e) {
			System.out.println("display error");
		}		
	}

	/**
	* Initializes solution table for formatting purposes
	*/
	public static void init() {
		for(int i=0; i<table.length; i++) {
			for(int j=0; j<table[i].length; j++) {
				table[i][j] = " "; //all solutions are blank spaces initially
			}
		}
	}
}