package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

	//Data Members
    private String imageID;
    private List<City> cities;
    private Map<Integer, Route> routes;
    private String longestRouteOwner;
	private int numPlayers;

	private static final int MIN_PLAYERS_DOUBLE_ROUTES = 3;

	//Constructor
	public Board(JsonObject jsonMap, JsonArray jsonRoutes, JsonArray jsonCities)
	{
		cities = new ArrayList<>();
		routes = new HashMap<>();
		imageID = jsonMap.get("file").getAsString();
		for (int i = 0; i < jsonCities.size(); i++) {
			City city = new City(jsonCities.get(i).getAsString(), i);
			cities.add(city);
		}
		for (int i = 0; i < jsonRoutes.size(); i++) {
			JsonObject jsonRoute = jsonRoutes.get(i).getAsJsonObject();
			JsonArray endpointArray = jsonRoute.get("endpoints").getAsJsonArray();
			String cityName1 = endpointArray.get(0).getAsString();
			String cityName2 = endpointArray.get(1).getAsString();

			Route route = new Route(jsonRoute);

			routes.put(route.getID(), route);
			for (City city : cities) {
				String cityName = city.getName();
				if (cityName1.equals(cityName) || cityName2.equals(cityName)) {
					if (cityName1.equals(cityName)) {
						route.setCity1(city);
					}
					else {
						route.setCity2(city);
					}
					city.addRoute(route);
				}
			}
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

	public Map<Integer, Route> getRoutes()
	{
		return routes;
	}

	public void setRoutes(Map<Integer, Route> pRoutes)
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

	public Route getRouteByID(int routeID) {
		return routes.get(routeID);
	}

	//Public Methods
    public void placeRoute(String player_username, Route route, boolean hasLongest) {
        for (Route r : routes.values()) {
			if (r.getID() == route.getID()) {
				r.setOwner(player_username);
				break;
			}
		}
		if (hasLongest) {
			longestRouteOwner = player_username;
		}
    }

    //Returns a list of all available routes
    public List<Route> getAvailableRoutes(Player player) {
	    List<Route> availableRoutes = new ArrayList<>();
		for (int i = 0; i < routes.size(); i++) {
			if (isRouteAvailable(i, player)) {
				availableRoutes.add(routes.get(i));
			}
		}
		return availableRoutes;
	}

	public List<Route> getRoutesByCity(City city) {
		List<Route> connectedRoutes = new ArrayList<>();
		for (int routeID : city.getRoutes()) {
			connectedRoutes.add(routes.get(routeID));
		}
		return connectedRoutes;
	}

	//Checks if the player could claim the given route
	private boolean isRouteAvailable(int routeID, Player player) {
		Route route = routes.get(routeID);
		CardColor routeColor = route.getColor();

		return !route.isClaimed() && verifyPlayerCards(route, player.getTrainCards()) &&
			verifyDoubleRouteClaim(route, player.getUsername());
	}

	//checks if the player can claim the given route with the specified card color
    public boolean claimRoute(int routeID, Player player, CardColor color) {
	    Route route = routes.get(routeID);
	    if (!route.isClaimed() && route.verifyCardColor(color, player.getTrainCards().get(color))
			    && verifyDoubleRouteClaim(route, player.getUsername())) {
		    routes.get(routeID).claim(player.getUsername(), player.getPlayerColor());
		    return true;
	    }
	    return false;
    }

    public List<Route> getClaimedRoutes() {
	    List<Route> claimedRoutes = new ArrayList<>();
	    for (Route route : routes.values()) {
		    if (route.isClaimed()) {
			    claimedRoutes.add(route);
		    }
	    }
	    return claimedRoutes;
    }

    //enforces rules about double routes
    private static boolean verifyPlayerCards(Route route, Map<CardColor, Integer> cards) {
	    CardColor routeColor = route.getColor();
	    int routeLength = route.getLength();
	    //check if the player has enough wild cards
	    if (cards.get(CardColor.GOLD) > routeLength) {
		    return true;
	    }
	    //If the route is gray, check if the player has enough cards of any color
	    if (routeColor == CardColor.GOLD) {
		    int maxCardCount = 0;
		    for (int cardCount : cards.values()) {
			    if (cardCount > maxCardCount) {
				    maxCardCount = cardCount;
			    }
		    }
		    if (maxCardCount >= routeLength) {
			    return true;
		    }
	    }
	    //check if the player has enough cards of the routes color
	    return cards.get(routeColor) > routeLength ;
    }

    private boolean verifyDoubleRouteClaim(Route route, String username) {
	    if (route.getDoubleID() == -1) {
		    return true;
	    }
	    Route doubleRoute = routes.get(route.getDoubleID());
	    return (!doubleRoute.isClaimed() || (numPlayers > 3  && !username.equals(doubleRoute.getOwner())));
    }
}
