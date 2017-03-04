package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;

import java.util.Observable;

import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.LoginActivity;

public class LoginPresenter extends BasePresenter {
	private static LoginPresenter presenter;
	private LoginActivity activity;

	private LoginPresenter() {
		super();
	}

	public static LoginPresenter getInstance() {
		if (presenter == null) {
			presenter = new LoginPresenter();
		}
		return presenter;
	}

	public void setActivity(LoginActivity activity) {
		this.activity = activity;
	}

	@Override
	public void update(UpdateType updateType) {
		if (updateType == UpdateType.LOGIN) {
			activity.onLogin();
		}
	}

	public void onConnectionError() {
		activity.onConnectionError();
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to create a user with the given username and password
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the new username
	 * @param password the corresponding password
	 */
	public void register(String ipAddress, String portNum, String username, String password) {
		proxy.initializeServerAddress(ipAddress, portNum);
		proxy.register(username, password);
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to authenticate the given user information
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the username to be validated
	 * @param password the corresponding password
	 */
	public void login(String ipAddress, String portNum, String username, String password) {
		proxy.initializeServerAddress(ipAddress, portNum);
		proxy.login(username, password);
//		createGame();
	}

	public void onLoginFailed() {
		activity.onLoginFailed();
	}

	public void onRegisterFailed() {
		activity.onRegisterFailed();
	}

	@Override
	public void onResume() {
		super.onResume();
		model.clearUser();
	}

	@Override
	public void logOut() {}

	@Override
	public AppCompatActivity getActivity() {
		return activity;
	}
}
