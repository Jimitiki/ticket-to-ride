package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.concurrent.ScheduledExecutorService;

import delta.monstarz.shared.commands.StartGameCommand;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;

/**
 * javadoc'd by Chris
 *
 * The GamePresenter handles most of the client logic for the game map view.
 * It takes user input from the view and then notifies the server of that input. It
 * watches the client model, and processes any changes made to the model, updating the game view as
 * appropriate. It uses the server proxy and the poller to send any data to the server.
 * @invariant There will never be more than 5 players in a game
 * @invariant There will always be at least 1 player
 *
 */
public class GamePresenter extends BasePresenter {
	private GameActivity activity;
	private static GamePresenter presenter;
	private static ScheduledExecutorService scheduler;
	private static final long POLL_TIME = 400;

	private GamePresenter() {
		super();
	}

	public static GamePresenter getInstance() {
		if (presenter == null) {
			presenter = new GamePresenter();
		}
		return presenter;
	}

	public boolean isGameStarted() {
		return model.isStarted();
	}

	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	/**
	 * This function is called when the client model notifies this class of any changes. It
	 * determines what has changed to update the view accordingly.
	 * @param o The observable that notified its observers
	 * @param arg Any arguments passed by the notify function
	 */
	@Override
	public void update(Observable o, Object arg) {
		activity.onGameUpdate(model.getPlayers());
	}

//	/**
//	 * Removes user from the current game stored in the client model
//	 * Switches to GameSelectView
//	 */
//	public void quitGame() {
//		QuitGameCommand command = new QuitGameCommand(model.getUsername(), model.getGameID());
//		proxy.sendCommand(model.getAuthToken(), command);
//	}

	/**
	 * Indicates to the proxy that the game should be started
	 * @pre There are between 2 and 5 players in the game
	 * @post The game will be started, allowing normal gameplay to occur
	 */
	public void startGame() {
		StartGameCommand command = new StartGameCommand(model.getUsername(), model.getGameID());
		proxy.sendCommand(model.getAuthToken(), command);
	}

	/**
	 * This function is called if the client communicator could not reach the server at the address
	 * given by the user
	 */
	@Override
	public void onConnectionError() {
		activity.onConnectionError();
	}

	/**
	 * The game activity calls this function when it is resumed. It is used to begin polling the
	 * server for game commands and observing the model for changes.
	 */
	@Override
	public void onResume() {
		super.onResume();
		//pollGameHistory();
	}

	/**
	 * The game activity calls this function when it is paused. It is used to stop polling the
	 * server and observing the model
	 */
	@Override
	public void onPause() {
		super.onPause();
		//endPoll();
	}

	/**
	 * retrieves a summary of the other players' states from the model to be displayed by the view.
	 * @return collection of PlayerInfo objects
	 */
//	public List<PlayerInfo> getOpponentInfo() {
//		return null;
//	}

	/**
	 * retrieves the game information of the user from the client model to be displayed by the view.
	 * @return PlayerInfo object
	 */
//	public PlayerInfo getPlayerInfo() {
//		return null;
//	}

	/**
	 * gets all of the routes on the board that have been claimed so the view can draw them.
	 * @return collection of Route objects
	 */
//	public List<Route> getClaimedRoutes() {
//		return null;
//	}

	/**
	 * Removes all game and user data from the client model, resetting the session entirely.
	 */
	@Override
	public void logOut() {
		activity.logout();
	}

	@Override
	public AppCompatActivity getActivity() {
		return activity;
	}

//	public void onGameStart() {
//		activity.onGameStart();
//	}

	/*
	 * Begins polling the server proxy for commands

	private void pollGameHistory() {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new CommandPoller(), 0, POLL_TIME, TimeUnit.MILLISECONDS);
	}

	private void endPoll() {
		scheduler.shutdown();
	}

	private class CommandPoller implements Runnable {
		@Override
		public void run() {
			if (proxy == null) {
				proxy = ServerProxy.getInstance();
			}
			proxy.listCommands(model.getAuthToken(), model.getGameID(), model.getUsername(), model.getCurCommand());
		}
	}*/
}
