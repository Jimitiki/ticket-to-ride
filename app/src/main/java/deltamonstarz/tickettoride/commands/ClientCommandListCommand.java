package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;
import deltamonstarz.tickettoride.model.ClientModel;

public class ClientCommandListCommand extends CommandListCommand {
	public ClientCommandListCommand(String username) {
		super(username);
	}

	@Override
	public void execute() {
		for (BaseCommand command : commands) {
			command.execute();
			ClientModel.getInstance().setLastCommandID(command.getId());
		}
	}
}
