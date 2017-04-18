package sqlplugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	}

	@Override
	public void addGame(Game game) {

		if (Server.restoreMode){
			return;
		}

		try {
			PreparedStatement pstmt;
			Statement stmt;
			Connection c;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ttr.db");
			System.out.println("Opened database successfully");
			c.setAutoCommit(false);

			stmt = c.createStatement();
			String sql = "";

			DatabaseMetaData md = c.getMetaData();
			ResultSet rs = md.getTables(null, null, "game", null);
			if (!rs.next()) {
				sql = "CREATE TABLE game (" +
						"gameid INT NOT NULL PRIMARY KEY," +
						"game BLOB NOT NULL )";
				stmt.executeUpdate(sql);
				System.out.println("Table game created successfully");
			}
			md = c.getMetaData();
			rs = md.getTables(null, null, "command", null);
			if (!rs.next()) {
				sql = "CREATE TABLE command (" +
						"gameid INT NOT NULL," +
						"commandid INT NOT NULL," +
						"command BLOB NOT NULL )";
				stmt.executeUpdate(sql);
				System.out.println("Table command created successfully");
			}

			rs = stmt.executeQuery( "SELECT * FROM game where gameid = "+ game.getGameID() +";" );
//			if (!rs.next()) {
			boolean game_exists = rs.next();
			rs = stmt.executeQuery( "SELECT * FROM command where gameid = "+ game.getGameID() +";" );
			int command_count = 0;
			while ( rs.next() ) {
				command_count++;
			}

			boolean gameIsStarting = false;
			BaseCommand command = game.getMostRecentCommand();
			if (command != null && command instanceof StartGameCommand){
				gameIsStarting = true;
			}

			if (game_exists && command_count < delta && !gameIsStarting) { //copy map from the other plugin and use it to add commands until delta is reached.
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(command);
				byte[] commandAsBytes = baos.toByteArray();
				pstmt = c.prepareStatement("INSERT INTO command (gameid, commandid, command) VALUES (" +
						game.getGameID() + ", " +
						command.getId() + ", ?)");
				ByteArrayInputStream bais = new ByteArrayInputStream(commandAsBytes);
				pstmt.setBinaryStream(1, bais, commandAsBytes.length);
				pstmt.executeUpdate();
			} else {
				//reset game delta to this.delta
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(game);
				byte[] gameAsBytes = baos.toByteArray();
				pstmt = c.prepareStatement("REPLACE INTO game (gameid, game) VALUES (" +
						game.getGameID() + ", ?)");
				ByteArrayInputStream bais = new ByteArrayInputStream(gameAsBytes);
				pstmt.setBinaryStream(1, bais, gameAsBytes.length);
				pstmt.executeUpdate();

				sql = "DELETE FROM command where gameid = " + game.getGameID();
				stmt.executeUpdate(sql);
			}

			pstmt.close();
			stmt.close();
			c.commit();
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
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
		try {
			Connection c;
			Statement stmt;
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:ttr.db");
			System.out.println("Opened database successfully");
			c.setAutoCommit(false);

			stmt = c.createStatement();

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
			c.close();
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
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
		File f = new File("ttr.db");
		f.delete();
	}
}
