/**
* Class Description: Abstract class defines the methods available to all node types
* @Author Nicholas Siebenlist
* @Since 11.21.19
* CMSC350 Project3
*/
public abstract class Node {
	protected abstract Node getLeft();
	protected abstract Node getRight();
	protected abstract double getValue();
	protected abstract void setLeft(Node n);
	protected abstract void setRight(Node n);
	protected abstract Fraction getFraction();
}