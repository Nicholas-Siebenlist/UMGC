/**
* Class Description: Custom class which represents a fraction which is to be used as a data type held by FractionNode
* @Author Nicholas Siebenlist
* @Since 11.21.19
* CMSC350 Project3
*/
public class Fraction implements Comparable<Fraction> {
	private int numerator; //the numerator of the fraction
	private int denominator; //the denominator of the fraction
	private double value; //fraction's numeric value

	public Fraction(String input) throws NumberFormatExpression {
		try {
			int divider = input.indexOf('/');
			numerator = Integer.parseInt(input.substring(0 , divider));
			denominator = Integer.parseInt(input.substring(divider+1));
			value = (Double.valueOf(numerator) / Double.valueOf(denominator));
		}
		catch(Exception e) {
			throw new NumberFormatExpression("Non numeric Input");
		}
	}

	/**
	* Creates a string version of the fraction
	* @return String version of the fraction
	*/
	@Override
	public String toString() {
		String output = (numerator + "/" + denominator);
		return output;
		
	}

	/**
	* Compares two fractions based on their numeric value
	* @return String version of the fraction
	*/
	@Override
	public int compareTo(Fraction f2) {
		int x = 0;
		if(this.getValue() > f2.getValue()) {
			x = 1;
		}
		else if(this.getValue() < f2.getValue()) {
			x = -1;
		}
		else {
			x = 0;
		}
		return x;
	}

	/**
	* Returns numeric value of fraction
	* @return double numeric value of fraction
	*/
	public double getValue() {
		return this.value;
	}
}

	