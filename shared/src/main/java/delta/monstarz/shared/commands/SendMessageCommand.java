package delta.monstarz.shared.commands;

import delta.monstarz.shared.Message;

/**
 * Created by cwjohn42 on 3/8/17.
 */

public class SendMessageCommand extends BaseCommand {
	private Message message;

	public SendMessageCommand(String username, int gameID, Message message) {
		super(username, gameID);
		this.message = message;
		isGlobal = true;
		name = "SendMessageCommand";
	}
}
