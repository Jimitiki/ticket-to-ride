package delta.monstarz.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import delta.monstarz.exceptions.InvalidCommandException;
import delta.monstarz.model.game.Game;
import delta.monstarz.services.GameManagementService;
import delta.monstarz.services.UserAuthenticationService;
import delta.monstarz.shared.commands.BaseCommand;

public class CommandManager {
	/**
	 * Executes the given command
	 * @param command Command object to be executed
	 * @throws Exception if execution fails, i.e. action is invalid
	 */
	public static void execute(BaseCommand command) {
		if (validate(command)) {
			command.execute();
		}
	}

	private static boolean validate(BaseCommand command) {
		GameManagementService gameService = GameManagementService.getInstance();
		UserAuthenticationService userService = UserAuthenticationService.getInstance();
		return gameService.gameExists(command.getGameID()) && userService.personExists(command.getUsername());
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
				if (allCommands.get(i).getId() <= commandIndex) {
					continue;
				}
				BaseCommand command = allCommands.get(i);
				if (command.isGlobal() || command.getUsername().equals(username)) {
					visibleCommands.add(command);
				}
			}

			// These commands are deleted once they have been sent back
			List<BaseCommand> commandsThatExpire = game.getOneTimeUseCommands();
			for ( Iterator<BaseCommand> iterator = commandsThatExpire.iterator(); iterator.hasNext(); ){
				BaseCommand command = iterator.next();
				if (username.equals(command.getUsername())){
					visibleCommands.add(command);
					iterator.remove();
				}
			}

			return visibleCommands;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}
}
