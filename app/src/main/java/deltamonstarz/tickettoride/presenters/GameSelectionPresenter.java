package deltamonstarz.tickettoride.presenters;

import java.util.Observable;
import java.util.Observer;

import deltamonstarz.tickettoride.Presenter;
import deltamonstarz.tickettoride.views.GameSelectorActivity;

/**
 * @author bradcarter
 */
public class GameSelectionPresenter implements Observer {
	private static GameSelectionPresenter presenter;
	private GameSelectorActivity activity;

	public static GameSelectionPresenter getInstance() {
		return presenter;
	};

	/**
	 * Begins polling the server proxy for a list of all current, joinable games
	 */
	public void pollGameList() {

	}

	@Override
	public void update(Observable pObservable, Object pO) {

	}
}
