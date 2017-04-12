package persistence.java_ser;

import plugin.IGameDAO;
import plugin.IPersistenceFactory;
import plugin.IUserDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationFactory implements IPersistenceFactory {
	@Override
	public IUserDAO getUserDAO() {
		return new SerializationUserDAO();
	}

	@Override
	public IGameDAO getGameDAO() {
		return new SerializationGameDAO();
	}
}
