package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.monstarz.shared.commands.ClaimRouteCommand;
import delta.monstarz.shared.model.Board;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.Route;
import deltamonstarz.tickettoride.model.ClientGame;
import deltamonstarz.tickettoride.model.UpdateType;

public class RoutePresenter extends BasePresenter {
	private ClientGame game;
	private Board board;

	public RoutePresenter() {
		super();
		game = model.getGame();
		board = game.getBoard();
	}

	@Override
	public void update(UpdateType updateType) {

	}

	public List<Route> getAvailableRoutes() {
		return board.getAvailableRoutes(game.getMe());
	}

	public List<Route> getAvailableDestinations(City city) {
		return board.getAvailableRoutesForCity(city, game.getMe());
	}

	public Map<CardColor, Integer> getUsableCards(int routeID) {
		Player player = game.getMe();
		Map<CardColor, Integer> usableCards = new HashMap<>();
		Route route = board.getRouteByID(routeID);
		for (Map.Entry<CardColor, Integer> cardCount : player.getTrainCards().entrySet()) {
			if (route.verifyCardColorByCount(cardCount.getKey(), cardCount.getValue())) {
				usableCards.put(cardCount.getKey(), cardCount.getValue());
			}
		}
		return usableCards;
	}

	public void claimRoute(int routeID, CardColor cardsUsed, int goldCardCount) {
		Player player = game.getMe();
		ClaimRouteCommand command = new ClaimRouteCommand(player.getUsername(), game.getGameID(),
				routeID, cardsUsed, goldCardCount);
		proxy.sendCommand(model.getAuthToken(), command);
	}

	@Override
	public void onConnectionError() {

	}

	@Override
	public void logOut() {

	}

	@Override
	public AppCompatActivity getActivity() {
		return null;
	}
}
