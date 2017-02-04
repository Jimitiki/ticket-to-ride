package delta.monstarz.server;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import delta.monstarz.exceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.LoginException;
import delta.monstarz.exceptions.UsernameInUseException;

import delta.monstarz.shared.Person;
import delta.monstarz.shared.GameInfo;

/**
 * Created by Trevor on 2/2/2017.
 */

public class ServerModel {

	private static ServerModel instance;

	private HashMap<String, Person> people = new HashMap<>(); //Key is username
	private HashMap<Integer, Game> games = new HashMap<>();

	private ServerModel(){

	}

	public ServerModel getInstance(){
		if (instance == null){
			instance = new ServerModel();
		}
		return instance;
	}

	/**
	 * A new game is created with an empty beginning state.
	 * Players can join the new game until the owner of the game starts the game
	 * @param playerName
	 * @param gameName
	 * @return The id of the new game
	 */
	public int createGame(String playerName, String gameName){
		return -1;
	}

	/**
	 * Players can join a game that has not yet started and has less than the max number of players
	 * Players can also join games that they are part of, even if the game has already started
	 * @param playerName
	 * @param gameName
	 */
	public void joinGame(String playerName, String gameName){

	}

	/**
	 * Players can quit a game
	 * @param playerName
	 * @param gameID
	 */
	public void quit(String playerName, int gameID){

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
	public void logout(String authToken){

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

	}

	public Game getGameByID(int gameID) {
		return games.get(gameID);
	}

}
