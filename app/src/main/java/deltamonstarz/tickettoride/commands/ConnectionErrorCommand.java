package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.BasePresenter;

public class ConnectionErrorCommand extends BaseCommand {
	private BasePresenter presenter;

	public ConnectionErrorCommand(String username) {
		super(username);
		presenter = ClientModel.getInstance().getPresenter();
	}

	@Override
	public void execute() {
		presenter.onConnectionError();
		new ClientAuthBadCommand("").execute();
	}
}
