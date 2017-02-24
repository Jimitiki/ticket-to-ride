package delta.monstarz.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.commands.BaseCommand;

/**
 * The Game class holds all the data related to a ticket to ride game
 * Each time a game is made a game id is assigned
 * The id starts at 0 and is incremented for each additional game
 */

public class Game {

	private static int nextNewGameID = 0;
	public static final int MAX_PLAYERS = 5;

	private int gameID;
	private String name;
	private String ownerName;
	private Date startTime;
	private boolean gameStarted = false;
	private List<Player> players = new ArrayList<>();

	private List<BaseCommand> history = new ArrayList<>();

	public Game(String gameName, String ownerName){
		this.name = gameName;
		this.ownerName = ownerName;
		this.gameID = nextNewGameID;
		nextNewGameID++;
	}

	public int getGameID() {
		return gameID;
	}

	public String getName() {
		return name;
	}

	public int getNumPlayers() {
		return players.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartTime() {
		return startTime;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}


	public List<BaseCommand> getHistory() {
		return history;
	}

	/**
	 * The game starts
	 * New players can no longer join the game
	 */
	public void start(){
		if (players.size() > 1){
			gameStarted = true;
			startTime = new Date(); // All new dates start with the current time
		}

	}

	/**
	 * Add a player to the game as long as the game is not full
	 * @param username
	 */
	public void addPlayer(String username){
		if (players.size() < MAX_PLAYERS && !gameStarted){
			Player player = new Player(username);
			players.add(player);
		}

	}

	public void removePlayer(String username) {
		//TODO Should players be a map of usernames to player objects?
	}

	/**
	 * Find out is a player is in the game by using a username as identification
	 * @param username A username of a player that may or may not be in the game
	 * @return A boolean value representing if the player is in the game
	 */
	public boolean hasPlayer(String username){

		for (Player player: players){
			if (player.getUsername().equals(username)){
				return true;
			}
		}

		return false;
	}

	/**
	 *
	 * @return Returns a GameInfo object that represents the game
	 */
	public GameInfo getGameInfo(){

		TreeSet<String> playersNames = new TreeSet<>();
		for (Player player: players){
			playersNames.add(player.getUsername());
		}

		GameInfo gameInfo = new GameInfo(
				name,
				ownerName,
				gameID,
				startTime,
				players.size(),
				gameStarted,
				playersNames
		);

		return gameInfo;
	}

	public void addCommand(BaseCommand command) {
		history.add(command);
	}
}
