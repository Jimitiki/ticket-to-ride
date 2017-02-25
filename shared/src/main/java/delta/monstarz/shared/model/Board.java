package delta.monstarz.shared.model;

import java.util.Collection;

/**
 * Created by oliphaun on 2/24/17.
 */

public class Board {
    private String imageID;
    private Collection<City> cities;
    private Collection<Route> routes;
    private String longestRouteOwner;

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
