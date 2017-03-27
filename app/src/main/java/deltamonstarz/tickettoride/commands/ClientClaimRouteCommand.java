package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Route;
import deltamonstarz.tickettoride.model.ClientModel;

public class ClientClaimRouteCommand extends ClaimRouteCommand {

	public ClientClaimRouteCommand(String username, int gameID, int routeID, CardColor color, int goldCardCount) {
		super(username, gameID, routeID, color, goldCardCount);
	}

	@Override
    public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.placeRoute(username, routeID);
		}
	}
}
