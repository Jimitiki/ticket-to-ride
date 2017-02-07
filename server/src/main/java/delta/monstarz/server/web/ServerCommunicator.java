package delta.monstarz.server.web;

import java.util.List;

import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.IServer;
import delta.monstarz.shared.Person;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.GameInfo;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerCommunicator {


//    public Result executeCommand(BaseCommand command) {
//        Result res = command.execute();
//        return res;
//    }

    public static Result register(Person peep) {
        Result res = new Result();
        IServer serv = ServerFacade.getInstance();
        String auth_token = serv.register(peep.getUsername(), peep.getPassword());
        res.setResultStr(auth_token);
        return res;
    }

    public static Result login(Person peep) {
        Result res = new Result();
        IServer serv = ServerFacade.getInstance();

        String auth_token = serv.login(peep.getUsername(), peep.getPassword());
        res.setResultStr(auth_token);

        return res;
    }

    public static List<GameInfo> listGames(String auth) {
        return null;
    }
}
