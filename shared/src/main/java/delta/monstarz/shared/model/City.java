package delta.monstarz.shared.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class City implements Serializable {
	private int ID;
    private String name;
	private List<Integer> routeIDs;

	public City(String name, int ID) {
		this.name = name;
		this.ID = ID;
		routeIDs = new ArrayList<>();
	}

	public int getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public List<Integer> getRoutes() {
		return routeIDs;
	}

	void addRoute(Route route) {
		routeIDs.add(route.getID());
	}

	@Override
	public boolean equals(Object o) {
		return o != null && o instanceof City && ((City) o).getID() == ID;
	}

}
