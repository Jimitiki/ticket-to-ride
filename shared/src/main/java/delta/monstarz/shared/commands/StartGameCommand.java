package delta.monstarz.shared.commands;

public class StartGameCommand extends BaseCommand {
	public StartGameCommand(String username, int gameID) {
		super(username, gameID);
		name = "StartGameCommand";
		isGlobal = true;
	}

	@Override
	public void execute() {
	}
}
