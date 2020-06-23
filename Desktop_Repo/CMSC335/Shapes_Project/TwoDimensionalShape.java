/**
* Class Description: An abstract container for specific two dimensional shapes.
* It is the child of Shape where it has inherited its methods.
* The field Area is specific to TwoDimensionalShapes
* @Author Nicholas Siebenlist
* @Since 02.09.20
* CMSC335 Project 2
*/

abstract class TwoDimensionalShape extends Shape {
	Area a;

	public TwoDimensionalShape(int x) {
		super(x);
	}

	abstract int getDimensions();
	abstract double calculateArea(double a, double b);
	abstract double calculateVolume(double a, double b);	
}