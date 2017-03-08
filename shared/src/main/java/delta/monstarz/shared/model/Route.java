package delta.monstarz.shared.model;

import java.util.Collection;
import java.util.List;

import javax.smartcardio.Card;

public class Route {
	private int id; //todo add ids to json
    private String city1;
    private String city2;
    private int length;
    private CardColor color;
    private String owner;
    private PlayerColor trainColor;
    private List<Segment> segments;

    public Route(int pId, String pCity1, String pCity2, int pLength, CardColor pColor, String pOwner, List<Segment> pSegments)
    {
        id = pId;
        city1 = pCity1;
        city2 = pCity2;
        length = pLength;
        color = pColor;
        owner = pOwner;
        segments = pSegments;
    }

    public int getId() {
	    return id;
    }

	public void setId(int id) {
		this.id = id;
	}

    public List<Segment> getSegments() {
	    return segments;
    }

    public void setSegments(List<Segment> segments) {
	    this.segments = segments;
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

    public void setLength(int length) {
	    this.length = length;
    }

    public String getCity2() {
	    return city2;
    }

    public void setCity2(String city2) {
	    this.city2 = city2;
    }

    public String getCity1() {
	    return city1;
    }

    public void setCity1(String city1) {
	    this.city1 = city1;
    }


}
