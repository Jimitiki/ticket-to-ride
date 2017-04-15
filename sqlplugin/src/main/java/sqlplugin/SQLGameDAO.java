package sqlplugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.plugin.IGameDAO;
/**
 * Created by oliphaun on 4/12/17.
 */

public class SQLGameDAO implements IGameDAO {
	private int delta;

	public SQLGameDAO () {
	}

	@Override
	public void addGame(Game game) {

	}

	@Override
	public List<Game> getGames() {
		return new ArrayList<>();
	}

	//@Override
	public void updateGame(int gameID, BaseCommand command) {
		Connection c;
		PreparedStatement pstmt;
		Statement stmt;
		try {
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

			if (true) { //copy map from the other plugin and use it to add commands until delta is reached.
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(command);
				byte[] commandAsBytes = baos.toByteArray();
				pstmt = c.prepareStatement("INSERT INTO command (gameid, commandid, command) VALUES (" +
						gameID + ", " +
						command.getId() + ", ?)");
				ByteArrayInputStream bais = new ByteArrayInputStream(commandAsBytes);
				pstmt.setBinaryStream(1, bais, commandAsBytes.length);
				pstmt.executeUpdate();
			} else {
				//reset game delta to this.delta
				Game game = GameManager.getInstance().getGameByID(gameID);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(baos);
				oos.writeObject(game);
				byte[] gameAsBytes = baos.toByteArray();
				pstmt = c.prepareStatement("REPLACE INTO game (gameid, game) VALUES (" +
						gameID + ", ?)");
				ByteArrayInputStream bais = new ByteArrayInputStream(gameAsBytes);
				pstmt.setBinaryStream(1, bais, gameAsBytes.length);
				pstmt.executeUpdate();

				sql = "DELETE FROM command where gameid = " + gameID;
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
	public List<BaseCommand> getDeltaCommands(int gameId) {
		return null;
	}

	@Override
	public void setDelta(int delta) {
		this.delta = delta;
	}

	@Override
	public void clear() {
		File f = new File("ttr.db");
		f.delete();
	}
}
