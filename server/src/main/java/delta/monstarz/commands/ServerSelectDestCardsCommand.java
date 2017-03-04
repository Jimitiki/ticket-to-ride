package delta.monstarz.commands;

import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;

/**
 * @author bradcarter
 */
public class ServerSelectDestCardsCommand extends SelectDestCardsCommand
{
	//Constructors
	public ServerSelectDestCardsCommand(String username, int gameID, List<DestCard> selection, List<DestCard> discard)
	{
		super(username, gameID, selection, discard);
	}

	//Object Methods

	//Getters and Setters

	//Public Methods

	@Override
	public void execute()
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);
		for (DestCard card : selection) {
			player.addDestCard(card);
		}
		game.getDestDeck().returnCards(discard);
		//TODO Implement this
	}


	//Internal Methods

}
