package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.DrawDestCardsCommand;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;
import deltamonstarz.tickettoride.views.gamePlay.GameFragment;

public class DestinationCardPresenter extends BasePresenter {
	private static DestinationCardPresenter presenter;
	GameFragment gameFragment;

	private DestinationCardPresenter() {};

	public static DestinationCardPresenter getInstance() {
		if (presenter == null) {
			presenter = new DestinationCardPresenter();
		}
		return presenter;
	}

	public void setGameFragment(GameFragment gameFragment) {
		this.gameFragment = gameFragment;
		proxy.sendCommand(model.getAuthToken(), new DrawDestCardsCommand(model.getUsername(), model.getGameID()));
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

	public void reportSelection(List<DestCard> keptCards, List<DestCard> returnedCards) {
		proxy.sendCommand(model.getUsername(), new SelectDestCardsCommand(
				model.getUsername(), model.getGameID(), keptCards, returnedCards
		));
	}

	public void onDestinationCardDraw(ArrayList<DestCard> destinationCards, int minSelection) {
		gameFragment.launchDestinationChooserDialog(destinationCards, minSelection);
	}
}
