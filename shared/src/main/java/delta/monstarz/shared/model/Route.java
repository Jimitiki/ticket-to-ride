package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class Route {
	private int id;
	private int doubleID;
    private City city1;
    private City city2;
    private int length;
    private CardColor color;
    private String owner;
    private PlayerColor trainColor;
    private List<Segment> segments;
	private boolean isClaimed = false;

    public Route(JsonObject jsonRoute) {
	    id = jsonRoute.get("id").getAsInt();
	    doubleID = jsonRoute.get("doubleID").getAsInt();

        //Parse the Segments
        segments = new ArrayList<>();
        if (jsonRoute.has("segments")) {
            JsonArray jsonSegments = jsonRoute.get("segments").getAsJsonArray();
            for (int i = 0; i < jsonSegments.size(); i++) {
	            // TODO: get rid of try-catch when all segments are added to json
	            try {
		            segments.add(new Segment(jsonSegments.get(i).getAsJsonObject()));
	            } catch (Exception e) {}
            }
        }

        //Parse color
	    color = CardColor.fromString(jsonRoute.get("color").getAsString());
        length = jsonRoute.get("length").getAsInt();
    }

    public int getID() {
	    return id;
    }

    int getDoubleID() {
	    return doubleID;
    }

    public List<Segment> getSegments() {
	    return segments;
    }

    public String getOwner() {
	    return owner;
    }

    void setOwner(String owner) {
	    this.owner = owner;
    }

	public PlayerColor getTrainColor() {
		return trainColor;
	}

	public void setTrainColor(PlayerColor trainColor) {
		this.trainColor = trainColor;
	}

	public CardColor getColor() {
	    return color;
    }

    public void setColor(CardColor color) {
	    this.color = color;
    }

    public int getLength() {
	    return length;
    }

	public City getCity1() {
		return city1;
	}

	void setCity1(City city) {
		city1 = city;
	}

	public City getCity2() {
		return city2;
	}

	void setCity2(City city) {
		city2 = city;
	}

	boolean isClaimed() {
		return isClaimed;
	}

	public City getOtherCity(City city) {
		return city.equals(city1) ? city2 : city1;
	}

	public void claim(String username, PlayerColor trainColor) {
		owner = username;
		this.trainColor = trainColor;
		isClaimed = true;
	}

	public boolean verifyCardColorByCount(CardColor cardColor, int cardCount) {
		return (color == CardColor.GOLD || cardColor == CardColor.GOLD ||
				color == cardColor) && cardCount >= length;
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof Route && ((Route) o).getID() == id;
	}
}
