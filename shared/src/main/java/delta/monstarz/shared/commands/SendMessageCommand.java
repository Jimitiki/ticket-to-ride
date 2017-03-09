package delta.monstarz.shared.commands;

import delta.monstarz.shared.Message;

public class SendMessageCommand extends BaseCommand {
	protected Message message;

	public SendMessageCommand(String username, int gameID, Message message) {
		super(username, gameID);
		this.message = message;
		isGlobal = true;
		name = "SendMessageCommand";
	}
}
