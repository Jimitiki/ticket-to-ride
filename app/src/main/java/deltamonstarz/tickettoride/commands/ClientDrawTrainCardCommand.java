package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.DrawTrainCardCommand;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by oliphaun on 2/25/17.
 */

public class ClientDrawTrainCardCommand extends DrawTrainCardCommand {
	public ClientDrawTrainCardCommand(String username, int gameID, int drawPileID) {
		super(username, gameID, drawPileID);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			model.drawTrainCard(cardDrawn);
		}
	}
}
