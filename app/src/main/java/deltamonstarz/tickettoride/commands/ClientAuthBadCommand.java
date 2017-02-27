package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.AuthBadCommand;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by lyman126 on 2/15/17.
 */

public class ClientAuthBadCommand extends AuthBadCommand {

	public ClientAuthBadCommand(String username) {
		super(username);
	}

	@Override
	public void execute() {
		ClientModel.getInstance().getPresenter().logOut();
	}
}
