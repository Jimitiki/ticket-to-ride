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
		ClientModel model = ClientModel.getInstance();
		if (model.getGame() != null) {
			int curCommandID = model.getCurCommand();
			for (BaseCommand command : commands) {
				if (command.getId() > curCommandID || command.expires()) {
					command.execute();
					ClientModel.getInstance().setLastCommandID(command.getId());
				}
			}
		}
	}
}
