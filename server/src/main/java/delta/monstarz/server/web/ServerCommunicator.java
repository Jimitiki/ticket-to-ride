package delta.monstarz.server.web;

import java.util.List;

import delta.monstarz.shared.Person;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.GameInfo;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerCommunicator {
    private static ServerCommunicator _instance = null;

    public static ServerCommunicator getInstance() {
        if (_instance == null) {
            _instance = new ServerCommunicator();
        }
        return _instance;
    }

//    public Result executeCommand(BaseCommand command) {
//        Result res = command.execute();
//        return res;
//    }

    public Result register(Person peep) {
        Result res = new Result();
        //try
            String auth_token = "thisistheauthtoken12345";
            res.setResultStr(auth_token);
        //catch
            res.setResultErr(null); //e
//        peep.addAuthToken(auth_token);
        return res;
    }

    public String login(String username, String password) {
        return null;
    }

    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
