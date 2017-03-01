package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.SelectDestCardsCommand;
import delta.monstarz.shared.model.DestCard;

/**
 * Created by cwjohn42 on 3/1/17.
 */

public class DestinationCardPresenter extends BasePresenter {
	@Override
	public void update(Observable o, Object arg) {

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

	public void onReturnCards(List<DestCard> keptCards, List<DestCard> returnedCards) {
		proxy.sendCommand(model.getUsername(), new /*SelectDestCardsCommand(model.getUsername(), model.getGameID()T)*/ BaseCommand(model.getUsername(), model.getGameID()));
	}
}
