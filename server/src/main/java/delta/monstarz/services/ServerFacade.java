package delta.monstarz.services;

import java.util.List;

import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.model.GameManager;
import delta.monstarz.model.account.PersonManager;
import delta.monstarz.shared.GameInfo;

/**
 * Created by oliphaun on 2/4/17.
 */

public class ServerFacade {
    private static ServerFacade _instance = null;
	private static PersonManager personManager = PersonManager.getInstance();
	private static GameManager gameManager = GameManager.getInstance();

    public static ServerFacade getInstance() {
        if (_instance == null) {
            _instance = new ServerFacade();
        }
        return _instance;
    }

    /**
     * A new user account is made
     * Each username must be unique
     * @param username
     * @param password
     * @return An authToken which will identify the current session for the user
     */
    public String register(String username, String password) {
        try {
            return personManager.register(username, password);
        }
        catch (LoginException e){
            return "";
        }
    }

    /**
     * Users can login using a username and password stored in peep.
     * @param username
     * @param password
     * @return A new auth token for the user.
     */
    public String login(String username, String password) {
        try {
            return personManager.login(username, password);
        }
        catch (LoginException e){
            return "";
        }
    }

    public int createGame(String username, String game_name) {
        return gameManager.createGame(username, game_name);
    }

    public List<GameInfo> listGames(String username) {
        List<GameInfo> games = gameManager.getGamesIn(username);
        games.addAll(gameManager.getJoinableGames(username));
        return games;
    }

    public void joinGame(String username, int gameID) {
	    gameManager.joinGame(username, gameID);
    }

	public boolean gameExists(int gameID) {
		if (gameManager.getGameByID(gameID) != null) {
			return true;
		}
		return false;
	}

	public boolean personExists(String username) {
		if (personManager.getPersonForUsername(username) != null) {
			return true;
		}
		return false;
	}

	public void startGame(int gameID) {
		gameManager.startGame(gameID);
	}
}
