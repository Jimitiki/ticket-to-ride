package deltamonstarz.tickettoride.presenters;

import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.Poller;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.GameSelectorActivity;

public class GameSelectorPresenter extends BasePresenter {
	private static GameSelectorPresenter presenter;
	private GameSelectorActivity activity;
	private ScheduledExecutorService scheduler;
	private Poller poller;
	private boolean isConnected;

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
	public void update(UpdateType updateType) {
		switch (updateType) {
			case LOGOUT:
				activity.logout();
				break;
			case JOIN_GAME:
				activity.onJoinGame();
				break;
			case GAME_LIST:
				isConnected = true;
				activity.onGameListUpdate(model.getAvailableGames());
				break;
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
		if (isConnected) {
			activity.onGameListUpdate(new ArrayList<GameInfo>());
			activity.onConnectionError();
			isConnected = false;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		model.clearGame();
		if (poller == null) {
			poller = new Poller();
		}
		poller.startPoll(new GamePoller());
	}

	public void onPause() {
		poller.endPoll();
	}

	@Override
	public void logOut() {
		activity.logout();
		model.clearUser();
	}

	@Override
	public AppCompatActivity getActivity() {
		return activity;
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
