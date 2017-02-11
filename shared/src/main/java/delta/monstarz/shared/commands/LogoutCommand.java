package delta.monstarz.shared.commands;

public class LogoutCommand extends BaseCommand {

	public LogoutCommand(String username, int gameID) {
		super(username, gameID);
		name = "LogoutCommand";
	}

	@Override
	public void execute() {}
}
