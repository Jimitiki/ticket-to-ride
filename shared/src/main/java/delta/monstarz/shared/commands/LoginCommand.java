package delta.monstarz.shared.commands;

public class LoginCommand extends BaseCommand {
	protected String authToken;
	protected boolean loginSuccessful;

	public LoginCommand(String username, int gameID) {
		super(username, gameID);
		name = "LoginCommand";
	}

	public String getAuthToken() {
		return authToken;
	}

	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}

	@Override
	public void execute() {

	}
}
