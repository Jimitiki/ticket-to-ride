package deltamonstarz.tickettoride;

import java.util.List;

import delta.monstarz.GameInfo;
import delta.monstarz.IServer;
import delta.monstarz.commands.BaseCommand;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ServerProxy implements IServer {

    public void executeCommand(BaseCommand command) throws Exception {

    }

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
