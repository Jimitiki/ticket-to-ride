package deltamonstarz.tickettoride.commands;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.commands.SendMessageCommand;
import deltamonstarz.tickettoride.model.ClientModel;

public class ClientSendMessageCommand extends SendMessageCommand {
	public ClientSendMessageCommand(String username, int gameID, Message message) {
		super(username, gameID, message);
	}

	public void execute() {
		ClientModel.getInstance().addMessage(message);
	}
}
