package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.LogoutCommand;
import deltamonstarz.tickettoride.ClientModel;

public class ClientLogoutCommand extends LogoutCommand {
	public ClientLogoutCommand(String username) {
		super(username);
	}

	@Override
	public void execute() {
		ClientModel.getInstance().removeLoginInformation();
	}
}
