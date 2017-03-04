package deltamonstarz.tickettoride;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by Chris on 3/4/17.
 */

public class Poller {
	private ScheduledExecutorService scheduler;
	private static final long POLL_TIME = 400;

	/*
	 * Begins polling the server proxy for commands
	*/
	public void startPoll(Runnable poll) {
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(poll, 0, POLL_TIME, TimeUnit.MILLISECONDS);
	}

	public void endPoll() {
		scheduler.shutdown();
	}
}
