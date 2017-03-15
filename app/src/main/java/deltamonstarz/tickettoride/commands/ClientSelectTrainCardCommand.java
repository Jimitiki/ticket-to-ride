package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.model.ClientGame;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by oliphaun on 3/15/17.
 */

public class ClientSelectTrainCardCommand extends SelectTrainCardCommand {
	public ClientSelectTrainCardCommand(String username, int gameID, int drawPileID) {
		super(username, gameID, drawPileID);
	}

	@Override
	public void execute() {
		ClientGame game = ClientModel.getInstance().getGame();
		Player me = game.getMe();
		TrainCard cardDrawn = game.drawFaceupTrainCard(cardSpot, replacementCard);
		if (username.equals(me.getUsername()) && cardDrawn != null) {
			me.drawTrainCard(cardDrawn);
		}
	}
}
