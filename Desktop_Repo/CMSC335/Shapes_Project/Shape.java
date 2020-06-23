/**
* Class Description: An abstract parent class that defines properties for all shapes to be inherited by TwoDimensionalShapes and ThreeDimensionalShapes
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/

abstract class Shape {
	NumberOfDimensions n;

	public Shape(int dimensions) {
		n = new NumberOfDimensions(dimensions);
	}
		
	abstract int getDimensions();
	abstract double calculateArea(double a, double b);
	abstract double calculateVolume(double a, double b);
}