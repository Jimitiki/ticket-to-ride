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
	private int numPlayers;

	private static final int MIN_PLAYERS_DOUBLE_ROUTES = 3;

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

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	//Public Methods
    public void placeRoute(String player_username, Route route, boolean hasLongest) {
        for (Route r : routes) {
			if (r.getID() == route.getID()) {
				r.setOwner(player_username);
				break;
			}
		}
		if (hasLongest) {
			longestRouteOwner = player_username;
		}
    }

    public List<Route> getAvailableRoutes(Player player) {
	    List<Route> availableRoutes = new ArrayList<>();
		for (Route route : routes) {
			CardColor routeColor = route.getColor();

			//check that the route is unclaimed and:
			//  1. the route is gray and the player has enough cards of a single color to claim it, or
			//  2. the player has enough wild cards to claim it, or
			//  3. the player has enough cards of the right color to claim it
			if (!route.isClaimed() && (
					routeColor == CardColor.GOLD && player.getMaxCardColorCount() > route.getLength() ||
					player.getTrainCards().get(CardColor.GOLD) >= route.getLength() ||
					player.getTrainCards().get(routeColor) >= route.getLength())) {
				int doubleID = route.getDoubleID();

				//if its a single route
				if (doubleID == -1) {
					availableRoutes.add(route);
				} else {
					Route doubleRoute = routes.get(doubleID);
					//if the other route is unclaimed, or there are enough players for it to count
					//and the other route is not already claimed by the user
					if (!doubleRoute.isClaimed() || numPlayers > MIN_PLAYERS_DOUBLE_ROUTES &&
							!player.getUsername().equals(doubleRoute.getOwner())) {
						availableRoutes.add(route);
					}
				}
			}
		}
		return availableRoutes;
	}

    public void claimRoute(int routeID, String username, PlayerColor color) {
		routes.get(routeID).claim(username, color);
    }
}
