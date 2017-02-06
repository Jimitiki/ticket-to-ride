package delta.monstarz.server.commands;

import delta.monstarz.server.Game;
import delta.monstarz.server.ServerModelManager;
import delta.monstarz.shared.commands.QuitGameCommand;

public class ServerQuitGameCommand extends QuitGameCommand{

	public ServerQuitGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		Game game = ServerModelManager.getInstance().getGameByID(gameID);
		game.removePlayer(username);
	}
}
