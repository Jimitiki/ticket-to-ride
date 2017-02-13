package deltamonstarz.tickettoride;

import delta.monstarz.shared.commands.BaseCommand;

/**
 * Created by oliphaun on 2/10/17.
 */

public interface IServerProxy {

    void register(String username, String password);

    void login(String username, String password);

	void logout(String username, String auth);

    void createGame(String username, String game_name, String auth);

    void listGames(String auth, String username);

    void sendCommand(String auth, BaseCommand command);

	void listCommands(String auth, String gameID, String username, int curCommand);
}
