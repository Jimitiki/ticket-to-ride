package delta.monstarz.server;

import java.util.List;

import delta.monstarz.shared.model.IDestinationDeck;
import delta.monstarz.shared.model.DestCard;

/**
 * @author bradcarter
 */
public class DestinationDeck implements IDestinationDeck
{
	@Override
	public List<DestCard> drawCards()
	{
		return null;
	}

	@Override
	public void returnCard(DestCard card)
	{

	}

	@Override
	public void returnCards(List<DestCard> cards) {

	}

	@Override
	public void clear()
	{

	}
}
