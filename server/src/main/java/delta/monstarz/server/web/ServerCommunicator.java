package delta.monstarz.server.web;

import java.util.List;

import delta.monstarz.server.Server;
import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.GameListCommand;
import delta.monstarz.shared.commands.CreateGameCommand;
import delta.monstarz.shared.commands.GameListCommand;
import delta.monstarz.shared.commands.LoginCommand;
import delta.monstarz.shared.commands.LogoutCommand;
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

    public static CreateGameCommand createGame(Args args, String auth) {
        ServerFacade serv = ServerFacade.getInstance();
        String username = args.getStr1();
        String game_name = args.getStr2();
        int gameID = serv.createGame(username, game_name, auth);
        CreateGameCommand command = new CreateGameCommand(username, gameID);
        return command;
    }

    public static GameListCommand listGames(String auth, String username) {
        List<GameInfo> gameList = serverFacade.listGames(auth, username);
        GameListCommand command = new GameListCommand(username);
	    command.setGames(gameList);
	    return command;
    }

	public static LogoutCommand logout(String auth, String username) {
		return new LogoutCommand(username);
	}
}
