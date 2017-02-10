package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.GameListCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientGameListCommand extends GameListCommand {
	public ClientGameListCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel.getInstance().setAvailableGames(games);
	}
}
