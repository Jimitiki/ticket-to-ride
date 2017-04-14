package persistence.java_sql;

import java.util.List;

import delta.monstarz.shared.model.Person;
import delta.monstarz.plugin.IUserDAO;
import java.sql.*;

/**
 * Created by oliphaun on 4/12/17.
 */

public class SQLUserDAO implements IUserDAO {
	@Override
	public void addPerson(Person p) {
		Connection c;
		Statement stmt;
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ttr.db");
			System.out.println("Opened database successfully");

			stmt = c.createStatement();
			String sql = "";

			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "person", null);
			if (!rs.next()) {
				sql = "CREATE  TABLE person (" +
						"username TEXT NOT NULL PRIMARY KEY," +
						"password TEXT NOT NULL )";
				stmt.executeUpdate(sql);
				System.out.println("Table person created successfully");
			}

			c.setAutoCommit(false);

			sql = "INSERT INTO person VALUES (" +
					p.getUsername() + ", " +
					p.getPassword() + ");";
			stmt.executeUpdate(sql);

			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
	}

	@Override
	public List<Person> getPersons() {
		return null;
	}


}
