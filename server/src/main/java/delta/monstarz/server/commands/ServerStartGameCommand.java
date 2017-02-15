package delta.monstarz.server.commands;

import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.commands.StartGameCommand;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class ServerStartGameCommand extends StartGameCommand {
	public ServerStartGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ServerFacade.getInstance().startGame(gameID);
	}
}
