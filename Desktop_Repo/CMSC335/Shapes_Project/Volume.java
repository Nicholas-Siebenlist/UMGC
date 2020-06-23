/**
* Class Description: A property of three dimensional shapes, stores the volume as a double
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/
public class Volume {
	double myVolume;

	public Volume(double v) {
		myVolume = v;
	}

	/*
	* Returns the volume
	* @return myVolume
	*/
	public double getVolume() {
		return myVolume;
	}
}