package delta.monstarz;

import java.util.Date;

/**
 * Created by Trevor on 2/3/2017.
 */

public class Person {

	private String username;
	private String password;
	private Date lastLogin;

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
}
