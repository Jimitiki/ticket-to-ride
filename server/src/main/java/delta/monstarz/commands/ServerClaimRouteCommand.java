package delta.monstarz.commands;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Route;

public class ServerClaimRouteCommand extends ClaimRouteCommand {
	public ServerClaimRouteCommand(String username, int gameID, Route route,CardColor color) {
		super(username, gameID, route, color);
	}

	@Override
	public void execute() {

	}
}
