package delta.monstarz.shared.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Map;

public class Board implements Serializable {

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

	private List<Route> getRoutesByCity(City city) {
		List<Route> connectedRoutes = new ArrayList<>();
		for (int routeID : city.getRoutes()) {
			connectedRoutes.add(routes.get(routeID));
		}
		return connectedRoutes;
	}

	public List<Route> getAvailableRoutesForCity(City city, Player player) {
		List<Route> connectedRoutes = getRoutesByCity(city);
		List<Route> availableDestinations = new ArrayList<>();
		for (Route route : connectedRoutes) {
			if (isRouteAvailable(route.getID(), player)) {
				availableDestinations.add(route);
			}
		}
		return availableDestinations;
	}

	//checks if the player can claim the given route with the specified card color
    public boolean claimRoute(int routeID, Player player, CardColor color, int goldCardCount) {
	    Route route = routes.get(routeID);
	    int cardCount = player.getTrainCards().get(color);
	    if (color != CardColor.GOLD) {
		    cardCount += goldCardCount;
	    }
	    if (!route.isClaimed() && player.getNumTrains() >= route.getLength() &&
			    route.verifyCardColorByCount(color, cardCount)
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

	//Checks if the player could claim the given route
	private boolean isRouteAvailable(int routeID, Player player) {
		Route route = routes.get(routeID);
		return !route.isClaimed() && player.getNumTrains() >= route.getLength()
				&& verifyPlayerCards(route, player.getTrainCards()) &&
				verifyDoubleRouteClaim(route, player.getUsername());
	}

    //enforces rules about double routes
    private static boolean verifyPlayerCards(Route route, Map<CardColor, Integer> cards) {
	    CardColor routeColor = route.getColor();
	    int goldCardCount = cards.get(CardColor.GOLD);
	    //If the route is gray, check if the player has enough cards of any color
	    if (routeColor == CardColor.GOLD) {
		    for (CardColor color : CardColor.values()) {
			    int cardCount = color == CardColor.GOLD ? goldCardCount : goldCardCount + cards.get(color);

			    if (route.verifyCardColorByCount(color, cardCount)) {
				    return true;
			    }
		    }
		    return false;
	    }
	    //check if the player has enough cards of the routes color
	    return route.verifyCardColorByCount(routeColor, cards.get(routeColor) + goldCardCount);
    }

    private boolean verifyDoubleRouteClaim(Route route, String username) {
	    if (route.getDoubleID() == -1) {
		    return true;
	    }
	    Route doubleRoute = routes.get(route.getDoubleID());
	    return (!doubleRoute.isClaimed() || (numPlayers > 3  && !username.equals(doubleRoute.getOwner())));
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
			for (int routeID : node.getRoutes()) {
				Route route = routes.get(routeID);
				if (route.getOwner() == null || !route.getOwner().equals(username)) {
					continue;
				}
				City connected = route.getOtherCity(node);
				if ( connected.equals(dest.getCity2()) ) {
					dest.setCompleted(true);
					return true;
				}
				if (!doneSet.contains(connected)) {
					todoSet.add(connected);
				}
			}
		}
		return false;
	}

	public List<Player> findLongestRouteOwners(List<Player> players) { //return a list in case people tie...
		int longestSoFar = 1; //to prevent anyone qualifying as longest with no trains at beginning
		List<Player> longestOwners = new ArrayList<>();
		for (Player player : players) {
			Set<City> ownedCities = new HashSet<>();
			for (Route route : routes.values()) {
				if (route.getOwner() != null && route.getOwner().equals(player.getUsername())) {
					ownedCities.add(route.getCity1());
					ownedCities.add(route.getCity2());
				}
			}
			for (City ownedCity : ownedCities) {
				for (int routeID : ownedCity.getRoutes()) {
					Route route = routes.get(routeID);
					if (route.getOwner() == null || !route.getOwner().equals(player.getUsername()) ) {
						continue;
					}
					List<Route> used = new ArrayList<>();
					used.add(route);
					int longest = recLongest(player.getUsername(), used, ownedCity, route);
					if (longest > longestSoFar) {
						longestSoFar = longest;
						longestOwners = new ArrayList<>();
					}
					if (longest == longestSoFar && !longestOwners.contains(player)) {
						longestOwners.add(player);
					}
				}
			}
		}
		return longestOwners;
	}

	private int recLongest(String username, List<Route> used, City fromCity, Route route) {
		int length = route.getLength();
		int longest = 0;
		City city = route.getOtherCity(fromCity);
		for (int routeID : city.getRoutes()) {
			Route nextRoute = routes.get(routeID);
			if (nextRoute.getOwner() == null || !nextRoute.getOwner().equals(username) || used.contains(nextRoute)) {
				continue;
			}
			List<Route> nextRouteUsed = new ArrayList<>(used);
			nextRouteUsed.add(nextRoute);
			int routeLength = recLongest(username, nextRouteUsed, city, nextRoute);
			if (routeLength > longest) {
				longest = routeLength;
			}
		}
		return longest + length;
	}
}
