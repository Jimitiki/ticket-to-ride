package persistence.java_ser;

import java.util.List;

import delta.monstarz.model.account.Person;
import plugin.IUserDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationUserDAO implements IUserDAO {
	@Override
	public void addPerson(Person p) {

	}

	@Override
	public List<Person> getPersons() {
		return null;
	}
}
