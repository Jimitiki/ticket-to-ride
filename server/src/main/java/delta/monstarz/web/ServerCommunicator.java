package delta.monstarz.web;

import java.util.List;

import delta.monstarz.services.ServerFacade;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.commands.GameListCommand;
import delta.monstarz.shared.commands.JoinGameCommand;
import delta.monstarz.shared.commands.LoginCommand;
import delta.monstarz.shared.commands.LogoutCommand;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerCommunicator {
    private static ServerFacade serverFacade = ServerFacade.getInstance();

    public static LoginCommand register(Args args) {
        String auth_token = serverFacade.register(args.getStr1(), args.getStr2());
	    LoginCommand command = new LoginCommand(args.getStr1(), true);
        boolean login_successful = !auth_token.equals("");
	    command.setLoginSuccessful(login_successful);
	    command.setAuthToken(auth_token);
	    return command;
    }

    public static LoginCommand login(Args args) {
        String auth_token = serverFacade.login(args.getStr1(), args.getStr2());
        LoginCommand command = new LoginCommand(args.getStr1(), false);
        boolean login_successful = !auth_token.equals("");
        command.setLoginSuccessful(login_successful);
        command.setAuthToken(auth_token);
        return command;
    }

    public static JoinGameCommand createGame(Args args) {
        ServerFacade serv = ServerFacade.getInstance();
        String username = args.getStr1();
        String game_name = args.getStr2();
        int gameID = serv.createGame(username, game_name);
        JoinGameCommand command = new JoinGameCommand(username, gameID);
        return command;
    }

    public static GameListCommand listGames(String username) {
        List<GameInfo> gameList = serverFacade.listGames(username);
        GameListCommand command = new GameListCommand(username);
	    command.setGames(gameList);
	    return command;
    }

	public static LogoutCommand logout(String username) {
		return new LogoutCommand(username);
	}
}
