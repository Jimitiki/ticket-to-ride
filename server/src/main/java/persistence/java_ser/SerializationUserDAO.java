package persistence.java_ser;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import delta.monstarz.shared.model.Person;
import plugin.IUserDAO;

/**
 * Created by lyman126 on 4/12/17.
 */

public class SerializationUserDAO implements IUserDAO {
	private static final String PARENT_FOLDER = "SerializationFiles";
	private static final String USERS_FOLDER = PARENT_FOLDER + "/" + "game-users";

	public SerializationUserDAO() {
		// Make a folder for the games
		// Make a folder for the users
		new File(PARENT_FOLDER).mkdir();
		new File(USERS_FOLDER).mkdir();

	}

	@Override
	public void addPerson(Person p) {
		try {
			FileOutputStream fout = new FileOutputStream(USERS_FOLDER + "/" + p.getUsername());
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(p);


			InputStream file = new FileInputStream(USERS_FOLDER + "/" + p.getUsername());
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			Person personOut = (Person) input.readObject();

			int u = 2;
		}
		catch (IOException ex){
			int r = 5;
		}
		catch (ClassNotFoundException ex){
			int e = 42;
		}
	}

	@Override
	public List<Person> getPersons() {
		return null;
	}
}
