package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class DestCard {
    private String city1;
    private String city2;
    private int value;

	public DestCard(JsonObject jsonDestinationCard) {
		JsonArray endpointArray = jsonDestinationCard.get("endpoints").getAsJsonArray();
		city1 = endpointArray.get(0).getAsString();
		city2 = endpointArray.get(1).getAsString();
		value = jsonDestinationCard.get("points").getAsInt();
	}

	@Override
    public String toString() {
	    String string;
	    string = "(" + String.valueOf(value) + ") " + city1 + " - " + city2;
	    return string;
    }

}
