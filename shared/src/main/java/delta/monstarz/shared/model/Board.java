package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	//Checks if the player could claim the given route
	public boolean isRouteAvailable(int routeID, Player player) {
		Route route = routes.get(routeID);
		CardColor routeColor = route.getColor();

		if (!route.isClaimed() && verifyPlayerCards(route, player.getTrainCards())) {
			return verifyDoubleRouteClaim(route, player.getUsername());
		}
		return false;
	}

	//checks if the player can claim the given route with the specified card color
    public boolean claimRoute(int routeID, Player player, CardColor color) {
	    Route route = routes.get(routeID);
	    if (!route.isClaimed() && verifyCardColor(route, color, player.getTrainCards().get(color))
			    && verifyDoubleRouteClaim(route, player.getUsername())) {
		    routes.get(routeID).claim(player.getUsername(), player.getPlayerColor());
		    return true;
	    }
	    return false;
    }

    //Checks if cards and route color/length correspond
    public boolean verifyCardColor(Route route, CardColor cardsUsed, int cardCount) {
		CardColor routeColor = route.getColor();
	    return (cardsUsed == CardColor.GOLD || routeColor == CardColor.GOLD ||
			    routeColor == cardsUsed) && cardCount > route.getLength();
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
		    if (maxCardCount > routeLength ) {
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
	    if (doubleRoute.isClaimed() && (numPlayers < 4 || username.equals(doubleRoute.getOwner()))) {
		    return false;
	    }
	    return true;
    }
}
