package delta.monstarz.commands;

import java.util.List;

import delta.monstarz.exceptions.InvalidCommandException;
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
	public void execute() //throws InvalidCommandException
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);
		for (DestCard card : selection) {
			//if (! player.getDestCardChoices().contains(card)) {
				//throw new InvalidCommandException();
			//}
			player.addDestCard(card);
		}
		// This is set to null because the server caches the destination cards the player has committed to choose from
		// Once they have made a decision the player no longer is restricted to choosing from the cached cards
		player.setDestCardChoices(null);
		game.getDestDeck().returnCards(discard);
		//TODO Implement this, the client need to be told to draw the cards(send a command). This is because the game will need to sync up later!
	}


	//Internal Methods

}
