package deltamonstarz.tickettoride.commands;

import java.util.List;

import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by oliphaun on 2/25/17.
 */

public class ClientSelectDestCardsCommand extends SelectDestCardsCommand {
	public ClientSelectDestCardsCommand(String username, int gameID, List<DestCard> selection, List<DestCard> discard) {
		super(username, gameID, selection, discard);
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
