package delta.monstarz.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import delta.monstarz.exceptions.loginExceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.exceptions.loginExceptions.UsernameInUseException;

import delta.monstarz.shared.Person;
import delta.monstarz.shared.GameInfo;

/**
 * Created by Trevor on 2/2/2017.
 */

public class ServerModelManager {

	private static ServerModelManager instance;
	private static final long authTokenLifeTime = 60 * ( 60 * 1000); // Only change the left number(minutes) Format: minutes * (seconds * milliseconds)

	private HashMap<String, Person> people = new HashMap<>(); //Key is username
	private HashMap<Integer, Game> games = new HashMap<>(); // Key is gameID

	private ServerModelManager(){

	}

	public static ServerModelManager getInstance(){
		if (instance == null){
			instance = new ServerModelManager();
		}
		return instance;
	}

	/**
	 * A new game is created with an empty beginning state.
	 * Players can join the new game until the owner of the game starts the game
	 * @param ownerName Username of the person who made the game
	 * @param gameName Name chosen by the creator of the gameloginExceptions
	 * @return The id of the new game
	 */
	public int createGame(String gameName, String ownerName){
		Game game = new Game(gameName, ownerName);
		games.put(game.getGameID(), game);
		return game.getGameID();
	}

	/**
	 * Players can join a game that has not yet started and has less than the max number of players
	 * A player only joins a game once
	 * @param playerName Username of player
	 * @param gameID ID of a game that exists
	 */
	public void joinGame(String playerName, int gameID){
		//Todo: Do we need to make a distinction between joining a game for the first time and re-entering a game?
		games.get(gameID).addPlayer(playerName);
	}

	/**
	 * Players can quit a game
	 * @param playerName
	 * @param gameID
	 */
	public void quitGame(String playerName, int gameID){

	}

	/**
	 * A new user account is made
	 * Each username must be unique
	 * @param username
	 * @param password
	 * @return An authToken which will identify the current session for the user
	 */
	public String register(String username, String password) throws LoginException{

		if ( username.equals("") || password.equals("") ){
			throw new InvalidCredentialsException();
		}
		else if (people.containsKey(username)){
			throw new UsernameInUseException();
		}
		else{
			Person person = new Person(username, password);

			String newAuthToken = UUID.randomUUID().toString();
			person.addAuthToken(newAuthToken);

			people.put(person.getUsername(), person);
			return newAuthToken;
		}
	}

	/**
	 * Users can login using a username and password that have already been registered
	 * @param username
	 * @param password
	 * @return
	 */
	public String login(String username, String password) throws LoginException{

		if ( username.equals("") || password.equals("") ){ // Basic check
			throw new InvalidCredentialsException();
		}
		else if (people.containsKey(username)){
			Person person = people.get(username);
			if ( person.getPassword().equals(password)){ // Password is good
				String newAuthToken = UUID.randomUUID().toString();
				person.addAuthToken(newAuthToken);
				return newAuthToken;
			}
			else{ // Password does not match
				throw new InvalidCredentialsException();
			}
		}
		else{ // Username not found
			throw new InvalidCredentialsException();
		}
	}


	/**
	 * A player is logged out and their authToken can no longer be used.
	 * @param authToken
	 */
	public void logout(String username, String authToken){
		if ( people.containsKey(username) ){
			people.get(username).removeAuthToken(authToken);
		}
	}

	/**
	 *
	 * @param username
	 * @return A list of games that the player is in
	 */
	public List<GameInfo> getGamesIn(String username){
		return null;
	}

	/**
	 *
	 * @return A list of all of the games that have not yet started
	 */
	public List<GameInfo> getOpenGames(){
		return null;
	}

	/**
	 *
	 * @return The games
	 */
	public HashMap<Integer, Game> getGames(){
		return games;
	}

	/**
	 * Start a game
	 * A game can only start if it has at least two people
	 * A game can't start if it has already started
	 * @param gameID
	 */
	public void startGame(int gameID){
		games.get(gameID).start();
	}

	/**
	 * Calculates the oldest allowed authToken time and has each person delete
	 * tokens that are older than the time
	 */
	private void clearExpiredTokens(){
		//Todo: Call this function periodically, probably once per minute
		Date oldestAllowedTime = new Date();
		oldestAllowedTime.setTime(oldestAllowedTime.getTime() - authTokenLifeTime);
	}

	public Game getGameByID(int gameID) {
		return games.get(gameID);
	}

}
