package delta.monstarz.shared.commands;

public class JoinGameCommand extends BaseCommand {

	public JoinGameCommand(String username, int gameID) {
		super(username, gameID);
		name = "JoinGameCommand";
		isGlobal = true;
	}

	@Override
	public void execute() {}
}
