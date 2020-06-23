import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of TwoDimensional shape, circle has a radius which defines its area
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Circle extends TwoDimensionalShape {
	Area a;
	private double radius;
	private double myArea; //calculates area before creating area object

	public Circle() {
		super(2);
	}

	/*
	* Computes Area from passed parameter
	* @return myArea
	*/
	public double calculateArea(double r, double s) {
		this.radius = r;
		myArea = Math.PI * Math.pow(radius,2); //calculates area
		a = new Area(myArea); //creates Area object
 		return myArea;
	}

	//Doesn't apply to circle but was inherited from shapes
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