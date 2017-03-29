package delta.monstarz.shared.commands;

/**
 * Created by cart1994 on 3/29/17.
 */

public class NotifyPlayersCommand extends BaseCommand {
    protected String message;


    public NotifyPlayersCommand(String username, int gameID, String message) {
        super(username, gameID);
        this.message = message;
        isGlobal = true;
        name = "NotifyPlayersCommand";
    }
}
