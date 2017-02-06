package deltamonstarz.tickettoride;

import java.util.Observable;
import java.util.Observer;

import javax.security.auth.login.LoginException;

import delta.monstarz.shared.IServer;
import delta.monstarz.shared.Person;
import delta.monstarz.shared.commands.JoinGameCommand;
import deltamonstarz.tickettoride.views.BaseView;

public class Presenter implements Observer{
	private ClientModel model;
	private BaseView curView;
	private IServer proxy;

	public Presenter() {
		model = ClientModel.getInstance();
	}

	@Override
	public void update(Observable o, Object arg) {

	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to create a user with the given username and password
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the new username
	 * @param password the corresponding password
	 * @return true if the user could be created, false if not
	 */
	public void register(String ipAddress, String portNum, String username, String password) {
		proxy = ServerProxy.getInstance(ipAddress, portNum);
//		try {
			model.setAuthToken(proxy.register(new Person(username, password)));
//		} catch (LoginException e) {
//
//		}
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to authenticate the given user information
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the username to be validated
	 * @param password the corresponding password
	 * @return true if the credentials were verified, false if not
	 */
	public void login(String ipAddress, String portNum, String username, String password) {
		proxy = ServerProxy.getInstance(ipAddress, portNum);
		model.setAuthToken(proxy.login(new Person(username, password)));
	}

	/**
	 * Tells the deltamonstarz.tickettoride.ServerProxy to logout the user
	 * Updates the client model and switched to LoginView
	 */
	public void logout() {
		model.setAuthToken(null);
	}

	/**
	 * Creates a new game
	 * Switches to GameView
	 * @param gameName name of the game to be created
	 */
	public void createGame(String gameName) {

	}

	/**
	 * Adds user to selected game
	 * Switches to GameView
	 * @param gameID gameID of the game chosen by the user
	 */
	public void joinGame(int gameID) {
		JoinGameCommand command = new JoinGameCommand(model.getUsername(), model.getGameID());
	}

	/**
	 * Removes user from the current game stored in the client model
	 * Switches to GameSelectView
	 */
	public void quitGame() {
		int gameID = model.getGameID();
		//ServerProxy.qu();
	}

	/**
	 * Begins polling the server proxy for a list of all current, joinable games
	 */
	public void pollGameList() {

	}

	/**
	 * Begins polling the server proxy for commands
	 */
	public void pollGameHistory() {
	}

	public BaseView getCurView() {
		return curView;
	}

	public void setCurView(BaseView view) {
		curView = view;
	}
}
