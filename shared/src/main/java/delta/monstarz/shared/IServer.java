package delta.monstarz.shared;

import java.util.List;

/**
 * Created by alex on 2/2/17.
 */

public interface IServer {
//    public void executeCommand(BaseCommand command) throws Exception;

    public String register(Person peep);
    public String login(Person peep);

    public List<GameInfo> listGames(String auth);
}
