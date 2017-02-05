package delta.monstarz.server.commands;

import delta.monstarz.shared.commands.QuitGameCommand;

public class ServerQuitGameCommand extends QuitGameCommand{

	public ServerQuitGameCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		super.execute();
	}
}
