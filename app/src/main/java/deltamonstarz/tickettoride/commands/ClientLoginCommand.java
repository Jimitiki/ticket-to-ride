package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.LoginCommand;
import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.presenters.GameSelectorPresenter;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

public class ClientLoginCommand extends LoginCommand {
	public ClientLoginCommand(String username) {
		super(username);
	}

	@Override
	public void execute() {
		if (loginSuccessful) {
			ClientModel.getInstance().addLoginInformation(username, authToken);
			ClientModel model = ClientModel.getInstance();
			ServerProxy.getInstance().createGame(model.getUsername(), "yeh", model.getAuthToken());
		} else {
			synchronized (LoginPresenter.getInstance()) {
				LoginPresenter.getInstance().notify();
			}
		}
	}
}
