package deltamonstarz.tickettoride.presenters;

import java.util.Observable;

import deltamonstarz.tickettoride.views.GameActivity;

public class GamePresenter extends Presenter{
	private GameActivity activity;
	private static GamePresenter presenter;

	private GamePresenter() {
		super();
	}

	public static GamePresenter getInstance() {
		if (presenter == null) {
			presenter = new GamePresenter();
		}
		return presenter;
	}



	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	/**
	 * Removes user from the current game stored in the client model
	 * Switches to GameSelectView
	 */
	public void quitGame() {
		int gameID = model.getGameID();
	}

	/**
	 * Begins polling the server proxy for commands
	 */
	public void pollGameHistory() {
	}

}
