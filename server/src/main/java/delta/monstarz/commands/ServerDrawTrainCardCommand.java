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
	//Data Members
	private Player player;

	//Constructors
	public ServerDrawTrainCardCommand(String username, int gameID, int drawPileID)
	{
		super(username, gameID, drawPileID);
	}

	public ServerDrawTrainCardCommand(Player p, int gameID, int drawPileID)
	{
		super(p.getUsername(), gameID, drawPileID);
		this.player = p;
	}

	//Object Methods

	//Getters and Setters

	//Public Methods
	@Override
	public void execute()
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		TrainCard card = game.getTrainDeck().drawCard();
		player.drawTrainCard(card);
		this.setCardDrawn(card);
	}


	//Internal Methods
}
