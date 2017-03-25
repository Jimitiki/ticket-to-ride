package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.StartTurnCommand;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by lyman126 on 3/24/17.
 */

public class ClientStartTurnCommand extends StartTurnCommand {
	public ClientStartTurnCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {
		ClientModel.getInstance().getGame().getMe().startTurn();
	}
}
