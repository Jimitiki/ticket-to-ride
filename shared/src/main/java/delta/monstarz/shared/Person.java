package delta.monstarz.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Trevor on 2/3/2017.
 */

public class Person {

	private String username;
	private String password;
	private Date lastLogin;
	private List<String> authTokens = new ArrayList<>();

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

	public void addAuthToken(String token){
		authTokens.add(token);
	}

	public boolean hasAuthToken(String token){
		return authTokens.contains(token);
	}
}
