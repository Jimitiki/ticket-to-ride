package delta.monstarz.model.game.manager;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;

/**
 * @author bradcarter
 */
public class DestinationCardManager
{
	//Data Members
	private LinkedList<DestCard> deck;

	//Constructor
	public DestinationCardManager(JsonArray jsonDestinationCards, List<City> cities) {
		deck = new LinkedList<>();
		for (int i = 0; i < jsonDestinationCards.size(); i++) {
			JsonObject destCard = jsonDestinationCards.get(i).getAsJsonObject();
			JsonArray endpointArray = destCard.get("endpoints").getAsJsonArray();
			String city1 = endpointArray.get(0).getAsString();
			String city2 = endpointArray.get(1).getAsString();

			DestCard card = new DestCard(destCard);
			card.setCity1(new City(city1, 0));
			card.setCity2(new City(city2, 0));
			deck.add(card);
		}
	}

	public void addCard(DestCard card) {
		deck.add(card);
	}

	/**
	 * Shuffles the deck of destination cards
	 */
	public void shuffle() {
		Collections.shuffle(deck);
	}

	/**
	 * Takes three cards from the destination deck
	 *
	 * @return Three destination cards in a list
	 */
	public ArrayList<DestCard> drawCards() {
		ArrayList<DestCard> cards = new ArrayList<>();
		for(int i = 0; i < 3; i++) {
			cards.add(deck.removeFirst());
		}
		return cards;
	}

	/**
	 * Returns a card to the deck.
	 */
	public void returnCard(DestCard card) {
		deck.addLast(card);
	}

	/**
	 * Returns a list of cards to the deck.
	 */
	public void returnCards(List<DestCard> cards) {
		for(DestCard card : cards) {
			returnCard(card);
		}
	}

	/**
	 * Removes all cards from the deck.
	 */
	public void clear()
	{
		deck = new LinkedList<>();
	}
}
