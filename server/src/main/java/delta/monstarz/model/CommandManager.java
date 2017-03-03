package delta.monstarz.model;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.services.ServerFacade;
import delta.monstarz.shared.commands.BaseCommand;

public class CommandManager {
	/**
	 * Executes the given command
	 * @param command Command object to be executed
	 * @throws Exception if execution fails, i.e. action is invalid
	 */
	public static void execute(BaseCommand command) throws Exception{
		if (validate(command)) {
			command.execute();
			Game game = GameManager.getInstance().getGameByID(command.getGameID());
			game.addCommand(command);
		} else {
			throw new Exception();
		}
	}

	private static boolean validate(BaseCommand command) {
		ServerFacade server = ServerFacade.getInstance();
		return server.gameExists(command.getGameID()) && server.personExists(command.getUsername());
	}

	/**
	 * Gets the commands from the given game visible to the specified player that were issued after the index given
	 * @param gameID id of the game from which commands are to be retrieved
	 * @param username username of the player requesting commands
	 * @param commandIndex index of the last command executed by the client
	 * @return list of commands
	 */
	public static List<BaseCommand> getCommands(int gameID, String username, int commandIndex) {
		try {
			Game game = GameManager.getInstance().getGameByID(gameID);
			List<BaseCommand> allCommands = game.getHistory();
			List<BaseCommand> visibleCommands = new ArrayList<>();
			for (int i = 0; i < allCommands.size(); i++) {
				BaseCommand command = allCommands.get(i);
				if (command.isGlobal() || command.getUsername().equals(username)) {
					visibleCommands.add(command);
				}
			}
			if (visibleCommands.size() == 0 || commandIndex == 0) {
				return visibleCommands;
			} else {
				return visibleCommands.subList(commandIndex, visibleCommands.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
