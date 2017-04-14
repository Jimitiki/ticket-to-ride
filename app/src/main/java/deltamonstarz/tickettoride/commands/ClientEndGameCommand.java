package deltamonstarz.tickettoride.commands;

import java.util.Collections;
import java.util.List;

import delta.monstarz.shared.commands.EndGameCommand;
import delta.monstarz.shared.model.PlayerResult;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by oliphaun on 3/25/17.
 */

public class ClientEndGameCommand extends EndGameCommand {
	public ClientEndGameCommand(String username, int gameID, List<PlayerResult> results) {
		super(username, gameID, results);
	}

	@Override
	public void execute() {
		ClientModel model = ClientModel.getInstance();
		if (model.getGameID() == gameID) {
			Collections.sort(results);
			model.getGame().endGame(results);
		}

	}
}
