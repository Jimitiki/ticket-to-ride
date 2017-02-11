package deltamonstarz.tickettoride.presenters;

import java.util.Observable;
import java.util.Observer;

import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.IServerProxy;

/**
 * Created by Chris on 2/10/2017.
 */

public abstract class Presenter implements Observer {
	protected IServerProxy proxy;
	protected ClientModel model;

	protected Presenter() {
		model = ClientModel.getInstance();
	}

	public void setProxy(IServerProxy proxy) {
		this.proxy = proxy;
	}

	@Override
	public abstract void update(Observable o, Object arg);

	public void observe() {
		ClientModel.getInstance().addObserver(this);
	}

	public void stopObserve() {
		ClientModel.getInstance().deleteObserver(this);
	}
}
