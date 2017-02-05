package delta.monstarz.server.commands;

import delta.monstarz.shared.commands.JoinGameCommand;

public class ServerJoinGameCommand extends JoinGameCommand {

	public ServerJoinGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
	}
}
