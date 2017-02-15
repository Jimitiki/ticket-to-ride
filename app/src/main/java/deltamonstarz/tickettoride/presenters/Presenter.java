package deltamonstarz.tickettoride.presenters;

import java.util.Observable;
import java.util.Observer;

import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.IServerProxy;

public abstract class Presenter implements Observer {
	static IServerProxy proxy;
	static ClientModel model;

	Presenter() {
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

	public void endObserve() {
		ClientModel.getInstance().deleteObserver(this);
	}
}
