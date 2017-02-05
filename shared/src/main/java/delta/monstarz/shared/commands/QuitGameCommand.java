package delta.monstarz.shared.commands;

public class QuitGameCommand extends BaseCommand {

	public QuitGameCommand(String username, int gameID) {
		super(username, gameID);
		name = "QuitGameCommand";
		isGlobal = true;
	}

	@Override
	public void execute() {}
}
