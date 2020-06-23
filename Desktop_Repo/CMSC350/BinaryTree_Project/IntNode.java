/**
* Class Description: Node class of type Integer
* @Author Nicholas Siebenlist
* @Since 11.21.19
* CMSC350 Project3
*/

public class IntNode extends Node {
	double value;
	Node left;
	Node right;
	
	public IntNode(double value) {
		this.value = value;
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
		return null;
	}
}