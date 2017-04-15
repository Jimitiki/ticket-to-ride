package serializeplugin;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.Person;
import delta.monstarz.plugin.IUserDAO;

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

		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}

	@Override
	public List<Person> getPersons() {
		ArrayList<Person> people = new ArrayList<>();


		try {

			File folder = new File(USERS_FOLDER);
			File[] listOfFiles = folder.listFiles();

			for (File file : listOfFiles) {
				InputStream inputFile = new FileInputStream(USERS_FOLDER + "/" + file.getName());
				InputStream buffer = new BufferedInputStream(inputFile);
				ObjectInput input = new ObjectInputStream(buffer);

				Person personOut = (Person) input.readObject();
				people.add(personOut);
			}

		}
		catch (Exception ex){
			ex.printStackTrace();
		}

		return people;
	}

	@Override
	public void clear() {
		File dir = new File(USERS_FOLDER);
		for(File file: dir.listFiles())
			if (!file.isDirectory()) {
				file.delete();
			}
	}
}
