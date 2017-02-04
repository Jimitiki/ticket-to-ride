package delta.monstarz;

import java.util.List;

import delta.monstarz.commands.BaseCommand;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerFacade implements IServer {
    private static ServerFacade _instance = null;

    public static ServerFacade getInstance() {
        if (_instance == null) {
            _instance = new ServerFacade();
        }
        return _instance;
    }

//    public Result executeCommand(BaseCommand command) {
//        Result res = command.execute();
//        return res;
//    }

    @Override
    public String register(String username, String password) {
        return null;
    }

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public List<GameInfo> listGames(String auth) {
        return null;
    }
}
