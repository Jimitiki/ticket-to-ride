package delta.monstarz.model.commands;

import delta.monstarz.services.GameManagementService;
import delta.monstarz.shared.commands.StartGameCommand;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class ServerStartGameCommand extends StartGameCommand {
	public ServerStartGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	/**
	 * Starts a game
	 */
	@Override
	public void execute() {
		GameManagementService.getInstance().startGame(gameID);
	}
}
