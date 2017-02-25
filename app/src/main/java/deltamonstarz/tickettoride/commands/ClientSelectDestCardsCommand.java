package deltamonstarz.tickettoride.commands;

import java.util.Collection;

import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.ClientModel;

/**
 * Created by oliphaun on 2/25/17.
 */

public class ClientSelectDestCardsCommand extends SelectDestCardsCommand {
	public ClientSelectDestCardsCommand(String username, int gameID, Collection<DestCard> selection) {
		super(username, gameID, selection);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			for (DestCard card : selection) {
				model.addDestCard(card);
			}
		}
	}
}
