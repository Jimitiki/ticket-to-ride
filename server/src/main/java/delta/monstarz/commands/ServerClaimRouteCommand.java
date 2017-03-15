package delta.monstarz.commands;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.Route;

public class ServerClaimRouteCommand extends ClaimRouteCommand {
	public ServerClaimRouteCommand(String username, int gameID, Route r) {
		super(username, gameID, r);
	}

	@Override
	public void execute() {

	}
}
