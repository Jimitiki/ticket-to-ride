package delta.monstarz.server;

import java.util.List;

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

    @Override
    public String register(Person peep) {
        String auth_token = "thisistheauthtoken12345";
        return auth_token;
    }

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
