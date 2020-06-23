import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

/**
* Class Description: This program defines an alert box to display errors raised during
* graph generation and topological sorting.
* @Author Nicholas Siebenlist
* @Since 12.9.19
* CMSC350 Project4
*/
public class AlertBox {

	/**
	* Creates window and scene for display of alert
	*
	*@param title The title of the alert box
	*@param message The message to be displayed in alert box
	*/
	public static void display(String title, String message) {
        	Stage window = new Stage();
        	window.initModality(Modality.APPLICATION_MODAL);
        	window.setTitle(title);
        	window.setMinWidth(350);
		window.setMinHeight(200);
        	Label label = new Label();
        	label.setText(message);
        	Button closeButton = new Button("Close this window");
        	closeButton.setOnAction(e -> window.close());
        	VBox layout = new VBox(10);
        	layout.getChildren().addAll(label, closeButton);
        	layout.setAlignment(Pos.CENTER);
        	Scene scene = new Scene(layout);
        	window.setScene(scene);
        	window.showAndWait();
    	}
}