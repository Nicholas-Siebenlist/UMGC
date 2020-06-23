/**
* Class Description: This program represents an exception type that is raised when
* a cycle is detected during topological sorting.
* @Author Nicholas Siebenlist
* @Since 12.9.19
* CMSC350 Project4
*/
public class CycleDetectedException extends Exception {

	public CycleDetectedException() {
		super("Cycle Detected");
	}
}