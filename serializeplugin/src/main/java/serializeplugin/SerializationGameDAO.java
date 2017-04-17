package serializeplugin;

import org.apache.commons.io.FileUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import delta.monstarz.Server;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.plugin.IGameDAO;
import delta.monstarz.shared.commands.StartGameCommand;
import delta.monstarz.shared.model.Person;

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

		if (Server.restoreMode){
			return;
		}


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

			boolean gameIsStarting = false;
			BaseCommand command = game.getMostRecentCommand();
			if (command != null && command instanceof StartGameCommand){
				gameIsStarting = true;
			}


			// +1 for game
			if (fileCount >= delta + 1 || fileCount == 0 || gameIsStarting) {
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


				FileOutputStream fout = new FileOutputStream(gameFolder + "/" + command.getId() + "-command");
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(command);

			}

		}
		catch (Exception ex){
			ex.printStackTrace();
		}

	}

	@Override
	public List<Game> getGames() {
		ArrayList<Game> games = new ArrayList<>();

		File gamesFolder = new File(GAMES_FOLDER);

		try {

			for (File gameFolder : gamesFolder.listFiles()) {
				for (File file : gameFolder.listFiles()) {
					if (file.getName().equals("game")) {
						String gamePath = GAMES_FOLDER + "/" + gameFolder.getName() + "/game";
						InputStream inputFile = new FileInputStream(gamePath);
						InputStream buffer = new BufferedInputStream(inputFile);
						ObjectInput input = new ObjectInputStream(buffer);

						Game game = (Game) input.readObject();
						games.add(game);
					}
				}
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}


		return games;
	}

	@Override
	public List<BaseCommand> getDeltaCommands(int gameId) {
		ArrayList<BaseCommand> deltaCommands = new ArrayList<>();

		try {

			String path = GAMES_FOLDER + "/" + String.valueOf(gameId);
			File gameFolder = new File(path);

			for (File subFile : gameFolder.listFiles()) {
				if (subFile.getName().contains("command")) {
					InputStream inputFile = new FileInputStream(path + "/" + subFile.getName());
					InputStream buffer = new BufferedInputStream(inputFile);
					ObjectInput input = new ObjectInputStream(buffer);

					BaseCommand commandOut = (BaseCommand) input.readObject();
					deltaCommands.add(commandOut);
				}
			}

			// Sort
			Collections.sort(deltaCommands);

			return deltaCommands;

		}
		catch (Exception ex){
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void setDelta(int delta) {
		if (delta >= 0){
			this.delta = delta;
		}
	}

	@Override
	public void clear() {
		File gamesFolder = new File(GAMES_FOLDER);

		try {

			for (File subFile : gamesFolder.listFiles()) {
				for (File subSubFile: subFile.listFiles()){
					subSubFile.delete();
				}
				subFile.delete();
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
