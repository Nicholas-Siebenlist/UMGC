import javax.swing.SwingUtilities;

/**
* Class Description: Controls initiation of traffic simulation program
* @Author Nicholas Siebenlist
* @Since 03.06.20
* CMSC335 Project 3
*/

public class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
		//means of initiating swing worker when desired
		private MainFrame mainFrame;
			@Override
			public void run() {
				mainFrame = new MainFrame("Traffic Simulation Program"); 
				//creates MainFrame class where simulation takes place
			}
		});
	}
}