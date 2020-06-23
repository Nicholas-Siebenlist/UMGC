import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of ThreeDimensionalShape, cone has a radius and height which defines its volume
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Cone extends ThreeDimensionalShape {
	Volume v;
	double radius;
	double height;
	double myVolume;
	Scanner scanner;

	public Cone() {
		super(3);
	}

	//doesn't apply to 3D shapes for this program but was inherited from shapes
	public double calculateArea(double r, double s) {
		return 0;
	}

	/*
	* 
	* @return myVolume
	*/
	public double calculateVolume(double r, double s) {
		radius = r;
		height = s;
		myVolume = Math.PI * Math.pow(radius,2) * height / 3.0; //computes volume
		v = new Volume(myVolume);
 		return myVolume;
	}

	public int getDimensions() {
		return n.getDimensions();
	}

}