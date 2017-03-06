package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.PlayerInfo;

/**
 * Created by oliphaun on 3/6/17.
 */

public class UpdatePlayerInfoCommand extends BaseCommand {
	protected PlayerInfo playerInfo;

	public UpdatePlayerInfoCommand(PlayerInfo playerInfo) {
		super("", 0); // we can add the username and gameID if needed, but client never sends this command... The server creates it.
		this.playerInfo = playerInfo;
	}
}
