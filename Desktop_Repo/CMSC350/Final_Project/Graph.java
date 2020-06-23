import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
* Class Description: This program represents a graph data type which holds
* vertices as the index in an array list and the edges as a linked list
* which all map to a hash map holding the actual values.
* @Author Nicholas Siebenlist
* @Since 12.9.19
* CMSC350 Project 4
*/
public class Graph {
	private String inputFile; //input file name
	private static ArrayList<LinkedList> arrayList = new ArrayList<LinkedList>(); //holds linked lists which represents edges
	private static HashMap<String, Integer> hashMap = new HashMap<>(); //holds key value pair where key is real name of vertex
	private static Stack<String> stack = null; //used for topological sorting
	private int[][] marked; //tracks which classes have been compiled during sorting
	private static LinkedList<Integer> linkedList; //holds edges before being added to array list of vertices

	/**
	* Constructor which takes input file name
	* @param inputFile file name to be opened
	*/
	public Graph(String inputFile) {
		this.inputFile = inputFile;
	}

	/**
	* Generates graph by reading input file line by line and creating a hash map with edges in numerical form
	* @return boolean value where true represents no error when file was opened and false when file fails to open
	*/
	public boolean buildGraph() {
		BufferedReader reader;
		String[] splitLine = null; //used to split key from rest of line
		String[] stringList = null; //used to split all dependent classes

		try {
			reader = new BufferedReader(new FileReader(inputFile));
			String line = reader.readLine();
			int hashCounter = 0; //tracker used to establish hash map
			int keyCounter; //tracker for hash map value of vertex
			while(line != null) {
				linkedList = new LinkedList<Integer>();
				splitLine = line.split(" ", 2);
				String key = splitLine[0];
				if(hashMap.containsKey(key)) {
					keyCounter = hashMap.get(key); 
					//if key is already in hash map, no new hash map pair must be generated.
				}
				else {
					hashMap.put(key, hashCounter);
					keyCounter = hashCounter;
					hashCounter++; //hash counter must be incremented when a hash pair is added
					arrayList.add(null);
					//dependent classes start with a null linked list that can be replaced later
				}
				if(splitLine.length>1) {
					stringList = splitLine[1].split(" ");
				}
				for(int i=0; i<stringList.length; i++) {
					if(hashMap.containsKey(stringList[i])) {
						addEdge(hashMap.get(stringList[i])); //calls addEdge function to extend linked list
					}
					else {
						hashMap.put(stringList[i], hashCounter);
						addEdge(hashCounter);
						hashCounter++;
						arrayList.add(null);
					}
				}
				arrayList.set(keyCounter, linkedList); //attaches linked list to associated vertex in array list
				line = reader.readLine();
			}
			reader.close();
			return true;
		}
		catch(IOException e) {
			return false; //represents failed graph build
		}
	}

	/**
	* adds edge which is represented as an integer in a linked list
	* @param edge the integer value mapped to a hash map 
	*/
	public static void addEdge(int edge) {
		linkedList.add(edge);
	}

	/**
	* initiates topological sorting based on a starting vertex name
	* @param vertex starting vertex name
	* @return String which is the completed topological order
	*/
	public String startTopology(String vertex) throws CycleDetectedException, InvalidClassException {
		int start = -1; //default invalid starting value
		if(hashMap.containsKey(vertex)) {
			start = hashMap.get(vertex); //confirms vertex name is in hash map
		}
		else {
			throw new InvalidClassException();
		}

		marked = new int[arrayList.size()][2]; //generates new 2D for keeping track of marked and finished compilation
		stack = new Stack<String>(); //refreshes stack with each sort
	
		try {
			buildOrder(start, marked); //calls recursive build
		}
		catch (CycleDetectedException e) {
			throw e;
		}

		String topology = "";
		while(!stack.empty()) { //unpacks stack with reverse topological order
			topology += stack.pop() + " ";
		}
		return topology;
	}

	/**
	* recursively builds compilation order in depth first search order
	* @param vertex integer value of vertex being compiled
	* @param marked the 2D array tracking what has already been compiled to check for cycles
	*/
	private static void buildOrder(int vertex, int[][] marked) throws CycleDetectedException {
		if(marked[vertex][0] == 1) {
			throw new CycleDetectedException();
		}
		if(marked[vertex][1] == 1) {
			return;
		}
		marked[vertex][0] = 1;
		
		if(arrayList.get(vertex) != null) {
			for(Iterator i= arrayList.get(vertex).iterator(); i.hasNext();) {
				buildOrder((int)i.next(), marked);
			}
		}	

		marked[vertex][1] = 1;
		String vertexName = getKey(vertex);
		stack.push(vertexName);
	}

	/**
	* Gets associated vertex name given mapped integer value
	* @param value hash map pair associated value for vertex
	* @return String version of vertex name
	*/
	private static String getKey(int value) {
		String vertexName = "";
		for (Map.Entry<String, Integer> entry : hashMap.entrySet()) { //iterates hash map to find correct pair
			if(value == entry.getValue()) {
				vertexName = entry.getKey();
			}
		}
		return vertexName;
	}
}