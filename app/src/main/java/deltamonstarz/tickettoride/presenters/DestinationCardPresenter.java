package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.commands.DrawDestCardsCommand;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.gamePlay.ChooseDestinationDialog;

public class DestinationCardPresenter extends BasePresenter {
	private static DestinationCardPresenter presenter;
	private ChooseDestinationDialog chooseDestinationDialog;

	private DestinationCardPresenter() {}

	public static DestinationCardPresenter getInstance() {
		if (presenter == null) {
			presenter = new DestinationCardPresenter();
		}
		return presenter;
	}

	public void setChooseDestinationDialog(ChooseDestinationDialog chooseDestinationDialog) {
		this.chooseDestinationDialog = chooseDestinationDialog;
	}

	@Override
	public void update(UpdateType updateType) {
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

	public void drawCards() {
		//proxy.sendCommand(model.getAuthToken(), new DrawDestCardsCommand(model.getUsername(), model.getGameID()));
		ArrayList<DestCard> destinationCards = new ArrayList<>();
		destinationCards.add(new DestCard(new City("Dallas"), new City("LA"), 5));
		destinationCards.add(new DestCard(new City("Las Vegas"), new City("New York"), 10));
		onDestinationCardDraw(destinationCards, 2);
	}

	public void reportSelection(List<DestCard> keptCards, List<DestCard> returnedCards) {
		proxy.sendCommand(model.getUsername(), new SelectDestCardsCommand(
				model.getUsername(), model.getGameID(), keptCards, returnedCards
		));
	}

	public void onDestinationCardDraw(ArrayList<DestCard> destinationCards, int minSelection) {
		chooseDestinationDialog.setDestCards(destinationCards, minSelection);
	}
}
