package persistence.java_ser;

import plugin.IGameDAO;
import plugin.IPersistenceFactory;
import plugin.IUserDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationFactory implements IPersistenceFactory {


	private SerializationUserDAO userDAO = new SerializationUserDAO();
	private SerializationGameDAO gameDAO = new SerializationGameDAO();


	@Override
	public IUserDAO getUserDAO() {
		return userDAO;
	}

	@Override
	public IGameDAO getGameDAO() {
		return gameDAO;
	}
}
