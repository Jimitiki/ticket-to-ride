package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.LoginCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientLoginCommand extends LoginCommand {
	public ClientLoginCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		if (loginSuccessful) {
			ClientModel model = ClientModel.getInstance();
			model.setAuthToken(authToken);
			model.setUsername(username);
		} else {
			ClientModel.getInstance().notify();
		}
	}
}
