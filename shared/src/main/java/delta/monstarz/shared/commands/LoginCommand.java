package delta.monstarz.shared.commands;

public class LoginCommand extends BaseCommand {
	protected String authToken;
	protected boolean loginSuccessful;

	public LoginCommand(String username) {
		super(username);
		name = "LoginCommand";
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	public boolean isLoginSuccessful() {
		return loginSuccessful;
	}

	public void setLoginSuccessful(boolean loginSuccessful) {
		this.loginSuccessful = loginSuccessful;
	}

	@Override
	public void execute() {

	}
}
