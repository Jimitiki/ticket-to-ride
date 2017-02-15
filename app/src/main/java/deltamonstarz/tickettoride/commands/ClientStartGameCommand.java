package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.StartGameCommand;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class ClientStartGameCommand extends StartGameCommand {
	public ClientStartGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		GamePresenter.getInstance().onGameStart();
	}
}
