package delta.monstarz.shared.commands;

/**
 * Created by lyman126 on 2/15/17.
 */

public class AuthBadCommand extends BaseCommand {

	public AuthBadCommand(String username){
		super(username);
		name = "AuthBadCommand";
	}


	@Override
	public void execute() {

	}
}
