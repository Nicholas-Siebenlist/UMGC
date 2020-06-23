import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of TwoDimensional shape, square has a length which defines its area
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Square extends TwoDimensionalShape {
	Area a;
	double length; //a single length represents all sides
	double myArea;

	public Square() {
		super(2);
	}

	/*
	* 
	* @return myArea
	*/
	public double calculateArea(double r, double s) {
		length = r;
		myArea = length * length; //calculates area
		a = new Area(myArea);
 		return myArea;
	}

	//Doesn't apply to square but was inherited from shapes
	public double calculateVolume(double r, double s) {
		return 0;
	}

	/*
	* Returns dimensions of the object
	* @return dimensions
	*/
	public int getDimensions() {
		return n.getDimensions();
	}

}