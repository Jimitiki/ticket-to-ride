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
			String cityName1 = endpointArray.get(0).getAsString();
			String cityName2 = endpointArray.get(1).getAsString();

			City city2 = null;

			DestCard card = new DestCard(destCard);
			for (City city : cities) {
				if (city.getName().equals(cityName1)) {
					card.setCity1(city);
				}
				else if (city.getName().equals(cityName2)) {
					card.setCity2(city);
				}
			}
			deck.add(card);
		}
		shuffle();
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
		int drawCardCount = deck.size() < 3 ? deck.size() : 3;
		for(int i = 0; i < drawCardCount; i++) {
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
