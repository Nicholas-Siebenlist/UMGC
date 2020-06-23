import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.*; 
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

/**
* Class Description: Forms the main gui for collecting an unsorted list of integers or fractions and calls the appropriate
* classes and methods to perform an ascending or descending sort while confirming the numeric type entered.
* @Author Nicholas Siebenlist
* @Since 11.19.19
* CMSC350 Project3
*/
public class Main extends Application {
	Stage window; //organizes GUI
	Scene scene; //specific window to display infix calculator
	Button button; //allows user to signal infix has been input
	String result; //holds result from infix evaluation

	public static void main(String[] args) {
    		launch(args);
	}

    	@Override
    	public void start(Stage primaryStage) throws Exception {
        	window = primaryStage;
        	window.setTitle("Binary Search Tree Sort");
       		TextField input = new TextField();
		input.setPrefColumnCount(25); //sets size of field for original list entry
		TextField output = new TextField();
		output.setPrefColumnCount(25);
		output.setEditable(false);
        	Label inputLabel = new Label("Original List"); //sets label for input field
		inputLabel.setLabelFor(input);
		Label outputLabel = new Label("Sorted List");
		outputLabel.setLabelFor(output);
        	button = new Button("Perform Sort"); //creates button for performing sort

		GridPane pane = new GridPane(); //grid necessary for structuring visual elements of GUI
		pane.setAlignment(Pos.CENTER);
		pane.setHgap(5);
		pane.setVgap(30); //vertical gap between elements
		pane.setPadding(new Insets(5,5,5,5));

		pane.add(inputLabel, 0, 1);
		pane.add(input, 1, 1);
		pane.add(outputLabel, 0, 2);
		pane.add(output, 1, 2);
		pane.add(button, 1, 3);
		pane.setHalignment(button, HPos.CENTER); //elements are centered

		GridPane p2 = new GridPane();
		Label sortOrder = new Label("Sort Order");
		RadioButton ascending = new RadioButton("Ascending"); //radio buttons created
		RadioButton descending = new RadioButton("Descending");
		p2.add(sortOrder, 0, 1);
		p2.add(ascending, 0, 2);
		p2.add(descending, 0, 3);
		ToggleGroup sortGroup = new ToggleGroup(); //toggle group for sort order created
		ascending.setToggleGroup(sortGroup); //radio buttons added to toggle group
		descending.setToggleGroup(sortGroup);	

		GridPane p3 = new GridPane();
		Label numericType = new Label("Numeric Type");
		RadioButton integer = new RadioButton("Integer");
		RadioButton fraction = new RadioButton("Fraction");
		p3.add(numericType, 0, 1);
		p3.add(integer, 0, 2);
		p3.add(fraction, 0, 3);
		ToggleGroup numericGroup = new ToggleGroup(); //toggle group for numeric type created
		integer.setToggleGroup(numericGroup);
		fraction.setToggleGroup(numericGroup);	

		ascending.setSelected(true); //ascending set as default selection
		integer.setSelected(true); //integer set as default selection

        	button.setOnAction( e -> { //action from clicking on button
			result = binarySort(input.getText(), ascending.isSelected(), integer.isSelected()); 
			output.setText(result); //sets output textfield to show result
		} );
	
		pane.add(p2, 0, 4);
		pane.add(p3, 2, 4);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);

        	scene = new Scene(borderPane, 600, 300);
        	window.setScene(scene);
        	window.show();
    	}

	/**
	* @param input String of original list
	* @param sortAscending boolean value representing sort type
	* @param numericInteger boolean value representing numeric type
	* @return String of sorted values
	*/
    	private String binarySort(String input, boolean sortAscending, boolean numericInteger) {
		String result ="";
		BinarySearchTree tree = new BinarySearchTree(input, sortAscending, numericInteger); //tree is initialized
		try {
			tree.createTree(); //tree is created
			result = tree.sort(); //tree is sorted
		}
		catch(NumberFormatExpression e) {
			AlertBox.display("Error Window", e.getMessage()); //error displayed in alert box
		}
		return result;
	}
}
