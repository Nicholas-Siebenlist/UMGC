import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of ThreeDimensionalShape, torus has a major and minor radius which defines its volume
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Torus extends ThreeDimensionalShape {
	Volume v; //specific to 3D shapes
	double majorRadius;
	double minorRadius;
	double myVolume;

	public Torus() {
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
		majorRadius = r;
		minorRadius = s;
		myVolume = Math.PI * Math.pow(minorRadius,2) * 2 * Math.PI * majorRadius; //calculates Volume
		v = new Volume(myVolume); //creates new Volume object
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