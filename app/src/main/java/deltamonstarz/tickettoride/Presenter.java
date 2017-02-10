package deltamonstarz.tickettoride;

import delta.monstarz.shared.IServer;
import delta.monstarz.shared.commands.JoinGameCommand;

public class Presenter{
	private static Presenter presenter = new Presenter();;
	private ClientModel model;

	private Presenter() {
		model = ClientModel.getInstance();
	}

	public static Presenter getInstance() {
		return presenter;
	}

	public void updateView() {

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
