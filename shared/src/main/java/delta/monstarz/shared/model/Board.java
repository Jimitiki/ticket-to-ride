package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

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
}
