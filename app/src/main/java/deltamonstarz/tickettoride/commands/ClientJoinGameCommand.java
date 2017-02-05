package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.JoinGameCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientJoinGameCommand extends JoinGameCommand {

	public ClientJoinGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.addPlayer(username);
		}
	}
}
