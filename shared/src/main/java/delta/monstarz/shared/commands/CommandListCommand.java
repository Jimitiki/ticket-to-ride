package delta.monstarz.shared.commands;

import java.util.List;

public class CommandListCommand extends BaseCommand {
	protected List<BaseCommand> commands;

	public CommandListCommand(String username) {
		super(username);
		name = "CommandListCommand";
	}

	public void setCommands(List<BaseCommand> commands) {
		this.commands = commands;
	}

	@Override
	public void execute() {}
}
