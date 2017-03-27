package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Board {

	//Data Members
    private String imageID;
    private List<City> cities;
    private List<Route> routes;
    private String longestRouteOwner;

	//Constructor
	public Board(JsonObject jsonMap, JsonArray jsonRoutes)
	{
		cities = new ArrayList<>();
		routes = new ArrayList<>();
		imageID = jsonMap.get("file").getAsString();
		for (int i = 0; i < jsonRoutes.size(); i++) {
			routes.add(new Route(jsonRoutes.get(i).getAsJsonObject()));
		}
	}

	//Getters and Setters
	public String getImageID()
	{
		return imageID;
	}

	public void setImageID(String pImageID)
	{
		imageID = pImageID;
	}

	public List<City> getCities()
	{
		return cities;
	}

	public void setCities(List<City> pCities)
	{
		cities = pCities;
	}

	public List<Route> getRoutes()
	{
		return routes;
	}

	public void setRoutes(List<Route> pRoutes)
	{
		routes = pRoutes;
	}

	public String getLongestRouteOwner()
	{
		return longestRouteOwner;
	}

	public void setLongestRouteOwner(String pLongestRouteOwner)
	{
		longestRouteOwner = pLongestRouteOwner;
	}

	//Public Methods
    public void placeRoute(String player_username, Route route, boolean hasLongest) {
        for (Route r : routes) {
			if (r.getId() == route.getId()) {
				r.setOwner(player_username);
				break;
			}
		}
		if (hasLongest) {
			longestRouteOwner = player_username;
		}
    }

	public boolean isDestDone(DestCard dest, String username) {
		Set<City> todoSet = new HashSet<>();
		Set<City> doneSet = new HashSet<>();
		todoSet.add( dest.getCity1() );
		while (!todoSet.isEmpty()) {
			Iterator<City> iter = todoSet.iterator();
			City node = iter.next();
			iter.remove();
			doneSet.add(node);
			for (City connected : City.getConnected()) {
				if (connected == dest.getCity2()) {
					return true;
				}
				if (!doneSet.contains(connected)) {
					todoSet.add(connected);
				}
			}
		}
		return false;
	}
}
