package delta.monstarz.plugin;

import java.util.List;

import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;

/**
 * The interface for a persistent Game data structure.
 */

public interface IGameDAO
{
    /**
     * Adds a game to the persistent data store.
     */
    void addGame(Game game);

    /**
     * Retrieves a list of all Players in the persistent data store.
     */
    List<Game> getGames();

    /**
     * Applies a command to a particular Game.
     *
     * @param gameID The ID of the game to update.
     * @param command The command to apply to the game.
     */
    void updateGame(int gameID, BaseCommand command);

    /**
     * Sets the number of Commands to be cached before updating the stored Game.
     */
    void setDelta(int delta);
}
