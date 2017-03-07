package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.commands.DrawDestCardsCommand;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
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
		if (updateType == UpdateType.DRAW_DEST_CARDS) {
			onDestinationCardDraw(model.getDestCardChoices(), model.getMinSelection());
		}
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
		DrawDestCardsCommand command = new DrawDestCardsCommand(model.getUsername(), model.getGameID());
		command.setExpires(true);
		proxy.sendCommand(model.getAuthToken(),command );
	}

	public void reportSelection(List<DestCard> keptCards, List<DestCard> returnedCards) {
		SelectDestCardsCommand command = new SelectDestCardsCommand(
				model.getUsername(),
				model.getGameID(),
				keptCards,
				returnedCards
		);

		proxy.sendCommand(model.getAuthToken(), command);

	}

	private void onDestinationCardDraw(ArrayList<DestCard> destinationCards, int minSelection) {
		chooseDestinationDialog.setDestCards(destinationCards, minSelection);
	}
}
