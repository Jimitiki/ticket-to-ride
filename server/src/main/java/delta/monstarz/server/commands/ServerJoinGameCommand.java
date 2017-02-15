package delta.monstarz.server.commands;

import delta.monstarz.server.Game;
import delta.monstarz.server.ServerFacade;
import delta.monstarz.server.ServerModelManager;
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
		ServerFacade.getInstance().joinGame(username, gameID);
	}
}
