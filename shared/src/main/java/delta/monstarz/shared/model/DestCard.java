package delta.monstarz.shared.model;

/**
 * Created by oliphaun on 2/24/17.
 */

public class DestCard {
    private String city1;
    private String city2;
    private int value;

	public DestCard(String pCity1, String pCity2, int pValue)
	{
		city1 = pCity1;
		city2 = pCity2;
		value = pValue;
	}

	@Override
    public String toString(){
	    String string;
	    string = "(" + String.valueOf(value) + ") " + city1 + " - " + city2;
	    return string;
    }

}
