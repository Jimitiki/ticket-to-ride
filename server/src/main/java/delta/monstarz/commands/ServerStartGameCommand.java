package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.services.GameManagementService;
import delta.monstarz.shared.commands.StartGameCommand;

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
		Game game = GameManagementService.getInstance().getGamebyID(gameID);
		board = game.getBoard();

		// Save this command
		game.addCommand(this);
	}
}
