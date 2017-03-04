package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;
import java.util.Observer;

import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.IServerProxy;
import deltamonstarz.tickettoride.model.UpdateType;

public abstract class BasePresenter implements Observer {
	static IServerProxy proxy;
	static ClientModel model;

	BasePresenter() {
		model = ClientModel.getInstance();
		proxy = ServerProxy.getInstance();
		model.addObserver(this);
	}

	public static void setProxy(IServerProxy serverProxy) {
		proxy = serverProxy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof UpdateType) {
			update((UpdateType) arg);
		}
	}

	public abstract void update(UpdateType updateType);

	public abstract void onConnectionError();

	public void onResume() {
		model.setPresenter(this);
	}

	public abstract void logOut();

	public abstract AppCompatActivity getActivity();
}
