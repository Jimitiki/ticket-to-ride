package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.Player;

public class ServerSelectTrainCardCommand extends SelectTrainCardCommand {
	public ServerSelectTrainCardCommand(String username, int gameID, int cardSpot) {
		super(username, gameID, cardSpot);
	}

	@Override
	public void execute() {
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);
		player.selectTrainCard(game.getFaceUpCardByPosition(cardSpot));
		replacementCard = game.replaceFaceUpCard(cardSpot);
		game.addCommand(new UpdatePlayerInfoCommand(username, gameID, player.playerInfo()));
	}
}
