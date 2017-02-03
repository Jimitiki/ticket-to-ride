package deltamonstarz.tickettoride;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import delta.monstarz.Command;
import delta.monstarz.IServer;

public class Presenter implements Observer{
	ClientModel model;
	ViewInterface curView;
	IServer proxy;

	@Override
	public void update(Observable o, Object arg) {

	}

	/**
	 * Tells the ServerProxy to create a user with the given username and password
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the new username
	 * @param password the corresponding password
	 * @return true if the user could be created, false if not
	 */
	public boolean register(String username, String password) {
		return false;
	}

	/**
	 * Tells the ServerProxy to authenticate the given user information
	 * If successful, updates the client model and switches to GameSelectView
	 * @param username the username to be validated
	 * @param password the corresponding password
	 * @return true if the credentials were verified, false if not
	 */
	public boolean login(String username, String password) {
		return false;
	}

	/**
	 * Tells the ServerProxy to logout the user
	 * Updates the client model and switched to LoginView
	 */
	public void logout() {

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
	public void joinGame(String gameID) {

	}

	/**
	 * Removes user from the selected game
	 * Switches to GameSelectView
	 * @param gameID
	 */
	public void quitGame(String gameID) {

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


}
