package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class ServerDrawTrainCardCommand extends DrawTrainCardCommand
{

	public ServerDrawTrainCardCommand(String username, int gameID, int drawPileID)
	{
		super(username, gameID);
	}

	@Override
	public void execute()
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);

		if (player.canDrawTrainCard()) {

			TrainCard card = game.getTrainDeck().drawCard();
			player.drawTrainCard(card);
			this.setCardDrawn(card);

			// Make/Add new commands
			game.addCommand(new UpdatePlayerInfoCommand(username, gameID, player.playerInfo()));
			game.addCommand(this);
		}
	}
}
