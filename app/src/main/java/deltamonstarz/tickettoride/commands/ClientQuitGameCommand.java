package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.QuitGameCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientQuitGameCommand extends QuitGameCommand {

	public ClientQuitGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.removePlayer(username);
		}
	}

}
