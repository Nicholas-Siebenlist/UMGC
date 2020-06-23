/**
* Class Description: Custom exception raised when an improper numeric expression is entered.
* @Author Nicholas Siebenlist
* @Since 11.22.19
* CMSC350 Project3
*/
public class NumberFormatExpression extends Exception { 
	public NumberFormatExpression(String errorMessage) {
        	super("Non Numeric Expression");
    	}
}
