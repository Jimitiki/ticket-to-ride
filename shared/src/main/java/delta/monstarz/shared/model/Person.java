package delta.monstarz.shared.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Trevor on 2/3/2017.
 */

public class Person implements Serializable {

	private String username;
	private String password;
	private Date lastLogin;
	private HashMap<String, Date> authTokens = new HashMap<>();

	public Person(String name, String pass) {
		username = name;
		password = pass;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public void setAuthToken(String token){
		authTokens.clear();
		authTokens.put(token, new Date());
	}

	public void refreshToken(String token){
		authTokens.put(token, new Date());
	}

	public void removeAuthToken(String token){
		authTokens.remove(token);
	}

	public boolean hasAuthToken(String token){
		return authTokens.containsKey(token);
	}

	public void removeExpiredTokens(Date oldestAllowedTime){
		for (HashMap.Entry<String,Date> entry: authTokens.entrySet()){
			if ( oldestAllowedTime.getTime() > entry.getValue().getTime()){
				authTokens.remove(entry.getKey());
			}
		}
	}
}
