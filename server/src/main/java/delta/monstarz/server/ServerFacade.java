package delta.monstarz.server;

import java.util.List;

import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.IServer;
import delta.monstarz.shared.Person;

/**
 * Created by oliphaun on 2/4/17.
 */

public class ServerFacade implements IServer {
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
     * @param peep
     * @return An authToken which will identify the current session for the user
     */
    @Override
    public String register(Person peep) throws LoginException {
        String username = peep.getUsername();
        String password = peep.getPassword();
        ServerModelManager model = ServerModelManager.getInstance();
        String auth = model.register(username, password);
        return auth;
    }

    /**
     * Users can login using a username and password stored in peep.
     * @param peep
     * @return A new auth token for the user.
     */
    @Override
    public String login(Person peep) {
        String auth_token = "thisistheauthtoken12345";
        return auth_token;
    }

    @Override
    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
