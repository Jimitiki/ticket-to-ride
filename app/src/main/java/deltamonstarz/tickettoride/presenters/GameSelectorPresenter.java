package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.views.GameSelectorActivity;

public class GameSelectorPresenter extends BasePresenter {
	private static GameSelectorPresenter presenter;
	private GameSelectorActivity activity;
	private ScheduledExecutorService scheduler;

	private GameSelectorPresenter() {
		super();
	}

	public static GameSelectorPresenter getInstance() {
		if (presenter == null) {
			presenter = new GameSelectorPresenter();
		}
		return presenter;
	}

	public void setActivity(GameSelectorActivity activity) {
		this.activity = activity;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (model.getAuthToken() == null) {
			activity.logout();
			endPoll();
		} else if (model.getGameID() >= 0) {
			activity.onJoinGame();
			endPoll();
		} else {
			activity.onGameListUpdate(model.getAvailableGames());
		}
	}

	/**
	 * Creates a new game
	 * Switches to GameView
	 * @param gameName name of the game to be created
	 */
	public void createGame(String gameName) {
		proxy.createGame(model.getUsername(), gameName, model.getAuthToken());
	}

	/**
	 * Adds user to selected game
	 * Switches to GameView
	 * @param gameID gameID of the game chosen by the uspublic void run() {
			if (proxy == null) {
				proxy = ServerProxy.getInstance();
			}
			proxy.listGames(model.getAuthToken(), model.getUsername());
		}er
	 */
	public void joinGame(int gameID) {
		proxy.joinGame(model.getAuthToken(), Integer.toString(gameID), model.getUsername());
	}

	@Override
	public void onConnectionError() {
		activity.onConnectionError();
	}

	@Override
	public void onResume() {
		super.onResume();
		model.clearGame();
		pollGameList();
	}

	@Override
	public void onPause() {
		super.onPause();
		endPoll();
	}

	@Override
	public void logOut() {
		activity.logout();
	}

	@Override
	public AppCompatActivity getActivity() {
		return activity;
	}

	/**
	 * Begins polling for a list of all current, joinable games
	 */
	public void pollGameList() {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new GamePoller(), 0, 10, TimeUnit.SECONDS);
	}

	private void endPoll() {
		scheduler.shutdown();
	}

	private class GamePoller implements Runnable {
		@Override
		public void run() {
			if (proxy == null) {
				proxy = ServerProxy.getInstance();
			}
			proxy.listGames(model.getAuthToken(), model.getUsername());
		}
	}
}
