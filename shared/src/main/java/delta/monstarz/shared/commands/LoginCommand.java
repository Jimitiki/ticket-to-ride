package delta.monstarz.shared.commands;

public class LoginCommand extends BaseCommand {
	protected String authToken;
	protected boolean loginSuccessful;
	protected boolean isRegister;

	public LoginCommand(String username, boolean isRegister) {
		super(username);
		name = "LoginCommand";
		this.isRegister = isRegister;
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

	public boolean isRegister() {
		return isRegister;
	}

	@Override
	public void execute() {

	}
}
