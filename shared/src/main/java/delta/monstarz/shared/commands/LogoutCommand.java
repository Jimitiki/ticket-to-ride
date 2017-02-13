package delta.monstarz.shared.commands;

public class LogoutCommand extends BaseCommand {

	public LogoutCommand(String username) {
		super(username);
		name = "LogoutCommand";
	}

	@Override
	public void execute() {}
}
