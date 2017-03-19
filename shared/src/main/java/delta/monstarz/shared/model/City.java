package delta.monstarz.shared.model;

import java.util.List;

public class City {
    private String name;
	private List<Route> routes;

	public City(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
