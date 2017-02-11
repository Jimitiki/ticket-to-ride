package delta.monstarz.server.web;

import java.util.List;

import delta.monstarz.server.Server;
import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.GameListCommand;
import delta.monstarz.shared.commands.LoginCommand;
import sun.rmi.runtime.Log;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerCommunicator {
    static ServerFacade serverFacade = ServerFacade.getInstance();

//    public Result executeCommand(BaseCommand command) {
//        Result res = command.execute();
//        return res;
//    }

    public static LoginCommand register(Args args) {
        String auth_token = serverFacade.register(args.getStr1(), args.getStr2());
	    LoginCommand command = new LoginCommand(args.getStr1());
	    command.setLoginSuccessful(true);
	    command.setAuthToken(auth_token);
	    return command;
    }

    public static LoginCommand login(Args args) {
        String auth_token = serverFacade.login(args.getStr1(), args.getStr2());
        LoginCommand command = new LoginCommand(args.getStr1());
        command.setLoginSuccessful(true);
        command.setAuthToken(auth_token);
        return command;
    }

    public static Result createGame(Args args, String auth) {
        Result res = new Result();
        String username = args.getStr1();
        String game_name = args.getStr2();

        int gameID = serverFacade.createGame(username, game_name, auth);
        res.setResultInt(gameID);

        return res;
    }

    public static GameListCommand listGames(String auth, String username) {
        List<GameInfo> gameList = serverFacade.listGames(auth, username);
        GameListCommand command = new GameListCommand(username);
	    command.setGames(gameList);
	    return command;
    }

}
