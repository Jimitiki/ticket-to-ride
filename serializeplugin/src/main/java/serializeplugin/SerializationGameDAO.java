package serializeplugin;

import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.plugin.IGameDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationGameDAO implements IGameDAO {

	@Override
	public void addGame(Game game) {

	}

	@Override
	public List<Game> getGames() {
		return null;
	}

	@Override
	public void updateGame(int gameID, BaseCommand command) {

	}

	@Override
	public void setDelta(int delta) {

	}

	@Override
	public void clear() {

	}
}
