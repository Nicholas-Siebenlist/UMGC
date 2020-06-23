/**
* Class Description: This program represents an exception type that is raised when
* topological sorting is performed on an invalid class
* @Author Nicholas Siebenlist
* @Since 12.9.19
* CMSC350 Project4
*/
public class InvalidClassException extends Exception {

	public InvalidClassException() {
		super("Invalid Class Entered");
	}
}