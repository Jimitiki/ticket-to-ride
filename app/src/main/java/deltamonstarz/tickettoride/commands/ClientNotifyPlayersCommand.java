package deltamonstarz.tickettoride.commands;

import android.widget.Toast;

import delta.monstarz.shared.commands.NotifyPlayersCommand;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * Created by cart1994 on 3/29/17.
 */

public class ClientNotifyPlayersCommand extends NotifyPlayersCommand {


    public ClientNotifyPlayersCommand(String username, int gameID, String message) {
        super(username, gameID, message);
    }

    @Override
    public void execute()
    {
        GamePresenter.getInstance().handleMessage(message);

    }
}
