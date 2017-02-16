package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import delta.monstarz.shared.commands.QuitGameCommand;
import delta.monstarz.shared.commands.StartGameCommand;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.views.GameActivity;

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



	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	@Override
	public void update(Observable o, Object arg) {
		activity.onGameUpdate(model.getPlayers());
	}

	/**
	 * Removes user from the current game stored in the client model
	 * Switches to GameSelectView
	 */
	public void quitGame() {
		QuitGameCommand command = new QuitGameCommand(model.getUsername(), model.getGameID());
		proxy.sendCommand(model.getAuthToken(), command);
	}

	public void startGame() {
		StartGameCommand command = new StartGameCommand(model.getUsername(), model.getGameID());
		proxy.sendCommand(model.getAuthToken(), command);
	}

	@Override
	public void onConnectionError() {
		activity.onConnectionError();
	}

	@Override
	public void onResume() {
		super.onResume();
		pollGameHistory();
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

	public void onGameStart() {
		activity.onGameStart();
	}

	/**
	 * Begins polling the server proxy for commands
	 */
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
	}
}
