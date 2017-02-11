package delta.monstarz.server.web;

import java.util.List;

import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.LoginCommand;
import sun.rmi.runtime.Log;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerCommunicator {


//    public Result executeCommand(BaseCommand command) {
//        Result res = command.execute();
//        return res;
//    }

    public static LoginCommand register(Args args) {
        ServerFacade serv = ServerFacade.getInstance();
        String auth_token = serv.register(args.getStr1(), args.getStr2());
	    LoginCommand command = new LoginCommand(args.getStr1());
	    command.setLoginSuccessful(true);
	    command.setAuthToken(auth_token);
	    return command;
    }

    public static Result login(Args args) {
        Result res = new Result();
        ServerFacade serv = ServerFacade.getInstance();

        String auth_token = serv.login(args.getStr1(), args.getStr2());
        res.setResultStr(auth_token);

        return res;
    }

    public static Result createGame(Args args, String auth) {
        Result res = new Result();
        ServerFacade serv = ServerFacade.getInstance();
        String username = args.getStr1();
        String game_name = args.getStr2();

        int gameID = serv.createGame(username, game_name, auth);
        res.setResultInt(gameID);

        return res;
    }

    public static List<GameInfo> listGames(String auth) {
        return null;
    }

}
