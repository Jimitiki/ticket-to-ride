package delta.monstarz.shared.commands;

import java.util.List;

public class CommandListCommand extends BaseCommand {
	private List<BaseCommand> commands;

	public CommandListCommand(String username, int gameID) {
		super(username, gameID);
		name = "CommandListCommand";
	}

	public void setCommands(List<BaseCommand> commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {}
}
