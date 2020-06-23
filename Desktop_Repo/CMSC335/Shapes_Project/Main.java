import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;

/**
* Class Description: Controls shapes GUI layout by creating window and handling shape selection
* Takes input from user once shape has been selected, validates input, displays area/volume
* Author: Nicholas Siebenlist
* Since: 02.09.20
* CMSC 335 Project 2
*/
public class Main {
	private static JFrame jFrame;
	private static JPanel northPanel;
	private static JPanel southPanel;
	private static JPanel westPanel;
	private static JPanel centerPanel;
	private static JPanel eastPanel;
	private static ButtonGroup shapesGroup;
	private static JButton createButton;
	private static Shape shape;
	private static JButton calculateButton;
	private static JTextField outputField;
	private static boolean twoPmt;
	private static JTextField parameter1;
	private static JTextField parameter2;
	private static double value1;
	private static double value2;
	private static boolean isTwoD;

	public static void main(String[] args) {
		jFrame = new JFrame("GUI");
		jFrame.setLayout(new BorderLayout(0, 0));
		jFrame.setSize(700, 350); //window size
		jFrame.setResizable(false);

		northPanel = new JPanel(new FlowLayout());
		southPanel = new JPanel(new FlowLayout());
		westPanel = new JPanel(new GridLayout(12, 0));
		centerPanel = new JPanel(new FlowLayout());
		eastPanel = new JPanel(new GridLayout(11, 1));

//EAST SECTION
		calculateButton = new JButton("Calculate Area / Volume");
		calculateButton.addActionListener(new ButtonListener());
		
//NORTH SECTION
		northPanel.add(new JLabel("*** Welcome to JAVA shapes program ***"));
		jFrame.add(northPanel, BorderLayout.NORTH);

//WEST SECTION
		JRadioButton circleButton = new JRadioButton("Circle", true);
		JRadioButton rectangleButton = new JRadioButton("Rectangle");
		JRadioButton squareButton = new JRadioButton("Square");
		JRadioButton triangleButton = new JRadioButton("Triangle");
		JRadioButton sphereButton = new JRadioButton("Sphere");
		JRadioButton cubeButton = new JRadioButton("Cube");
		JRadioButton coneButton = new JRadioButton("Cone");
		JRadioButton cylinderButton = new JRadioButton("Cylinder");
		JRadioButton torusButton = new JRadioButton("Torus");

		shapesGroup = new ButtonGroup();
		shapesGroup.add(circleButton);
		shapesGroup.add(rectangleButton);
		shapesGroup.add(squareButton);
		shapesGroup.add(triangleButton);
		shapesGroup.add(sphereButton);
		shapesGroup.add(cubeButton);
		shapesGroup.add(coneButton);
		shapesGroup.add(cylinderButton);
		shapesGroup.add(torusButton);

		westPanel.add(circleButton);
		westPanel.add(rectangleButton);
		westPanel.add(squareButton);
		westPanel.add(triangleButton);
		westPanel.add(sphereButton);
		westPanel.add(cubeButton);
		westPanel.add(coneButton);
		westPanel.add(cylinderButton);
		westPanel.add(torusButton);
		westPanel.add(new JLabel(" "));

		createButton = new JButton("Choose Shape");
		westPanel.add(createButton);
		createButton.addActionListener(new ButtonListener());

		jFrame.add(westPanel, BorderLayout.WEST);	

//SOUTH SECTION
		outputField = new JTextField("Output Area", 40);
		southPanel.add(outputField);
		jFrame.add(southPanel, BorderLayout.SOUTH);

//FRAME DISPLAY
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	} //END OF MAIN

	/**
	* Class Description: Internal class that handles button actions, specifically create shape and calculate buttons
	*/
	private static class ButtonListener implements ActionListener {
		private static String selection;

		public void actionPerformed(ActionEvent e) {
			selection = "";
			String imageFile = "";
			String pmt1="";
			String pmt2="";

			if(e.getSource() == createButton) {
				twoPmt = false;
				//determines which radio button is selected
				for(Enumeration<AbstractButton> myButtons = shapesGroup.getElements(); myButtons.hasMoreElements();) {
					AbstractButton button = myButtons.nextElement();
					if(button.isSelected()) {
						selection = button.getText();
					}
				}
				//goes through cases of radio button selection, creates shape, generates image and requests input
				switch(selection) {
					case "Circle":
						shape = new Circle();
						imageFile = "circle.png";
						pmt1 = "Radius";
						twoPmt = false;
						isTwoD = true;
						break;
					case "Rectangle":
						shape = new Rectangle();
						imageFile = "rectangle.png";
						pmt1 = "Length";
						pmt2 = "Width";
						twoPmt = true;
						isTwoD = true;
						break;	
					case "Square":
						shape = new Square();
						imageFile = "square.png";
						pmt1 = "Length";
						twoPmt = false;
						isTwoD = true;
						break;
					case "Triangle":
						shape = new Triangle();
						imageFile = "triangle.png";
						pmt1 = "Base";
						pmt2 = "Height";
						twoPmt = true;
						isTwoD = true;
						break;
					case "Sphere":
						shape = new Sphere();
						imageFile = "sphere.png";
						pmt1 = "Radius";
						twoPmt = false;
						isTwoD = false;
						break;
					case "Cone":
						shape = new Cone();
						imageFile = "cone.png";
						pmt1 = "Radius";
						pmt2 = "Height";
						twoPmt = true;
						isTwoD = false;
						break;	
					case "Cube":
						shape = new Cube();
						imageFile = "cube.png";
						pmt1 = "Length";
						twoPmt = false;
						isTwoD = false;
						break;
					case "Cylinder":
						shape = new Cylinder();
						imageFile = "cylinder.png";
						pmt1 = "Radius";
						pmt2 = "Height";
						twoPmt = true;
						isTwoD = false;
						break;
					case "Torus":
						shape = new Torus();
						imageFile = "torus.png";
						pmt1 = "Major Radius";
						pmt2 = "Minor Radius";
						twoPmt = true;
						isTwoD = false;
						break;
					default:
						break;
				}

//CENTER SECTION
				try{
				//sizes image of shape to be displayed
					BufferedImage myPicture = ImageIO.read(new File(imageFile));
					Image tmp = myPicture.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
       					BufferedImage resized = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);
        				Graphics2D g2d = resized.createGraphics();
        				g2d.drawImage(tmp, 0, 0, null);
        				g2d.dispose();
					myPicture = resized;
					JLabel picLabel = new JLabel(new ImageIcon(myPicture));
					centerPanel.removeAll();
					centerPanel.add(new JLabel("                      ")); //following lines are for spacing purposes
					centerPanel.add(new JLabel("                      "));
					centerPanel.add(new JLabel("                      "));
					centerPanel.add(new JLabel("                      "));
					centerPanel.add(picLabel);
					jFrame.add(centerPanel, BorderLayout.CENTER);
				}
				catch(Exception myException) {
				}

				//EAST SECTION
				// output for parameter requests
				eastPanel.removeAll();
				parameter1 = new JTextField(10);
				parameter2 = new JTextField(10);
	
				String shapeMessage = "Shape selected: " + selection;
				eastPanel.add(new JLabel(shapeMessage));
				eastPanel.add(new JLabel("---------------------"));
				eastPanel.add(new JLabel(" "));
				eastPanel.add(new JLabel(pmt1));
				eastPanel.add(parameter1);
				if(twoPmt) {
					eastPanel.add(new Label(pmt2));
					eastPanel.add(parameter2);
				}
				else {
					eastPanel.add(new JLabel(" "));
					eastPanel.add(new JLabel(" "));
				}				
				eastPanel.add(new Label(" "));
				eastPanel.add(new JLabel("---------------------"));
				eastPanel.add(new JLabel(" "));
				eastPanel.add(calculateButton);

				jFrame.add(eastPanel, BorderLayout.EAST);
				jFrame.revalidate();
			}

			else if(e.getSource() == calculateButton) {
			//validates input and calculates area / volume
				value2 = 0.1;
				double x; //final computed area or volume
				String strDouble;
				try {
					value1 = Double.parseDouble(parameter1.getText());
					if(twoPmt) {
						value2 = Double.parseDouble(parameter2.getText());
					}
					if(value1>0 && value2>0) {
						String fullOutput;
						if(isTwoD) {
							x = shape.calculateArea(value1, value2);
							strDouble = String.format("%.3f", x);
							fullOutput = "The Area is: " + strDouble;
						}
						else {
							x = shape.calculateVolume(value1, value2);
							strDouble = String.format("%.3f", x);
							fullOutput = "The Volume is: " + strDouble;
						}
						outputField.setText(fullOutput);
					}
					else {
						outputField.setText("All entered values must be positive");
					}
				}
				catch (Exception inputException) {
					outputField.setText("Not a valid input, please try again.");
				}
			}
		}
	}		




}