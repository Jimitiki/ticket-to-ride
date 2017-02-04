package delta.monstarz.shared;

import java.util.List;

/**
 * Created by alex on 2/2/17.
 */

public interface IServer {
//    public void executeCommand(BaseCommand command) throws Exception;

    public Result register(Person peep);
    public String login(String username, String password);

    public List<GameInfo> listGames(String auth);
}
