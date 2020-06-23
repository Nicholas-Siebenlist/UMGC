import java.util.Scanner;
import java.io.File;
import java.awt.*;
import javax.swing.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

/**
* Class Description: This program parses an input file using recursion to construct a GUI
* determined by the predefined language rules supplied in the assignment.
* @Author Nicholas Siebenlist
* @Since 11.24.19
* CMSC330 Project1
*/
public class Main {
	private static String originalText; //static string used to read in the input file
	private static String remainingText; //static string that holds the entire input file and is parsed as the program progresses

	public static void main(String[] args) {
		try {
			Scanner scanner;
			File inputFile;
			inputFile = new File("Input.txt");
			scanner = new Scanner(inputFile); //scanner for reading in input file
			originalText ="";
			while(scanner.hasNextLine()) {
				originalText += scanner.nextLine().trim() + " ";
			}
			remainingText = originalText.trim(); //removes hanging white space from concatenated text
			JFrame frame = null;
		
			frame = (JFrame)add(frame); //initial recursive call with null frame
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true); //sets frame as visible so it will be displayed
		}
		catch(Exception e) {
			if(e.getMessage() == null) {
				JOptionPane.showMessageDialog(null, "Missing qualifying information");
			}
			JOptionPane.showMessageDialog(null, e.getMessage()); //displays errors for user
		}
	}

	/**
	* Recursively adds widgets to the panels it was called from while parsing the input file string
	* @param currentComponent the frame or panel the next component must be added to
	* @return component of many possible types
	*/
	private static Component add(Component currentComponent) throws Exception {
		String nextWord;
		nextWord = getNextWord(remainingText); //gets the identifying label for parsing text
		try {
			if(nextWord.equals("Window")) {
				JFrame windowFrame;
				String label = getNextString(remainingText);
				windowFrame = new JFrame(label); //sets window title
				String dimensions = getNextDimensions(remainingText);
				ArrayList<Integer> size = getDimensions(dimensions);
				windowFrame.setSize(size.get(0), size.get(1)); //sets window dimensions
				if(currentComponent != null) { //only creates window if the null frame was sent
					throw new Exception("Window can not be nested");
				}
				else {
					JPanel windowPanel = (JPanel)add(windowFrame); //recursive call
					windowFrame.add(windowPanel);  //adds base panel to base frame
				}
				return windowFrame;	
			}
			else if(nextWord.equals("Layout")) {
				String layoutType = getNextLayout(remainingText);
				JPanel panel = setLayout(layoutType); //gets panel/layout type while parsing text
				while(!(previewWord(remainingText).equals("End;") || previewWord(remainingText).equals("End."))) {
					panel = (JPanel)add(panel); //recursive add
				}
				try {
					getNextWord(remainingText); //parses End; away when detected
				}
				catch (Exception e) {
					throw e;
				}
				return panel;
			}
			else if(nextWord.equals("Button")) {
				try {
					String label = getNextString(remainingText);
					JButton jButton = new JButton(label); //creates new button
					JPanel x = (JPanel)currentComponent; //received component is a panel and must be cast as such
					x.add(jButton); 
					return x;
				}
				catch(Exception e) {
					throw new Exception("Error while parsing Button");
				}
			}
			else if(nextWord.equals("Group")) {
				JPanel x = (JPanel)currentComponent;
				ButtonGroup group = new ButtonGroup(); //creates button group for toggling radio buttons
				while(!(previewWord(remainingText).equals("End;"))) {
					JRadioButton y = (JRadioButton)add(x);
					x.add(y); //adds button to frame
					group.add(y); //adds button to group
				}
				try {
					getNextWord(remainingText); //parses End; away when detected
				}
				catch(Exception e) {
					throw new Exception("Error while parsing Group");
				}
				return x;
			}
			else if(nextWord.equals("Label")) {
				try {
					String label = getNextString(remainingText);
					JLabel jLabel = new JLabel(label); //creates new label
					JPanel x = (JPanel)currentComponent; //received component is a panel and must be cast as such
					x.add(jLabel); 
					return x;
				}
				catch(Exception e) {
					throw new Exception("Error while parsing Label");
				}
			}
			else if(nextWord.equals("Panel")) {
				JPanel x = (JPanel)currentComponent;
				x.add(add(x)); //passes panel along as layout is determined
				return x;
			}
			else if(nextWord.equals("Textfield")) {
				try {
					String textLength = getNextWord(remainingText);
					ArrayList<Integer> size = getDimensions(textLength);
					JTextField textField = new JTextField(size.get(0)); //creates new textfield with specified dimension
					JPanel x = (JPanel)currentComponent; //received component is a panel and must be cast as such
					x.add(textField);
					return x;
				}
				catch(Exception e) {
					throw new Exception("Error while parsing Textfield");
				}
			}
			else if(nextWord.equals("Radio")) {
				try {
					String label = getNextString(remainingText);
					JRadioButton radioButton = new JRadioButton(label); //creates new radio button
					return radioButton;
				}
				catch(Exception e) {
					throw new Exception("Error while parsing Button");
				}
			}
			else {
				return null;
			}
		}
		catch(Exception e) {
			throw e;
		}
	}

	/**
	* parses a word from input text
	* @param text the unparsed text
	* @return word removed from text
	*/
	private static String getNextWord(String text) throws Exception {
		try {
			if(text.equals("End.")) {
				return text;
			}
			String[] split = text.split(" ", 2);
			remainingText = split[1].trim();
			if(remainingText.substring(0,1).equals(";")) {
				remainingText = remainingText.substring(1).trim();
			}
			return split[0];
		}
		catch(Exception e) {
			throw new Exception("Qualifying information missing");
		}
	}

	/**
	* previews word in unparsed text
	* @param text the unparsed text
	* @return first word found in unparsed text
	*/
	private static String previewWord(String text) {
		String[] split = text.split(" ", 2);
		return split[0];
	}

	/**
	* gets next String label
	* @param text the unparsed text
	* @return the label found within quotation marks
	*/
	private static String getNextString(String text) throws Exception {
		try {
			remainingText = remainingText.substring(1);
			String[] split = remainingText.split("\"", 2);
			remainingText = split[1];
			if(remainingText.substring(0,1).equals(";")) {
				remainingText = remainingText.substring(1).trim();
			}
			return split[0];
		}
		catch(Exception e) {
			throw new Exception("Expected String");
		}
	}

	/**
	* parses next dimensions
	* @param text the unparsed text
	* @return string that holds the dimensions for specifying calling component
	*/
	private static String getNextDimensions(String text) throws Exception {
		try {
			if(!text.trim().substring(0,1).equals("(")) {
				System.out.println("Here");
				throw new Exception();
			}
			String[] split = text.split("\\)", 2);
			remainingText = split[1].trim();
			return split[0];
		}
		catch(Exception e) {
			throw new Exception("Expected Dimensions");
		}
	}

	/**
	* parses next string of text required for layout description
	* @param text the unparsed text
	* @return string required for layout specifications
	*/
	private static String getNextLayout(String text) throws Exception {
		try {
			String[] split = text.split(":", 2);
			remainingText = split[1].trim();
			return split[0];
		}
		catch(Exception e) {
			throw new Exception("Expected layout type");
		}
	}

	/**
	* returns panel with layout specifications
	* @param text the unparsed text
	* @return the panel of specified type and dimensions
	*/
	private static JPanel setLayout(String text) throws Exception {
		JPanel panel = new JPanel();
		if(text.equals("Flow")) { //flow layout requires no dimensions
			panel.setLayout(new FlowLayout());
		}
		else {
			String[] grid = text.split(" ", 2);
			text = grid[0];
			if(text.equals("Grid")) { 
				GridLayout gridLayout; //sets grid layout type and collects dimensions
				ArrayList<Integer> size = getDimensions(grid[1]);
				if(size.size() == 2) {
					gridLayout = new GridLayout(size.get(0), size.get(1));
				}
				else if(size.size() == 4) {
					gridLayout = new GridLayout(size.get(0), size.get(1), size.get(2), size.get(3));
				}
				else {
					throw new Exception("Expected Layout Dimensions");
				}
				panel.setLayout(gridLayout);
			}
			else {
				throw new Exception("Layout type not specified");
			}
		}
		return panel;
	}

	/**
	* Parses numbers from a string
	* @param text the string to be searched for numbers
	* @return ArrayList of numbers found
	*/
	private static ArrayList<Integer> getDimensions(String text) throws Exception {
		try {
			ArrayList<Integer> size = new ArrayList<Integer>();
			Pattern pattern = Pattern.compile("\\d+"); //regex pattern for greedy digit
        		Matcher matcher = pattern.matcher(text);
        		while(matcher.find()) {
        			int n = Integer.parseInt(matcher.group());
				size.add(n);
        		}
			return size;
		}
		catch(Exception e) {
			throw new Exception("Expected Dimensions");
		}
	}
}