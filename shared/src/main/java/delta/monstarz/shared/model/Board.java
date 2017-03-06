package delta.monstarz.shared.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Created by oliphaun on 2/24/17.
 */

public class Board {

	//Data Members
    private String imageID;
    private List<City> cities;
    private List<Route> routes;
    private String longestRouteOwner;

	//Constructor
	public Board()
	{
		cities = new ArrayList<>();
		routes = new ArrayList<>();
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
