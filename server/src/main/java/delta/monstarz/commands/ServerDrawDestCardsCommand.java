package delta.monstarz.commands;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.DrawDestCardsCommand;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;

/**
 * @author bradcarter
 */
public class ServerDrawDestCardsCommand extends DrawDestCardsCommand
{
	public ServerDrawDestCardsCommand(Player pP, int gameID)
	{
		super(pP.getUsername(), gameID);
	}

	@Override
	public void execute() {
		Game game = GameManager.getInstance().getGameByID(gameID);

		DrawDestCardsCommand command = new DrawDestCardsCommand(username, gameID);

		Player player = GameManager.getInstance().getGameByID(gameID).getPlayerByUsername(username);

		ArrayList<DestCard> cards = player.getDestCardChoices();

		if (cards == null ){
			cards = game.getDestDeck().drawCards();
			player.setDestCardChoices(cards);
		}

		command.setChoices(cards);
		command.setExpires(true);

		if (player.getDestCards().size() <= 1){
			command.setMustKeep(2);
		}
		game.addCommand(command);
	}
}
