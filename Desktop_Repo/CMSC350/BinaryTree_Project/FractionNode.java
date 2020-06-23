/**
* Class Description: Node of type fraction that holds Fractions and their numeric value
* @Author Nicholas Siebenlist
* @Since 11.21.19
* CMSC350 Project3
*/

public class FractionNode extends Node {
	double value;
	Fraction fraction;
	Node left;
	Node right;
	
	public FractionNode(Fraction fraction) {
		this.fraction = fraction;
		value = fraction.getValue();
		left = null;
		right = null;
	}

	public double getValue() {
		return this.value;
	}

	public Node getLeft() {
		return this.left;
	}

	public Node getRight() {
		return this.right;
	}

	public void setLeft(Node left) {
		this.left = left;
	}
	
	public void setRight(Node right) {
		this.right = right;
	}

	public Fraction getFraction() {
		return this.fraction;
	}
}