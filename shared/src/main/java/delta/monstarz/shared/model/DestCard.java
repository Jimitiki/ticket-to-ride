package delta.monstarz.shared.model;

import com.google.gson.JsonObject;

public class DestCard {
    private City city1;
    private City city2;
    private int value;
	private boolean isCompleted;

	public DestCard(JsonObject jsonDestinationCard) {
		value = jsonDestinationCard.get("points").getAsInt();
	}

	public DestCard(int points){
		value = points;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean completed) {
		isCompleted = completed;
	}

	@Override
    public String toString() {
	    String string;
	    string = "(" + String.valueOf(value) + ") " + city1.getName() + " - " + city2.getName();
	    return string;
    }

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof DestCard) {
			DestCard card = (DestCard) obj;
			return city1.equals(card.city1) && city2.equals(card.city2);
		}
		return false;
	}

	public City getCity1() {
		return city1;
	}

	public void setCity1(City city1) {
		this.city1 = city1;
	}

	public City getCity2() {
		return city2;
	}

	public void setCity2(City city2) {
		this.city2 = city2;
	}

	public int getValue() {
		return value;
	}
}
