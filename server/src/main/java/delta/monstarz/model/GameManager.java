package delta.monstarz.model;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import delta.monstarz.model.account.PersonManager;
import delta.monstarz.model.game.Game;
import delta.monstarz.JSONReader;
import delta.monstarz.shared.GameInfo;

/**
 * This class manages the servers collection of games.
 */
public class GameManager
{

	public static String jsonGameData = "server/src/main/assets/preferences.json";

	//Instance Variables
	/**
	 * A map relating game IDs to their corresponding games.
	 */
	private Map<Integer, Game> games;

	//Singleton Implementation
	private GameManager()
	{
		games = new HashMap<>();
	}

	private static GameManager instance;

	public static GameManager getInstance()
	{
		if(instance == null)
		{
			instance = new GameManager();
		}
		return instance;
	}

	//Getters and Setters
	/**
	 * Gets a game by it's gameID
	 * @pre gameID must not be less than 0 and must be for a game that exits
	 * @post none
	 * @param gameID
	 * @return A game object
	 */
	public Game getGameByID(int gameID) {
		return games.get(gameID);
	}

	//Public Methods
	/**
	 * A new game is created with an empty beginning state.
	 * The owner is added to the game.
	 * Other players can join the new game until the owner of the game starts the game
	 * @pre ownerName and gameName not null, ownerName should be a username an account on the server
	 * @post games count increases by one
	 * @param ownerName Username of the person who made the game
	 * @param gameName Name chosen by the creator of the gameloginExceptions
	 * @return The id of the new game, or -1 if the game could not be created.
	 */
	public int createGame(String ownerName, String gameName)
	{
		if (PersonManager.getInstance().isValidUsername(ownerName)) {
			JsonObject jsonGame = JSONReader.readJSON(jsonGameData);
			Game game = Game.init(jsonGame, ownerName, gameName);
			games.put(game.getGameID(), game);
			game.addPlayer(ownerName);

			return game.getGameID();
		}
		else {
			return -1;
		}
	}

	/**
	 * Players can join a game that has not yet started and has less than the max number of players
	 * A player only joins a game once
	 * @pre playerName not null and is a username on the server, gameId gte 0, game has at most 4 players
	 * @post Game player count goes up by one
	 * @param playerName Username of player
	 * @param gameID ID of a game that exists
	 */
	public void joinGame(String playerName, int gameID){
		Game game = games.get(gameID);
		if ( !game.hasPlayer(playerName) ){
			game.addPlayer(playerName);
		}

	}

	/**
	 * @pre username not null, username is for a person on the server
	 * @post none
	 * @param username
	 * @return A list of games that the player is in
	 */
	public List<GameInfo> getGamesIn(String username){

		ArrayList<GameInfo> list = new ArrayList<>();

		for (Map.Entry<Integer, Game> entry: games.entrySet()){
			if ( entry.getValue().hasPlayer(username) && !entry.getValue().isGameEnded() ){
				list.add(entry.getValue().getGameInfo());
			}
		}

		return list;
	}

	/**
	 *  Players can only join a game that has not started and they are not already part of
	 * @pre username not null and is an existing user on the server
	 * @post No duplicate games are returned
	 * @return A list of games that a player can join
	 *
	 */
	public List<GameInfo> getJoinableGames(String username){

		ArrayList<GameInfo> list = new ArrayList<>();

		for (Map.Entry<Integer, Game> entry: games.entrySet()){
			Game game = entry.getValue();
			if ( !game.isGameStarted() && !game.hasPlayer(username) && game.getNumPlayers() < 5){
				list.add(entry.getValue().getGameInfo());
			}
		}

		return list;
	}

	/**
	 * Start a game
	 * A game can only initGame if it has at least two people
	 * A game can't initGame if it has already started
	 * @pre gameID of a existing game that has not yet started, the game should have at least two people
	 * @post the game with gameID will initGame
	 * @param gameID
	 */
	public void startGame(int gameID){
		games.get(gameID).initGame();
	}

	/**
	 * Removes all game data
	 */
	public void clear()
	{
		games.clear();
	}
}
