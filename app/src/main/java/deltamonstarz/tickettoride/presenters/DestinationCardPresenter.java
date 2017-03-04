package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;
import deltamonstarz.tickettoride.views.gamePlay.GameFragment;

public class DestinationCardPresenter extends BasePresenter {
	GameFragment gameFragment;

	@Override
	public void update(Observable o, Object arg) {
//		if (model.getTempDestCards) {
//			gameFragment.launchDestinationChooserDialog()
//		}
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
		proxy.sendCommand(model.getUsername(), new /*SelectDestCardsCommand(model.getUsername(), model.getGameID()T)*/ BaseCommand(model.getUsername(), model.getGameID()));
	}
}
