import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;   
import java.awt.geom.*; 
import java.lang.Math;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

/**
* Class Description: Program creates a GUI which simulates a traffic pattern where cars interact with stoplights.
* Cars and lights can be added and removed from the simulation.
* The simulation itself can be paused, resumed, stopped, and started.
* Certain cars will have their position and speed displayed.
* A timestamp is updated every second unless the entire program is stopped.
* @Author Nicholas Siebenlist
* @Since 03.06.20
* CMSC335 Project 3
*/
public class MainFrame extends JFrame {
	private JButton startButton = new JButton("Start");
	private JLabel timestampLabel = new JLabel();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");  
	private LocalDateTime now;
	private static int[] carPositions = new int[]{40, 190, 310, 640, 760, 890}; //starting positions of cars, can be altered
	private static int[] lightPositions = new int[]{200, 400, 600, 800, 1000, 1200}; //light positions (multiply by 5 to convert to meters)
	private static int[] velocities = new int[]{0, 0, 0, 0, 0, 0};
	private JLabel position1Label = new JLabel();
	private JLabel position2Label = new JLabel();
	private JLabel position3Label = new JLabel();
	private static JLabel velocity1Label = new JLabel("0");
	private static JLabel velocity2Label = new JLabel("0");
	private static JLabel velocity3Label = new JLabel("0");
	private static boolean paused = false;
	private static boolean running = false;
	private static boolean stopped = false;
	private static Integer lightStatus = 0;
	private static int timer = 0;
	private static int lightCount = 3; //tracks which lights are active
	private static int carCount = 3; //tracks which cars are displayed
	PaintPanel bottomPanel = new PaintPanel();

	public MainFrame(String title) {
		super(title);
		setLayout(new GridLayout(2,1));

		JPanel topPanel = new JPanel(new GridLayout(1,4)); //top panel holds 4 display ports

		topPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		bottomPanel.setBorder(BorderFactory.createRaisedBevelBorder());

		JPanel leftPanel = new JPanel(new GridLayout(5,1));
		JPanel centerPanel = new JPanel(new GridLayout(2,1));
		JPanel rightPanel = new JPanel(new GridLayout(2,1));
		JPanel additionsPanel = new JPanel(new GridLayout(5,1));

		//creates bevels to improve aesthetic of application				

		leftPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		centerPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		rightPanel.setBorder(BorderFactory.createRaisedBevelBorder());
		additionsPanel.setBorder(BorderFactory.createRaisedBevelBorder());


		//---------------LEFT PANEL------------------
		//contains buttons for simulation controls

		JPanel left1 = new JPanel();
		left1.add(new JLabel("Traffic Simulation Controls"));
		leftPanel.add(left1);

		JPanel left2 = new JPanel();
		left2.add(startButton);
		leftPanel.add(left2);

		JButton stopButton = new JButton("Stop");
		JPanel left3 = new JPanel();
		left3.add(stopButton);
		leftPanel.add(left3);

		JButton pauseButton = new JButton("Pause");
		JPanel left4 = new JPanel();
		left4.add(pauseButton);
		leftPanel.add(left4);

		JButton resumeButton = new JButton("Resume");
		JPanel left5 = new JPanel();
		left5.add(resumeButton);
		leftPanel.add(left5);

		//---------------CENTER PANEL------------------
		//displays timestamps

		JPanel center1 = new JPanel();
		center1.add(new JLabel("Traffic Simulation Timestamp"));
		centerPanel.add(center1);

		JPanel center2 = new JPanel();

		center2.add(timestampLabel);
		centerPanel.add(center2);

		//---------------RIGHT PANEL------------------
		//displays speed and position matrix for cars 1-3

		JPanel carPanel = new JPanel();
		carPanel.add(new JLabel("Car Positions and Speed"));
		rightPanel.add(carPanel);

		JPanel rightContainer = new JPanel(new GridLayout(4,4));

		rightContainer.add(new JLabel("-"));
		rightContainer.add(new JLabel("X-Position"));
		rightContainer.add(new JLabel("Y-Position"));
		rightContainer.add(new JLabel("Speed (m/s)"));

		rightContainer.add(new JLabel("Car 1:"));
		rightContainer.add(position1Label);
		rightContainer.add(new JLabel("0"));
		rightContainer.add(velocity1Label);

		rightContainer.add(new JLabel("Car 2:"));
		rightContainer.add(position2Label);
		rightContainer.add(new JLabel("0"));
		rightContainer.add(velocity2Label);

		rightContainer.add(new JLabel("Car 3:"));
		rightContainer.add(position3Label);
		rightContainer.add(new JLabel("0"));
		rightContainer.add(velocity3Label);

		rightPanel.add(rightContainer);

		//----------------ADDITIONS PANEL-----------------
		//Contains buttons for adding and removing cars / traffic lights

		JPanel temp1 = new JPanel();
		temp1.add(new JLabel("Modify Simulation"));
		additionsPanel.add(temp1);

		JButton addLightButton = new JButton("Add Traffic Light");
		JPanel temp2 = new JPanel();
		temp2.add(addLightButton);
		additionsPanel.add(temp2);

		JButton removeLightButton = new JButton("Remove Traffic Light");
		JPanel temp3 = new JPanel();
		temp3.add(removeLightButton);
		additionsPanel.add(temp3);

		JButton addCarButton = new JButton("Add Car");
		JPanel temp4 = new JPanel();
		temp4.add(addCarButton);
		additionsPanel.add(temp4);

		JButton removeCarButton = new JButton("Remove Car");
		JPanel temp5 = new JPanel();
		temp5.add(removeCarButton);
		additionsPanel.add(temp5);

		//----------------BUTTON LISTENERS----------------

		Task2 task2 = new Task2(); //task2 is responsible for updating car positions and is therefore linked to the start button

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!running) {
					task2.execute();
				}
				running = true;
				stopped = false;
			}
		});

		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				stopped = true; //these boolean values are checked in running threads and can prevent positions from updating
				paused = true;
			}
		});

		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				paused = true;
			}
		});

		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!stopped) {
					paused = false;
				}
			}
		});

		addLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lightCount < 6) {
					lightCount++; //increases the light count up to 6
				}
			}
		});

		removeLightButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(lightCount > 3) {
					lightCount--; //reduces light count but can't go below 3
				}
			}
		});

		addCarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(carCount < 6) {
					carCount++; //increases the car count up to 6
				}
			}
		});

		removeCarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(carCount > 3) {
					carCount--; //reduces car count but can't go below 3
				}
			}
		});


		//----------------THREAD STARTS-----------------------

		Task1 task1 = new Task1(); //thread responsible for timestamps starts immediately
		task1.execute();

		Task3 task3 = new Task3(); //thread responsible for updating traffic lights starts immediately
		//lights only update when program is running though.
		task3.execute();

		//--------------FRAME----------------------
		//combines various panels for final GUI display

		topPanel.add(leftPanel);
		topPanel.add(centerPanel);
		topPanel.add(rightPanel);
		topPanel.add(additionsPanel);

		add(topPanel);
		add(bottomPanel);

		setSize(1400, 400);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	* Class Description: A Thread that updates the timestamp portion of the GUI by publishing LocalDateTimes in the background
	* @Author Nicholas Siebenlist
	* @Since 03.06.20
	* CMSC335 Project 3
	*/
	private class Task1 extends SwingWorker<Void, String> {
		@Override
		protected Void doInBackground() throws Exception {
			while(true) {
				if(!stopped) {	
					now = LocalDateTime.now();
					String x = dtf.format(now).toString();
					publish(x); //Here the timestamps are published
				}
				Thread.sleep(200); //200ms pauses between updates are small enough to not be noticed by user
				//also reduces computational cost on program
			}       
		}

		@Override
		protected void process(List<String> chunks) {
			String value = chunks.get(chunks.size() - 1);
			timestampLabel.setText("Current local time: " + value);
			//the processed time stamps are used to update the label in the GUI
		}

		@Override
		protected void done() {
			try {
			}
			catch (Exception e) {
			}                
		}
	}

	/**
	* Class Description: A Thread that updates the position of each car in the program
	* Before moving each forward, the thread checks the state of the program and the relative position of near cars
	* This prevents cars from crashing into each other given the possibility caused by red lights
	* @Author Nicholas Siebenlist
	* @Since 03.06.20
	* CMSC335 Project 3
	*/
	private class Task2 extends SwingWorker<Void, int[]> {
		@Override
		protected Void doInBackground() throws Exception {
			while(true) {
				if(!paused && !stopped) {
					for(int i=0; i<carPositions.length; i++){
						boolean atLight = false;
						boolean separated = true;
						int tempPos = carPositions[i]+40;
						for(int j=0; j<lightCount;j++) { //checks which lights are currently functional
							if(tempPos==lightPositions[j]) {
								atLight = true;
							}
						}
						int distanceApart = (carPositions[(i+1)%6] - carPositions[i]);
						distanceApart = Math.abs(distanceApart);
						if(distanceApart <=50) { //an arbitrary distance chosen as acceptable between cars
							separated = false;
						}
						if((lightStatus==2 && atLight) || (!separated)) {
							velocities[i] = 0; 
							//if a car is stopped at a light or stopped because it was about to crash
							//then velocity is reduced to 0.
						}
						else {
							velocities[i] = 20; //velocity is set to 20 unless the car is stopped
							carPositions[i]++;
							if(carPositions[i] == 1360) {
								carPositions[i] = 0;
							}
						}
					}
					publish(carPositions);	
				}	
				Thread.sleep(50);						
			}             
		}

		@Override
		protected void process(List<int[]> chunks) { //updates the position and speed matrix
			int[] value = chunks.get(chunks.size() - 1);
			position1Label.setText(Integer.toString((value[0]+40)*5)); 
			position2Label.setText(Integer.toString((value[1]+40)*5));
			position3Label.setText(Integer.toString((value[2]+40)*5));
			velocity1Label.setText(Integer.toString(velocities[0]));
			velocity2Label.setText(Integer.toString(velocities[1]));
			velocity3Label.setText(Integer.toString(velocities[2]));
		}

		@Override
		protected void done() {
			try {
			}
			catch (Exception e) {
			}                
		}
	}

	/**
	* Class Description: A Thread that updates the status of the traffic lights, each of which is coordinated.
	* The timing chosen for the lights was arbitrary, it can be modified in the doInBackground method.
	* @Author Nicholas Siebenlist
	* @Since 03.06.20
	* CMSC335 Project 3
	*/
	private class Task3 extends SwingWorker<Void, Integer> {
		@Override
		protected Void doInBackground() throws Exception {
			Integer lightTemp;
			while(true) {
				if(!paused && running && !stopped) {
					timer += 1;
					Thread.sleep(50);
					if(timer%120 < 70) { //0-70 represents green
						lightTemp = 0;
					}
					else if (timer%120 < 90) { //70-90 represents yellow
						lightTemp = 1;
					}
					else { //90-120 represents red
						lightTemp = 2;
					}
					publish(lightTemp);
				}
				Thread.sleep(50);
			}
		}

		@Override
		protected void process(List<Integer> chunks) {
			Integer lightTemp = chunks.get(chunks.size() - 1);
			lightStatus = lightTemp; //the status of the lights is updated to the GUI
		}

		@Override
		protected void done() {
			try {
			}
			catch (Exception e) {
			}                
		}
	}

	/**
	* Class Description: A panel which draws the positions of car and traffic lights on display based on absolute positioning
	* The status of each light is checked, as is which lights are currently functional and which cars are on display.
	* @Author Nicholas Siebenlist
	* @Since 03.06.20
	* CMSC335 Project 3
	*/
	private class PaintPanel extends JPanel implements ActionListener {
		private Graphics2D g2;
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g2 = (Graphics2D) g;

			for(int i=0; i<lightCount; i++) { //draws traffic light line
				g2.draw(new Line2D.Double(lightPositions[i], 100, lightPositions[i], 200)); 
			}

			for(int i=0; i<carCount; i++) { //draws rectangle representing the car
				g.drawRect(carPositions[i], 140, 40, 20);
			}

			if(lightStatus==0) {
				g2.setColor(Color.GREEN);
			}
			else if(lightStatus==1) {
				g2.setColor(Color.YELLOW);
			}
			else if(lightStatus==2) {
				g2.setColor(Color.RED);
			}

			for(int i=0; i<lightCount; i++) { //draws the circle representing the traffic light
				Ellipse2D light = new Ellipse2D.Double(lightPositions[i]-10, 50, 20, 20);
				g2.fill(light);
			}
			repaint();
		}
		
		public void actionPerformed(ActionEvent e) {
		}
	}
}