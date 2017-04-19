package sqlplugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import delta.monstarz.Server;
import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.plugin.IGameDAO;
import delta.monstarz.shared.commands.StartGameCommand;

/**
 * Created by oliphaun on 4/12/17.
 */

public class SQLGameDAO implements IGameDAO {
	private int delta;

	public SQLGameDAO () {
		delta = 10;
		Connection connection = getConnection();
		if (connection != null) {
			try {
				Statement statement = connection.createStatement();
				if (!tableExists(connection, "game")) {
					String sql = "CREATE TABLE game (" +
							"gameid INT NOT NULL PRIMARY KEY," +
							"game BLOB NOT NULL );";
					statement.executeUpdate(sql);
					statement.close();
				}
				if (!tableExists(connection, "command")) {
					String sql = "CREATE TABLE command (" +
							"gameid INT NOT NULL," +
							"commandid INT NOT NULL," +
							"command BLOB NOT NULL, UNIQUE(gameid, commandid) ON CONFLICT IGNORE );";
					statement.executeUpdate(sql);
					statement.close();
				}
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addGame(Game game) {
		if (Server.restoreMode){
			return;
		}

		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
			int commandCount = getCommandCountByGameID(game.getGameID(), connection);

			boolean gameIsStarting = false;
			BaseCommand command = game.getMostRecentCommand();
			if (command != null && command instanceof StartGameCommand){
				gameIsStarting = true;
			}

			//If the last command to be executed was a startGameCommand,
			//save the whole game no matter what
			if (commandCount > delta || gameIsStarting) {
				saveGame(game, connection);
			} else {
				saveCommand(command, connection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Game> getGames() {
		ArrayList<Game> games = new ArrayList<>();
		try {
			Connection c;
			Statement stmt;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ttr.db");
			System.out.println("Opened database successfully");
			c.setAutoCommit(false);

			stmt = c.createStatement();

			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "game", null);
			if (!rs.next()) {
				return games;
			}

			rs = stmt.executeQuery( "SELECT game FROM game;" );
			while ( rs.next() ) {
				InputStream buffer = rs.getBinaryStream("game");
				ObjectInput input = new ObjectInputStream(buffer);
				Game game = (Game) input.readObject();
				games.add(game);
			}

			rs.close();
			stmt.close();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		return games;
	}

	@Override
	public List<BaseCommand> getDeltaCommands(int gameId) {
		ArrayList<BaseCommand> commands = new ArrayList<>();
		Connection c = getConnection();
		try {
			c.setAutoCommit(false);

			Statement stmt = c.createStatement();

			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "command", null);
			if (!rs.next()) {
				return commands;
			}

			rs = stmt.executeQuery( "SELECT command FROM command where gameid="+ gameId +";" );
			while ( rs.next() ) {
				InputStream buffer = rs.getBinaryStream("command");
				ObjectInput input = new ObjectInputStream(buffer);
				BaseCommand command = (BaseCommand) input.readObject();
				commands.add(command);
			}

			rs.close();
			stmt.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Collections.sort(commands);
		return commands;
	}


	@Override
	public void setDelta(int delta) {
		if (delta > 0){
			this.delta = delta;
		}
	}

	@Override
	public void clear() {
		Connection connection = getConnection();
		try {
			connection.setAutoCommit(false);
			Statement statement = connection.createStatement();
			String sql = "DELETE from 'game';";
			statement.executeUpdate(sql);
			sql = "DELETE from 'command';";
			statement.executeUpdate(sql);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void saveGame(Game game, Connection connection) throws SQLException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(game);
			byte[] gameAsBytes = baos.toByteArray();
			PreparedStatement preparedStatement = connection.prepareStatement("REPLACE INTO game " +
					"(gameid, game) VALUES (" + game.getGameID() + ", ?)");
			ByteArrayInputStream bais = new ByteArrayInputStream(gameAsBytes);
			preparedStatement.setBinaryStream(1, bais, gameAsBytes.length);
			preparedStatement.executeUpdate();
			preparedStatement.close();

			Statement statement = connection.createStatement();
			String sql = "DELETE FROM command where gameid = " + game.getGameID();
			statement.executeUpdate(sql);
			statement.close();

			connection.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveCommand(BaseCommand command, Connection connection) throws SQLException {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(command);
			byte[] commandAsBytes = baos.toByteArray();
			PreparedStatement statement = connection.prepareStatement("INSERT INTO command (gameid, " +
					"commandid, command) VALUES (" + command.getGameID() + ", " + command.getId() + ", ?)");
			ByteArrayInputStream bais = new ByteArrayInputStream(commandAsBytes);
			statement.setBinaryStream(1, bais, commandAsBytes.length);
			statement.executeUpdate();
			statement.close();

			connection.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int getCommandCountByGameID(int gameID, Connection connection) throws SQLException{
		Statement statement = connection.createStatement();
		String sql = "SELECT COUNT(*) FROM 'command' WHERE 'gameID' == '" + gameID + "';";
		ResultSet resultSet = statement.executeQuery(sql);
		if (resultSet.next()) {
			return resultSet.getInt(1);
		}
		return -1;
	}

	private boolean tableExists(Connection connection, String table) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet rs = metaData.getTables(null, null, table, null);
		return rs.next();
	}

	private Connection getConnection() {
		try {
			return DriverManager.getConnection("jdbc:sqlite:ttr.db");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
