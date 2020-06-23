import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of TwoDimensional shape, rectangle has a length and width which defines its area
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Rectangle extends TwoDimensionalShape {
	Area a;
	double width;
	double length;
	double myArea;

	public Rectangle() {
		super(2);
	}

	/*
	* 
	* @return myArea
	*/
	public double calculateArea(double r, double s) {
		length = r;
		width = s;
		myArea = length * width; //calculates area
		a = new Area(myArea);
 		return myArea;
	}

	//Doesn't apply to rectangle but was inherited from shapes
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