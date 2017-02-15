package delta.monstarz.server.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import delta.monstarz.server.Server;
import delta.monstarz.server.ServerFacade;
import delta.monstarz.shared.Args;
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
    private static ServerFacade serverFacade = ServerFacade.getInstance();

    public static LoginCommand register(Args args) {
        String auth_token = serverFacade.register(args.getStr1(), args.getStr2());
	    LoginCommand command = new LoginCommand(args.getStr1());
        boolean login_successful = !auth_token.equals("");
	    command.setLoginSuccessful(login_successful);
	    command.setAuthToken(auth_token);
	    return command;
    }

    public static LoginCommand login(Args args) {
        String auth_token = serverFacade.login(args.getStr1(), args.getStr2());
        LoginCommand command = new LoginCommand(args.getStr1());
        boolean login_successful = !auth_token.equals("");
        command.setLoginSuccessful(login_successful);
        command.setAuthToken(auth_token);
        return command;
    }

    public static CreateGameCommand createGame(Args args) {
        ServerFacade serv = ServerFacade.getInstance();
        String username = args.getStr1();
        String game_name = args.getStr2();
        int gameID = serv.createGame(username, game_name);
        CreateGameCommand command = new CreateGameCommand(username, gameID);
        return command;
    }

    public static GameListCommand listGames(String username) {
        List<GameInfo> gameList = serverFacade.listGames(username);
        GameListCommand command = new GameListCommand(username);

	    GameInfo[] array = new GameInfo[]
	    {
		    new GameInfo("Name", "Owner", 0, new Date(), 2, false),
		    new GameInfo("Team Cap", "Steve", 1, new Date(), 3, false),
		    new GameInfo("Bikini Bottom", "Owner", 2, new Date(), 4, true),
		    new GameInfo("Team Iron", "Owner", 3, new Date(), 4, false),
		    new GameInfo("Ooo", "Owner", 4, new Date(), 5, true),
	    };
	    ArrayList<GameInfo> mockArray = new ArrayList<>(Arrays.asList(array));
	    command.setGames(mockArray);

	    //command.setGames(gameList);
	    return command;
    }

	public static LogoutCommand logout(String username) {
		return new LogoutCommand(username);
	}
}
