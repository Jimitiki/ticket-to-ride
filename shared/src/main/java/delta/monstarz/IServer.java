package delta.monstarz;

/**
 * Created by alex on 2/2/17.
 */

public interface IServer {
    public Err executeCommand(BaseCommand command);

    public String register(String username, String password);
    public String login(String username, String password);

    public Result listGames(String auth);
}
