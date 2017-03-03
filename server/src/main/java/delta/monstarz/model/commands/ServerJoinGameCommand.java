package delta.monstarz.model.commands;

import delta.monstarz.services.ServerFacade;
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
