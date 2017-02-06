package delta.monstarz.server.commands;

import delta.monstarz.server.Game;
import delta.monstarz.server.ServerModelManager;
import delta.monstarz.shared.commands.JoinGameCommand;

public class ServerJoinGameCommand extends JoinGameCommand {

	public ServerJoinGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		Game game = ServerModelManager.getInstance().getGameByID(gameID);
		game.addPlayer(username);
	}
}
