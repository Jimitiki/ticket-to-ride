package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.services.GameManagementService;
import delta.monstarz.shared.commands.JoinGameCommand;

public class ServerJoinGameCommand extends JoinGameCommand {

	public ServerJoinGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	public ServerJoinGameCommand(JoinGameCommand command) {
		super(command.getUsername(), command.getGameID());
	}

	@Override
	public void execute() {
		GameManagementService.getInstance().joinGame(username, gameID);

		// Save this command
		Game game = GameManager.getInstance().getGameByID(gameID);
		game.addCommand(this);
	}
}
