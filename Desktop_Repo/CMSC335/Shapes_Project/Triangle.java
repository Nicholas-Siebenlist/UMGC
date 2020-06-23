import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of TwoDimensional shape, triangle has a base and height which defines its area
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Triangle extends TwoDimensionalShape {
	Area a;
	double base;
	double height;
	double myArea;

	public Triangle() { //constructor sends number of dimensions to shape
		super(2);
	}

	/*
	* Takes input on base and height, checking for correct format, then computes area and displays it
	* @return myArea
	*/
	public double calculateArea(double r, double s) {
		base = r;
		height = s;
		myArea = .5 * base * height; //calculates area
		a = new Area(myArea);
 		return myArea;
	}

	//Doesn't apply to triangle but was inherited from shapes
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