package plugin;

/**
 * The interface used to define a persistence plugin.
 */
public interface IPersistenceFactory
{
    IUserDAO getUserDAO();
    IGameDAO getGameDAO();
}