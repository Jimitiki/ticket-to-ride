package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class ServerDrawTrainCardCommand extends DrawTrainCardCommand
{
	//Constructors
	public ServerDrawTrainCardCommand(String username, int gameID, int drawPileID)
	{
		super(username, gameID, drawPileID);
	}

	//Object Methods

	//Getters and Setters

	//Public Methods
	@Override
	public void execute()
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		TrainCard card = game.getTrainDeck().drawCard();
		Player player = game.getPlayerByUsername(username);
		player.drawTrainCard(card);
		this.setCardDrawn(card);
	}


	//Internal Methods
}
