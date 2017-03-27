package delta.monstarz.commands;

import delta.monstarz.model.CommandManager;
import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Route;

public class ServerClaimRouteCommand extends ClaimRouteCommand {
	public ServerClaimRouteCommand(String username, int gameID, int routeID, CardColor color, int goldCardCount) {
		super(username, gameID, routeID, color, goldCardCount);
	}

	@Override
	public void execute() {
		Game game = GameManager.getInstance().getGameByID(gameID);
		if (!game.claimRoute(routeID, username, cardsUsed, goldCardCount)) {
			System.out.print("could not claim route");
		} else {
			Player player = game.getPlayerByUsername(username);
			game.addCommand(this);
			game.addCommand(new UpdatePlayerInfoCommand(username, gameID, player.playerInfo()));
		}
	}
}
