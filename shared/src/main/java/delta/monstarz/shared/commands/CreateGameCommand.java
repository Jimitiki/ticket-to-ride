package delta.monstarz.shared.commands;

/**
 * Created by oliphaun on 2/11/17.
 */

public class CreateGameCommand extends BaseCommand {
    public CreateGameCommand(String username, int gameID) {
        super(username, gameID);
        name = "CreateGameCommand";
        isGlobal = true;
    }

    @Override
    public void execute() {
    }
}
