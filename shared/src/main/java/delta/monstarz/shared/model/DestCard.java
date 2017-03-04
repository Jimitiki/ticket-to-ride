package delta.monstarz.shared.model;

/**
 * Created by oliphaun on 2/24/17.
 */

public class DestCard {
    private City city1;
    private City city2;
    private int value;
    private String imageID;


	public DestCard(City city1, City city2, int value) {
		this.city1 = city1;
		this.city2 = city2;
		this.value = value;
	}

	@Override
    public String toString(){
	    String string;
	    string = "(" + String.valueOf(value) + ") " + city1.getName() + " - " + city2.getName();
	    return string;
    }

}
