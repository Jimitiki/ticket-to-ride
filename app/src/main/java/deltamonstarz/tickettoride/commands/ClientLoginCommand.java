package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.LoginCommand;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

public class ClientLoginCommand extends LoginCommand {
	public ClientLoginCommand(String username, boolean isRegister) {
		super(username, isRegister);
	}

	@Override
	public void execute() {
		if (loginSuccessful) {
			ClientModel.getInstance().addLoginInformation(username, authToken);
			ClientModel model = ClientModel.getInstance();
		} else {
			if (isRegister) {
				LoginPresenter.getInstance().onRegisterFailed();
			} else {
				LoginPresenter.getInstance().onLoginFailed();
			}
		}
	}
}
