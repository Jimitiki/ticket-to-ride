package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.StartGameCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.tasks.DemoAsyncTask;

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
