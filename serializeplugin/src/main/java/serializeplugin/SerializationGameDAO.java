package serializeplugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.plugin.IGameDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationGameDAO implements IGameDAO {

	private static final String PARENT_FOLDER = "SerializationFiles";
	private static final String GAMES_FOLDER = PARENT_FOLDER + "/" + "games";

	int delta = 10;

	public SerializationGameDAO() {
		new File(PARENT_FOLDER).mkdir();
		new File(GAMES_FOLDER).mkdir();
	}

	@Override
	public void addGame(Game game) {


		try {
			String path = GAMES_FOLDER + "/" + String.valueOf(game.getGameID());
			new File(path).mkdir();
			File gameFolder = new File(path);

			int fileCount = 0;
			for (File subFile: gameFolder.listFiles()){
				String name = subFile.getName();
				if (name.contains("game") || name.contains("command")){
					fileCount++;
				}
			}

			// +1 for game
			if (fileCount >= delta + 1 || fileCount == 0) {
				// Delete everything
				for (File subFile: gameFolder.listFiles()){
					subFile.delete();
				}


				// Save the whole game
				FileOutputStream fout = new FileOutputStream(gameFolder + "/" + "game");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(game);

			} else {
				// Save the most recent command
				BaseCommand command = game.getMostRecentCommand();

				FileOutputStream fout = new FileOutputStream(gameFolder + "/" + command.getId() + "-command");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(game);

			}

		}
		catch (Exception ex){
			ex.printStackTrace();
		}

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
		if (delta > 0){
			this.delta = delta;
		}
	}

	@Override
	public void clear() {

	}
}
