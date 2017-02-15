package deltamonstarz.tickettoride.presenters;

import java.util.Observable;

import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.exceptions.ConnectionException;
import deltamonstarz.tickettoride.views.LoginActivity;

public class LoginPresenter extends Presenter {
	private static LoginPresenter presenter;
	private LoginActivity activity;
	private ServerProxy proxy;

	private LoginPresenter() {
		super();
//		proxy = ServerProxy.getInstance();
//		initializeServerAddress("10.0.2.2", "8080");
//		proxy.login("alex", "alex");
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
	public void update(Observable o, Object arg) {
		if (model.getAuthToken() != null) {
			activity.onLogin();
		} else {
			activity.onLoginFailed();
		}
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to create a user with the given username and password
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the new username
	 * @param password the corresponding password
	 */
	public void register(String ipAddress, String portNum, String username, String password) throws ConnectionException{
		proxy = ServerProxy.getInstance();
		initializeServerAddress(ipAddress, portNum);
		proxy.register(username, password);
		//GameSelectorPresenter.getInstance().pollGameList();
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to authenticate the given user information
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the username to be validated
	 * @param password the corresponding password
	 */
	public void login(String ipAddress, String portNum, String username, String password) throws ConnectionException {
		proxy = ServerProxy.getInstance();
		initializeServerAddress(ipAddress, portNum);
		proxy.login(username, password);
//		createGame();
	}

	private void initializeServerAddress(String ipAddress, String portNum) {
		proxy.setUrl(ipAddress);
		proxy.setPort(portNum);
	}

	private void createGame() {

		proxy.createGame("alex", "alextest", model.getAuthToken());
	}
}
