package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.StartGameCommand;
import deltamonstarz.tickettoride.model.ClientGame;
import deltamonstarz.tickettoride.model.ClientModel;

public class ClientStartGameCommand extends StartGameCommand {
	public ClientStartGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (!model.isStarted()) {
			model.startGame(board);
		}
	}
}
