package delta.monstarz.server;

import java.util.List;

import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.shared.GameInfo;

/**
 * Created by oliphaun on 2/4/17.
 */

public class ServerFacade {
    private static ServerFacade _instance = null;

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
        ServerModelManager model = ServerModelManager.getInstance();
        try {
            return model.register(username, password);
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
        ServerModelManager model = ServerModelManager.getInstance();
        try {
            return model.login(username, password);
        }
        catch (LoginException e){
            return "";
        }
    }

    public int createGame(String username, String game_name, String auth) {
        return 0;
    }

    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
