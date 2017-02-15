package deltamonstarz.tickettoride.presenters;

import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import delta.monstarz.shared.commands.QuitGameCommand;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.views.GameActivity;

public class GamePresenter extends BasePresenter {
	private GameActivity activity;
	private static GamePresenter presenter;
	private static ScheduledExecutorService scheduler;

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
		QuitGameCommand command = new QuitGameCommand(model.getUsername(), model.getGameID());
		proxy.sendCommand(model.getAuthToken(), command);
	}

	@Override
	public void onConnectionError() {
		activity.onConnectionError();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	/**
	 * Begins polling the server proxy for commands
	 */
	public void PollGameHistory() {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new CommandPoller(), 0, 10, TimeUnit.SECONDS);
	}

	public void endPoll() {
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
