package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.GameInfo;

public class GameListCommand extends BaseCommand {
	protected List<GameInfo> games;

	public GameListCommand(String username) {
		super(username);
		name = "GameListCommand";
	}

	public void setGames(List<GameInfo> games) {
		this.games = games;
	}

	@Override
	public void execute() {}
}
