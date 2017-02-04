package delta.monstarz.server;

import java.util.List;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.IServer;
import delta.monstarz.shared.Person;
import delta.monstarz.shared.Result;

/**
 * Created by oliphaun on 2/4/17.
 */

public class ServerFacade implements IServer {
    @Override
    public Result register(Person peep) {
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
