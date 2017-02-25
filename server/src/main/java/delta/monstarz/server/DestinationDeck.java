package delta.monstarz.server;

import java.util.List;

import delta.monstarz.shared.IDestinationDeck;

/**
 * @author bradcarter
 */
public class DestinationDeck implements IDestinationDeck
{
	@Override
	public List<DestinationCard> drawCards()
	{
		return null;
	}

	@Override
	public void returnCard(DestinationCard card)
	{

	}

	@Override
	public void clear()
	{

	}
}
