package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.GameListCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientGameListCommand extends GameListCommand {
	public ClientGameListCommand(String username) {
		super(username);
	}

	@Override
	public void execute() {
		ClientModel.getInstance().updateAvailableGames(games);
	}
}
