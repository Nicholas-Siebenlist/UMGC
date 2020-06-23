/**
* Class Description: A property of shapes that holds an integer value for the number of dimensions a shape has
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/

public class NumberOfDimensions {
	private int dimensions;

	public NumberOfDimensions(int n) {
		dimensions = n;
	}

	/*
	* Returns the number of dimensions
	* @return dimensions
	*/
	public int getDimensions() {
		return dimensions;
	}
}