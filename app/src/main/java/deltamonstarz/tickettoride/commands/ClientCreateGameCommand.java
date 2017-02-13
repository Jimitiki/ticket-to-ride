package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.CreateGameCommand;
import deltamonstarz.tickettoride.ClientModel;

/**
 * Created by oliphaun on 2/11/17.
 */

public class ClientCreateGameCommand extends CreateGameCommand {
    public ClientCreateGameCommand(String username, int gameID) {
        super(username, gameID);
    }

    @Override
    public void execute() {
        ClientModel model = ClientModel.getInstance();
//        if (model.getGameID() == gameID) {
//            model.addPlayer(username);
//        }
        System.out.println("Game created with ID:");
        System.out.println(gameID);
    }
}
