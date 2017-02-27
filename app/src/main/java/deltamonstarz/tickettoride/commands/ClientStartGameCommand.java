package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.StartGameCommand;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.GamePresenter;

public class ClientStartGameCommand extends StartGameCommand {
	public ClientStartGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (!model.isStarted()) {
			model.startGame();
			GamePresenter.getInstance().onGameStart();
		}
	}
}
