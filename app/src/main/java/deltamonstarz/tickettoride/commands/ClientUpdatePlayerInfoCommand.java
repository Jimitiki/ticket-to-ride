package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.commands.UpdatePlayerInfoCommand;
import delta.monstarz.shared.model.PlayerInfo;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by oliphaun on 3/6/17.
 */

public class ClientUpdatePlayerInfoCommand extends UpdatePlayerInfoCommand {
	public ClientUpdatePlayerInfoCommand(PlayerInfo playerInfo) {
		super("", -1, playerInfo);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		model.updatePlayerInfo(playerInfo);
	}
}
