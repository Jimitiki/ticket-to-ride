package delta.monstarz.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import delta.monstarz.exceptions.loginExceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.exceptions.loginExceptions.UsernameInUseException;

import delta.monstarz.shared.GameInfo;

/**
 * Created by Trevor on 2/2/2017.
 */

public class ServerModelManager {

	private static ServerModelManager instance;
	private static long authTokenLifeTime = 60 * ( 60 * 1000); // Only change the left number(minutes) Format: minutes * (seconds * milliseconds)
	private static long removalFrequency = 60 * 1000; // One Minute
	private Date nextTokenCleaning = new Date();

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

	public static void clearModel(){
		instance = new ServerModelManager();
	}

	public static long getAuthTokenLifeTime() {
		return authTokenLifeTime;
	}

	public static void setAuthTokenLifeTime(long authTokenLifeTime) {
		ServerModelManager.authTokenLifeTime = authTokenLifeTime;
	}

	public static long getRemovalFrequency() {
		return removalFrequency;
	}

	public static void setRemovalFrequency(long removalFrequency) {
		ServerModelManager.removalFrequency = removalFrequency;
	}

	/**
	 * A new game is created with an empty beginning state.
	 * The owner is added to the game.
	 * Other players can join the new game until the owner of the game starts the game
	 * @param ownerName Username of the person who made the game
	 * @param gameName Name chosen by the creator of the gameloginExceptions
	 * @return The id of the new game
	 */
	public int createGame(String ownerName, String gameName) {
		if (people.containsKey(ownerName)) {
			Game game = new Game(gameName, ownerName);
			games.put(game.getGameID(), game);
			game.addPlayer(ownerName);
			return game.getGameID();
		}
		return -1;
	}

	/**
	 * Players can join a game that has not yet started and has less than the max number of players
	 * A player only joins a game once
	 * @param playerName Username of player
	 * @param gameID ID of a game that exists
	 */
	public void joinGame(String playerName, int gameID){
		//Todo: Do we need to make a distinction between joining a game for the first time and re-entering a game?
		Game game = games.get(gameID);
		if ( !game.hasPlayer(playerName) ){
			games.get(gameID).addPlayer(playerName);
		}

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
			System.out.println("Register: Malformed username or password" + username + ", " + password);
			throw new InvalidCredentialsException();
		}
		else if (people.containsKey(username)){
			System.out.println("Register:" + username + ", Already in use");
			throw new UsernameInUseException();
		}
		else{
			Person person = new Person(username, password);

			String newAuthToken = UUID.randomUUID().toString();
			person.addAuthToken(newAuthToken);

			people.put(person.getUsername(), person);
			System.out.println("Registered:" + username + ", " + password);
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
			System.out.println("Login Malformed username or password: " + username + ", " + password);
			throw new InvalidCredentialsException();
		}
		else if (people.containsKey(username)){
			Person person = people.get(username);
			if ( person.getPassword().equals(password)){ // Password is good
				String newAuthToken = UUID.randomUUID().toString();
				person.addAuthToken(newAuthToken);
				System.out.println("Login Successful: " + username + ", " + password);
				return newAuthToken;
			}
			else{ // Password does not match
				System.out.println("Login Failed Password Wrong: " + username);
				throw new InvalidCredentialsException();
			}
		}
		else{ // Username not found
			System.out.println("Login Failed Username Not Found: " + username);
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

		ArrayList<GameInfo> list = new ArrayList<>();

		for (Map.Entry<Integer, Game> entry: games.entrySet()){
			if ( entry.getValue().hasPlayer(username) ){
				list.add(entry.getValue().getGameInfo());
			}
		}

		return list;
	}

	/**
	 *  Players can only join a game that has not started and they are not already part of
	 * @return A list of games that a player can join
	 *
	 */
	public List<GameInfo> getJoinableGames(String username){

		ArrayList<GameInfo> list = new ArrayList<>();

		for (Map.Entry<Integer, Game> entry: games.entrySet()){
			Game game = entry.getValue();
			if ( !game.isGameStarted() && !game.hasPlayer(username) ){
				list.add(entry.getValue().getGameInfo());
			}
		}

		return list;
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
	 *
	 * @param authToken An authToken from an http header as part of a request from a client
	 * @return Is the authToken valid
	 */
	public boolean authTokenIsValid(String authToken){

		// Clean out old tokens if necessary
		if ( nextTokenCleaning.getTime() < new Date().getTime()){
			clearExpiredTokens();
			Date next = new Date();
			next.setTime(next.getTime() + removalFrequency);
			nextTokenCleaning = next;
		}

		for (Map.Entry<String,Person> entry: people.entrySet()){
			if (entry.getValue().hasAuthToken(authToken)){
				entry.getValue().refreshToken(authToken); // Date object updated to the current time
				return true;
			}
		}
		return false;
	}

	/**
	 * Calculates the oldest allowed authToken time and has each person delete
	 * tokens that are older than the time
	 */
	private void clearExpiredTokens(){
		//Todo: Call this function periodically, probably once per minute
		Date oldestAllowedTime = new Date();
		oldestAllowedTime.setTime(oldestAllowedTime.getTime() - authTokenLifeTime);

		for (Map.Entry<String,Person> entry: people.entrySet()){
			entry.getValue().removeExpiredTokens(oldestAllowedTime);
		}
	}

	public Game getGameByID(int gameID) {
		return games.get(gameID);
	}

	public Person getPersonByUsername(String username) {
		return people.get(username);
	}

}
