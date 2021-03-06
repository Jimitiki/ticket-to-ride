package delta.monstarz.commands;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.TrainCard;

public class ServerSelectTrainCardCommand extends SelectTrainCardCommand {
	public ServerSelectTrainCardCommand(String username, int gameID, int cardSpot) {
		super(username, gameID, cardSpot);
	}

	@Override
	public void execute() {
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);
		TrainCard card = game.getFaceUpCardByPosition(cardSpot);

		if (player.canSelectTrainCard(card)) {
			game.addCommand(this);
			player.selectTrainCard(card);
			game.replaceFaceUpCard(cardSpot);
			replacementCard = game.getFaceUpCardByPosition(cardSpot);

			game.addCommand(new UpdatePlayerInfoCommand(username, gameID, player.playerInfo()));

		}
	}
}
