package deltamonstarz.tickettoride;

/**
 * Created by oliphaun on 2/10/17.
 */

public interface IServerProxy {

    void register(String username, String password);

    void login(String username, String password);

    void createGame(String username, String game_name, String auth);

    void listGames(String auth);
}
