package delta.monstarz;

import java.util.List;

import delta.monstarz.commands.BaseCommand;

/**
 * Created by alex on 2/2/17.
 */

public interface IServer {
    public void executeCommand(BaseCommand command) throws Exception;

    public String register(String username, String password);
    public String login(String username, String password);

    public List<GameInfo> listGames(String auth);
}
