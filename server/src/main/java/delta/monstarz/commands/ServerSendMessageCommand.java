package delta.monstarz.commands;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.Message;
import delta.monstarz.shared.commands.SendMessageCommand;

public class ServerSendMessageCommand extends SendMessageCommand {
	public ServerSendMessageCommand(String username, int gameID, Message message) {
		super(username, gameID, message);
	}

	@Override
	public void execute() {
		// Save this command
		GameManager.getInstance().getGameByID(gameID).addCommand(this);
	}
}
