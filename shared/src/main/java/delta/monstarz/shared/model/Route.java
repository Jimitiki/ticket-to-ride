package delta.monstarz.shared.model;

import java.util.Collection;

/**
 * Created by oliphaun on 2/24/17.
 */

public class Route {
	private int id; //todo add ids to json
    private City city1;
    private City city2;
    private int length;
    private Enum color;
    private String owner;
    private Collection<Segment> segments;

	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
    public Collection<Segment> getSegments() { return segments; }
    public void setSegments(Collection<Segment> segments) { this.segments = segments; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public Enum getColor() { return color;}
    public void setColor(Enum color) { this.color = color; }
    public int getLength() {return length;}
    public void setLength(int length) { this.length = length;}
    public City getCity2() { return city2;}
    public void setCity2(City city2) {this.city2 = city2;}
    public City getCity1() {return city1;}
    public void setCity1(City city1) { this.city1 = city1;}


}
