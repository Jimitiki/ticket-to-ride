package persistence.java_ser;

import java.io.File;
import java.util.List;

import delta.monstarz.model.account.Person;
import plugin.IUserDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationUserDAO implements IUserDAO {

	private static final String USERS_FOLDER = "users";

	public SerializationUserDAO() {
		// Make a folder for the games
		// Make a folder for the users

		new File(USERS_FOLDER).mkdir();
	}

	@Override
	public void addPerson(Person p) {
		// Save a person to the users folder
	}

	@Override
	public List<Person> getPersons() {
		return null;
	}
}
