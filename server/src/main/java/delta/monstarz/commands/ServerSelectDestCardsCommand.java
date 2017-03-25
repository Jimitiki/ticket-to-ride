package delta.monstarz.commands;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.exceptions.InvalidCommandException;
import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;

/**
 * @author bradcarter
 */
public class ServerSelectDestCardsCommand extends SelectDestCardsCommand
{
	//Constructors
	public ServerSelectDestCardsCommand(String username, int gameID, ArrayList<DestCard> selection, ArrayList<DestCard> discard)
	{
		super(username, gameID, selection, discard);
	}

	@Override
	public void execute() //throws InvalidCommandException
	{
		Game game = GameManager.getInstance().getGameByID(gameID);
		Player player = game.getPlayerByUsername(username);
		player.selectDestinationCards(selection);
		// This is set to null because the server caches the destination cards the player has committed to choose from
		// Once they have made a decision the player no longer is restricted to choosing from the cached cards
		player.setDestCardChoices(null);
		game.getDestDeck().returnCards(discard);
		game.addCommand(new UpdatePlayerInfoCommand(username, gameID, player.playerInfo()) );

		// Save this command
		game.addCommand(this);

		// Check if game is ready to initGame
		if (!game.isPlayHasStarted() && game.gameReadyToStart()){
			game.startFirstTurn();
		}


	}
}
