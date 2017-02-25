package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.Route;
import deltamonstarz.tickettoride.ClientModel;

/**
 * Created by oliphaun on 2/22/17.
 */

public class ClientClaimRouteCommand extends ClaimRouteCommand {
	private Route route;
	private boolean hasLongest;

	public ClientClaimRouteCommand(String username, Route route) {
		super(username, route);
	}

	@Override
    public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.placeRoute(username, route, hasLongest);
		}
	}
}
