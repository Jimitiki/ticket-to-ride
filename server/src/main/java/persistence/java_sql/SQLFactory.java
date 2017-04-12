package persistence.java_sql;

import plugin.IGameDAO;
import plugin.IPersistenceFactory;
import plugin.IUserDAO;

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
