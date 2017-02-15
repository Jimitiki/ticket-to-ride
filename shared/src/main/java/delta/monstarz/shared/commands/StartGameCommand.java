package delta.monstarz.shared.commands;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class StartGameCommand extends BaseCommand {
	public StartGameCommand(String username, int gameID) {
		super(username, gameID);
		name = "StartGameCommand";
	}

	@Override
	public void execute() {
	}
}
