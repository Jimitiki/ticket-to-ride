package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.GameInfo;

public class GameListCommand extends BaseCommand {
	protected List<GameInfo> games;

	public GameListCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {}
}
