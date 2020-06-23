import javafx.application.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.*; 
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

/**
* Class Description: Main program which handles GUI for graph generation and topological sorting
* @Author Nicholas Siebenlist
* @Since 12.9.19
* CMSC350 Project 4
*/
public class Main extends Application {
	Stage window; //organizes GUI
	Scene scene; //specific window to display infix calculator
	static String result = ""; //holds recompilation string
	static Graph graph = null; //will hold the generated graph

	public static void main(String[] args) {
    		launch(args);
	}

    	@Override
    	public void start(Stage primaryStage) throws Exception {
        	window = primaryStage;
        	window.setTitle("Class Dependency Graph"); //sets window title

       		TextField inputFile = new TextField();
		inputFile.setPrefColumnCount(20); //sets size of field for original list entry

		Label fileLabel = new Label("Input file name:"); //sets label for input file
		fileLabel.setLabelFor(inputFile);

		TextField inputClass = new TextField(); //sets label for class to initiate sorting
		inputClass.setPrefColumnCount(20);

		Label classLabel = new Label("Class to recompile:"); //sets label for input field
		classLabel.setLabelFor(inputClass);

		Text output = new Text();
        	
		Label outputLabel = new Label("Recompilation Order");

		Button buildButton = new Button("Build Directed Graph"); //creates button for performing graph build

        	buildButton.setOnAction( e -> { //action from clicking on button
			buildGraph(inputFile.getText()); //builds graph
			output.setText(""); //resets output when graph is rebuilt
		} );

	       	Button orderButton = new Button("Topological Order"); //creates button for performing sort
        	orderButton.setOnAction( e -> { //action from clicking on button
			topologicalOrder(inputClass.getText()); //generates topological order
			output.setText(result); //sets output textfield to show result
		} );

		GridPane pane = new GridPane(); //grid necessary for structuring visual elements of GUI
		pane.setHgap(5);
		pane.setVgap(15); //vertical gap between elements
		pane.setPadding(new Insets(15,15,15,15));

		pane.add(fileLabel, 0, 0);
		pane.add(inputFile, 1, 0);
		pane.add(buildButton, 2, 0);
		pane.add(classLabel, 0, 1);
		pane.add(inputClass, 1, 1);
		pane.add(orderButton, 2, 1);


		GridPane pane2 = new GridPane(); //grid necessary for structuring visual elements of GUI
		pane2.setHgap(5);
		pane2.setVgap(15); //vertical gap between elements
		pane2.setPadding(new Insets(15,15,15,15));
		pane2.add(outputLabel, 0, 0);
		pane2.add(output, 0, 1);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(pane);
		borderPane.setCenter(pane2);

        	scene = new Scene(borderPane, 600, 300);
        	window.setScene(scene);
        	window.show();
    	}

	/**
	* constructs graph based on information found in text file name provided
	* @param inputFile the string name of file to be opened
	*/
    	private void buildGraph(String inputFile) {
		graph = new Graph(inputFile); //creates new graph
		boolean built = graph.buildGraph(); //checks if build was successful based on return
		AlertBox alertBox = new AlertBox();
		if(built) {
			alertBox.display("Message", "Graph Built Successfully");
		}
		else {
			alertBox.display("Message", "File Did Not Open");	
		}		
	}

	/**
	* generates topological order based on name of initiating vertex
	* @param vertex String name of starting point for recompilation
	*/
	private static void topologicalOrder(String vertex) {
		AlertBox alertBox = new AlertBox();
		try {
			result = graph.startTopology(vertex); //calls graphs initiating function for sorting
		}
		catch(CycleDetectedException e) { //catches cycle exception
			alertBox.display("Message", e.getMessage()); 
		}
		catch(InvalidClassException e) { //catches invalid class exception
			alertBox.display("Message", e.getMessage());
		}
	}
}
