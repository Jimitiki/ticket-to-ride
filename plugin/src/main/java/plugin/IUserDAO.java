package plugin;

import java.util.List;

import delta.monstarz.shared.model.Player;

/**
 * The interface for a persistent User data structure.
 */

public interface IUserDAO
{
    /**
     * Adds a player to a persistent data store.
     */
    void addPlayer(Player p);

    /**
     * Retrieves a list of all Players in the persistent data store.
     */
    List<Player> getPlayers();
}
