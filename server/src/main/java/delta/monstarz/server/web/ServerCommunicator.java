package delta.monstarz.server.web;

import java.util.List;

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
        //try
            String auth_token = serv.register(peep);
            res.setResultStr(auth_token);
        //catch
            res.setResultErr(null); //e
//        peep.addAuthToken(auth_token);
        return res;
    }

    public static Result login(Person peep) {
        Result res = new Result();
        IServer serv = ServerFacade.getInstance();
        //try
            String auth_token = serv.login(peep);
            res.setResultStr(auth_token);
        //catch
            res.setResultErr(null); //e
        return res;
    }

    public static List<GameInfo> listGames(String auth) {
        return null;
    }
}
