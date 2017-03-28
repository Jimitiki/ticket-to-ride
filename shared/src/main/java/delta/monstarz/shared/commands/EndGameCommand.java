package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.model.PlayerResult;

/**
 * Created by oliphaun on 3/25/17.
 */

public class EndGameCommand extends BaseCommand {
	protected List<PlayerResult> results;

	public EndGameCommand(String username, int gameID, List<PlayerResult> results) {
		super(username, gameID);
		name = "EndGameCommand";
		setGlobal(true);
		this.results = results;
	}
}
