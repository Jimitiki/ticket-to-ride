package delta.monstarz.model.account;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import delta.monstarz.Server;
import delta.monstarz.exceptions.loginExceptions.InvalidCredentialsException;
import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.exceptions.loginExceptions.UsernameInUseException;

/**
 * Manages the servers collection of user accounts.
 */
public class PersonManager
{
	//Instance Variables
	/**
	 * A map relating usernames to Person objects.
	 */
	private Map<String, Person> people;

	/**
	 * A number representing the number of miliseconds that an authorization token is valid for.
	 * The format is [minutes] * ([seconds] * [miliseconds])
	 * Don't mess with the seconds or miliseconds.
	 */
	private long authTokenLifeTime = 60 * ( 60 * 1000);

	/**
	 * A number representing the number of miliseconds between authorization token cleanings.
	 * The format is [seconds] * [miliseconds]
	 */
	private long removalFrequency = 60 * 1000; // One Minute

	/**
	 * The time of the next authorization token cleaning.
	 */
	private Date nextTokenCleaning = new Date();

	//Singleton Implementation
	private PersonManager()
	{
		people = new HashMap<>();
	}

	private static PersonManager instance;

	public static PersonManager getInstance()
	{
		if(instance == null)
		{
			instance = new PersonManager();
		}
		return instance;
	}

	//Getters and Setters
	/**
	 * Get a person object by username
	 * @pre username must not be null, username must be for an existing person on the server
	 * @post none
	 * @param username
	 * @return Person Object connected associated with the username
	 */
	public Person getPersonForUsername(String username) {
		return people.get(username);
	}

	/**
	 * Checks if a username exists.
	 */
	public boolean isValidUsername(String username)
	{
		return people.containsKey(username);
	}

	//TODO I don't know if there is a good reason to let these be edited at runtime. Can we get rid of the getters and setters?
	public long getAuthTokenLifeTime() {
		return authTokenLifeTime;
	}

	public void setAuthTokenLifeTime(long authTokenLifeTime) {
		authTokenLifeTime = authTokenLifeTime;
	}

	public long getRemovalFrequency() {
		return removalFrequency;
	}

	public void setRemovalFrequency(long removalFrequency) {
		removalFrequency = removalFrequency;
	}

	//Public Methods
	/**
	 * Creates a new account
	 * @pre username and password not null
	 * @pre username must not be taken
	 * @post AuthToken created and saved on the server, or an error is returned
	 * @param username
	 * @param password
	 * @throws LoginException
	 * @return An authToken which will identify the current session for the user
	 */
	public String register(String username, String password) throws LoginException{

		if ( username.equals("") || password.equals("") ){
			System.out.println("Register: Malformed username or password " + username + ", " + password);
			throw new InvalidCredentialsException();
		}
		else if (people.containsKey(username)){
			System.out.println("Register:" + username + ", is already in use");
			throw new UsernameInUseException();
		}
		else{
			Person person = new Person(username, password);

			String newAuthToken = UUID.randomUUID().toString();
			person.setAuthToken(newAuthToken);

			people.put(person.getUsername(), person);
			Server.plugin.getUserDAO().addPerson(person);
			System.out.println("Registered:" + username + ", " + password);
			return newAuthToken;
		}
	}

	/**
	 * Logs a user in or throws a LoginException if the user does not exist.
	 * @pre username and password not null
	 * @post AuthToken created and saved on the server, or an error is returned
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
				person.setAuthToken(newAuthToken);
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
	 * Checks if an authorization token is valid.
	 *
	 * @pre authToken is not null
	 * @post none
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

		//TODO This checks if the token belongs to any user. Do we need to fix that?
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
		Date oldestAllowedTime = new Date();
		oldestAllowedTime.setTime(oldestAllowedTime.getTime() - authTokenLifeTime);

		for (Map.Entry<String,Person> entry: people.entrySet()){
			entry.getValue().removeExpiredTokens(oldestAllowedTime);
		}
	}

	/**
	 * Removes all person data
	 */
	public void clear()
	{
		people.clear();
	}
}
