package delta.monstarz.shared.model;

/**
 * Created by oliphaun on 2/24/17.
 */

public class DestCard {
    private City city1;
    private City city2;
    private int value;
    private String imageID;




    @Override
    public String toString(){
	    String string;
	    string = "(" + String.valueOf(value) + ") " + city1.getName() + " - " + city2.getName();
	    return string;
    }

}
