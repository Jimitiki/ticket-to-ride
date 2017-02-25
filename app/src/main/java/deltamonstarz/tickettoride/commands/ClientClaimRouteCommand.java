package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.Route;

/**
 * Created by oliphaun on 2/22/17.
 */

public class ClientClaimRouteCommand extends ClaimRouteCommand {
	private Route route;
	private boolean hasLongest;

	public ClientClaimRouteCommand(String username, Route route) {
		super(username, route);
	}
}
