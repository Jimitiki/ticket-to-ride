package deltamonstarz.tickettoride;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.model.player.ClientPlayer;

import static org.junit.Assert.*;

/**
 * Created by Trevor on 4/4/2017.
 */

@RunWith(AndroidJUnit4.class)
public class StateTests {

	TrainCard goldCard;
	TrainCard blueCard;
	TrainCard greenCard;

	Route route0;
	Route route1;

	DestCard destCard1;
	DestCard destCard2;
	DestCard destCard3;
	ArrayList<DestCard> threeDestCards = new ArrayList<>();

	public void setup(){

		// TrainCards
		goldCard = new TrainCard(CardColor.GOLD);
		blueCard = new TrainCard(CardColor.BLUE);
		greenCard = new TrainCard(CardColor.GREEN);

		// Route
		route0 = new Route(0, CardColor.BLACK, 0);
		route1 = new Route(1, CardColor.BLUE, 1);

		// DestCards
		City city1 = new City("city1", 1);
		City city2 = new City("city2", 2);
		City city3 = new City("city3", 3);
		City city4 = new City("city4", 4);
		City city5 = new City("city5", 5);
		City city6 = new City("city6", 6);

		destCard1 = new DestCard(10);
		destCard1.setCity1(city1);
		destCard1.setCity2(city2);

		destCard2 = new DestCard(20);
		destCard2.setCity1(city3);
		destCard2.setCity2(city4);

		destCard3 = new DestCard(30);
		destCard3.setCity1(city5);
		destCard3.setCity2(city6);

		threeDestCards.clear();
		threeDestCards.add(destCard1);
		threeDestCards.add(destCard2);
		threeDestCards.add(destCard3);

	}

	@Test
	public void testSetupState(){
		setup();
		ClientPlayer player = new ClientPlayer("testPlayer");

		assertTrue(player.canDrawTrainCard());
		assertFalse(player.canSelectTrainCard(goldCard));
		assertFalse(player.canSelectTrainCard(blueCard));
		assertFalse(player.canDrawDestinationCard());
		assertFalse(player.canPlaceRoute(route1));
		assertFalse(player.mustDrawDestinationCard());
		assertFalse(player.isTakingTurn());

	}

	@Test
	public void testInactiveState(){
		setup();
		ClientPlayer player = new ClientPlayer("testPlayer");

		player.drawDestinationCards(threeDestCards);
		player.selectDestinationCards(threeDestCards);

		assertFalse(player.canDrawTrainCard());
		assertFalse(player.canSelectTrainCard(goldCard));
		assertFalse(player.canSelectTrainCard(blueCard));
		assertFalse(player.canDrawDestinationCard());
		assertFalse(player.canPlaceRoute(route1));
		assertFalse(player.mustDrawDestinationCard());
		assertFalse(player.isTakingTurn());
	}

	@Test
	public void testPlayerTurnState(){
		setup();
		ClientPlayer player = new ClientPlayer("testPlayer");

		player.drawDestinationCards(threeDestCards);
		player.selectDestinationCards(threeDestCards);
		player.startTurn();

		assertTrue(player.canDrawTrainCard());
		assertTrue(player.canSelectTrainCard(goldCard));
		assertTrue(player.canSelectTrainCard(blueCard));
		assertTrue(player.canDrawDestinationCard());
		assertTrue(player.canPlaceRoute(route0)); // Route0 takes 0 trains
		assertFalse(player.canPlaceRoute(route1)); // Not enough trains
		assertFalse(player.mustDrawDestinationCard());
		assertTrue(player.isTakingTurn());
	}

	@Test
	public void testTrainCardState(){
		setup();
		ClientPlayer player = new ClientPlayer("testPlayer");
	}

	@Test
	public void testDestinationCardState(){
		setup();
		ClientPlayer player = new ClientPlayer("testPlayer");
	}

}
