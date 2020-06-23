import java.util.Scanner;
import java.io.*;

/**
* Class Description: A child of ThreeDimensionalShape, cube has a length which defines its volume
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Cube extends ThreeDimensionalShape {
	Volume v;
	double length;
	double myVolume;
	Scanner scanner;

	public Cube() {
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
		length = r;
		myVolume = Math.pow(length,3); //computes volume
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