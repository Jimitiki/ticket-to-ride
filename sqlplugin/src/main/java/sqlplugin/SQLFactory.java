package sqlplugin;

import delta.monstarz.plugin.IGameDAO;
import delta.monstarz.plugin.IPersistenceFactory;
import delta.monstarz.plugin.IUserDAO;

/**
 * Created by oliphaun on 4/12/17.
 */

public class SQLFactory implements IPersistenceFactory {
	@Override
	public IUserDAO getUserDAO() {
		return new SQLUserDAO();
	}

	@Override
	public IGameDAO getGameDAO() {
		return new SQLGameDAO();
	}
}
