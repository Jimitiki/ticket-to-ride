package delta.monstarz.model.commands;

import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.model.game.manager.PlayerManager;
import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.TrainCard;

/**
 * @author bradcarter
 */
public class ServerDrawTrainCardCommand extends DrawTrainCardCommand
{
	//Data Members
	private Player p;

	//Constructors
	public ServerDrawTrainCardCommand(String username, int gameID, int drawPileID)
	{
		super(username, gameID, drawPileID);
	}

	public ServerDrawTrainCardCommand(Player p, int gameID, int drawPileID)
	{
		super(p.getUsername(), gameID, drawPileID);
		this.p = p;
	}

	//Object Methods

	//Getters and Setters

	//Public Methods
	@Override
	public void execute()
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		TrainCard card = game.getTrainDeck().drawCard();
		p.drawTrainCard(card);
		DrawTrainCardCommand command = new DrawTrainCardCommand(username, gameID, -1);
		command.setCardDrawn(card);
		game.getHistory().add(command);
	}


	//Internal Methods
}
