import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of ThreeDimensionalShape, sphere has a radius which defines its volume
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Sphere extends ThreeDimensionalShape {
	Volume v;
	double radius;
	double myVolume;

	public Sphere() {
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
		myVolume = (4.0 / 3.0) * Math.PI * Math.pow(radius,3); //calculates Volume
		v = new Volume(myVolume);
 		return myVolume;
	}

	/*
	* Returns dimensions of the object
	* @return dimensions
	*/
	public int getDimensions() {
		return n.getDimensions();
	}

}