/**
* Class Description: Custom binary search tree which contains methods to initialize the tree, allows for a new node to be inserted into the tree with recursion, and an in order traversal.
* @Author Nicholas Siebenlist
* @Since 11.21.19
* CMSC350 Project3
*/
public class BinarySearchTree {
	private Node root = null; //only one root which is the starting node
	private String original; //string provided by main gui from user entry
	private static boolean sortAscending; //true if user wants ascended sorted, false for descending
	private static boolean typeInteger; //true if user entered integers, false if user entered fractions

	public BinarySearchTree(String input, boolean sortAscending, boolean typeInteger) {
		this.original = input;
		this.sortAscending = sortAscending;
		this.typeInteger = typeInteger;
	}

	/**
	* sets root of binary tree after recursive add
	* @param root the root node
	*/
	private void setRoot(Node root) {
		this.root = root;
	}

	/**
	* Returns root node
	* @return the root node
	*/	
	public Node getRoot() {
		return this.root;
	}

	/**
	* Recursively parses existing tree to evaluate desired location of new node
	* @param node Node to be added
	* @param currentNode node currently being evaluated against to determine left or right branching
	*/
	private Node addNode(Node node, Node currentNode) {
		if(currentNode == null) {
			return node;
		}
		if(typeInteger) {
			if(node.getValue() <= currentNode.getValue()) {
				currentNode.setLeft(addNode(node, currentNode.getLeft())); //recursive call to add if less than or equal to current node, represents left branching
			}
			if(node.getValue() > currentNode.getValue()) {
				currentNode.setRight(addNode(node, currentNode.getRight())); //recursive call to add if greater than current node, represents right branching
			}
		}
		else {
			if(node.getFraction().compareTo(currentNode.getFraction()) <= 0) {
				currentNode.setLeft(addNode(node, currentNode.getLeft())); //recursive call to add if less than or equal to current node, represents left branching
			}
			if(node.getFraction().compareTo(currentNode.getFraction()) > 0) {
				currentNode.setRight(addNode(node, currentNode.getRight())); //recursive call to add if greater than current node, represents right branching
			}
		}
		return currentNode;
	}

	/**
	* Recursively traverses binary search tree. Considers sort order and numeric type when determining what data and in what order to collect
	* @param myNode current node being parsed
	* @return string sorted values
	*/
	private static String traversal(Node myNode) {
		if(sortAscending) { //evaluates sort type and will perform traversal from left to current to right
			String output ="";
			if(myNode != null) {
				output += traversal(myNode.getLeft());
				if(typeInteger) { //considers numeric type to pull either value or the fraction used
					output += (int)(myNode.getValue()) + " ";
				}
				else {
					output += (myNode.getFraction().toString()) + " "; //fraction toString used here
				}
				output += traversal(myNode.getRight());
			}
			return output;
		}
		else {
			String output ="";
			if(myNode != null) {
				output += traversal(myNode.getRight());
				if(typeInteger) {
					output += (int)(myNode.getValue()) + " ";
				}
				else {
					output += (myNode.getFraction().toString()) + " "; //fraction toString used here
				}
				output += traversal(myNode.getLeft());
			}
			return output;
		}
	}

	/**
	* Public call to add a new node which passes node and pulls root to add recursively
	* @param n Node to be added
	*/
	public void add(Node n) {
		setRoot(addNode(n, this.root));
	}

	/**
	* Creates binary search tree by parsing original list or numbers and added a new node for each value
	*/
	public void createTree() throws NumberFormatExpression {
		original = original.trim();
		String [] numbers = original.split("\\s+"); //splits original string into array and for each value adds a node created of its value
		for(int i=0; i<numbers.length; i++) {
			if(typeInteger) {
				try {
					IntNode node = new IntNode(Double.valueOf(numbers[i])); //creates new IntNode
					add(node);
				}
				catch(Exception e) {
					throw new NumberFormatExpression("Non numeric Input");
				}
			}
			else {
				try {
					FractionNode node = new FractionNode(new Fraction(numbers[i])); //creates new FractionNode
					add(node);
				}
				catch(NumberFormatExpression e) {
					throw e;
				}
			}
		}
	}

	/**
	* Public call to sort the binary search tree and calls the private recursive method
	* @return string sorted values of binary search tree
	*/
	public String sort() {
		String output = traversal(this.root);
		return output;
	}
}