package delta.monstarz.model.commands;

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
	private Player p;

	public ServerDrawDestCardsCommand(Player pP, int gameID)
	{
		super(pP.getUsername(), gameID);
		p = pP;
	}

	@Override
	public void execute() {
		Game game = GameManager.getInstance().getGameByID(gameID);
		ArrayList<DestCard> cards = game.getDestDeck().drawCards();
		DrawDestCardsCommand command = new DrawDestCardsCommand(username, gameID);
		command.setChoices(cards);
		game.getHistory().add(command);
	}
}
