package delta.monstarz.server;

import java.util.List;

import delta.monstarz.shared.commands.BaseCommand;

public class CommandManager {


	/**
	 * validates that a command has valid data members, i.e. username & gameID correspond
	 * @param command the Command object that will be validated
	 * @throws Exception if validation fails
	 */
	public static void validate(BaseCommand command) throws Exception{

	}

	/**
	 * Executes the given command
	 * @param command Command object to be executed
	 * @throws Exception if execution fails, i.e. action is invalid
	 */
	public static void execute(BaseCommand command) throws Exception{
		command.execute();
	}

	/**
	 * Gets the commands from the given game visible to the specified player that were issued after the index given
	 * @param gameID id of the game from which commands are to be retrieved
	 * @param username username of the player requesting commands
	 * @param commandIndex index of the last command executed by the client
	 * @return list of commands
	 */
	public static List<BaseCommand> getCommands(int gameID, String username, int commandIndex) {
		return null;
	}
}
