package delta.monstarz;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.commands.BaseCommand;

/**
 * The Game class holds all the data related to a ticket to ride game
 * Each time a game is made a game id is assigned
 * The id starts at 0 and is incremented for each additional game
 */

public class Game {

	private static int nextNewGameID = 0;

	private int gameID;
	private String name;
	private int startTime;
	private boolean gameStarted = false;
	private List<Player> players = new ArrayList<>();

	private List<BaseCommand> history = new ArrayList<BaseCommand>();

	public Game(){
		this.gameID = nextNewGameID;
		nextNewGameID++;
	}

	public int getGameID() {
		return gameID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getStartTime() {
		return startTime;
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * The game starts
	 * New players can no longer join the game
	 */
	public void start(){

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
		GameInfo gameInfo = new GameInfo(
				name,
				gameID,
				startTime,
				players.size(),
				gameStarted
		);

		return gameInfo;
	}
}
