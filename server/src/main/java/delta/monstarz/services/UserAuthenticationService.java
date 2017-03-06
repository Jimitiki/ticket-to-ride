package delta.monstarz.services;

import delta.monstarz.exceptions.loginExceptions.LoginException;
import delta.monstarz.model.account.PersonManager;

/**
 * @author bradcarter
 */
public class UserAuthenticationService
{
	//Data Members
	PersonManager personManager;

	//Singleton Implementation
	private UserAuthenticationService()
	{
		personManager = PersonManager.getInstance();
	}

	private static UserAuthenticationService instance;

	public static UserAuthenticationService getInstance()
	{
		if(instance == null)
		{
			instance = new UserAuthenticationService();
		}
		return instance;
	}

	//Public Methods
	/**
	 * A new user account is made
	 * Each username must be unique
	 * @param username
	 * @param password
	 * @return An authToken which will identify the current session for the user
	 */
	public String register(String username, String password) {
		try {
			return personManager.register(username, password);
		}
		catch (LoginException e){
			return "";
		}
	}

	/**
	 * Users can login using a username and password stored in peep.
	 * @param username
	 * @param password
	 * @return A new auth token for the user.
	 */
	public String login(String username, String password) {
		try {
			return personManager.login(username, password);
		}
		catch (LoginException e){
			return "";
		}
	}

	public boolean personExists(String username) {
		if (personManager.getPersonForUsername(username) != null) {
			return true;
		}
		return false;
	}

}
