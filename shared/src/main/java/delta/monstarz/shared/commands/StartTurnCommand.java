package delta.monstarz.shared.commands;

/**
 * Created by lyman126 on 3/24/17.
 */

public class StartTurnCommand extends BaseCommand {
	public StartTurnCommand(String username, int gameID) {
		super(username, gameID);
		isGlobal = false;
		name = "StartTurnCommand";
	}
}
