package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.DrawDestCardsCommand;
import deltamonstarz.tickettoride.model.ClientModel;

public class ClientDrawDestCardsCommand extends DrawDestCardsCommand {
	public ClientDrawDestCardsCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.drawDestinationCards(choices, mustKeep);
		}
	}
}
