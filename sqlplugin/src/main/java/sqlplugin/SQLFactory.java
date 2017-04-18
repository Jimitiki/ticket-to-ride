package sqlplugin;

import delta.monstarz.plugin.IGameDAO;
import delta.monstarz.plugin.IPersistenceFactory;
import delta.monstarz.plugin.IUserDAO;

/**
 * Created by oliphaun on 4/12/17.
 */

public class SQLFactory implements IPersistenceFactory {
	private IGameDAO gameDAO;
	private IUserDAO userDAO;

	public SQLFactory() {
		gameDAO = new SQLGameDAO();
		userDAO = new SQLUserDAO();
	}

	@Override
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	@Override
	public IGameDAO getGameDAO() {
		return gameDAO;
	}
}
