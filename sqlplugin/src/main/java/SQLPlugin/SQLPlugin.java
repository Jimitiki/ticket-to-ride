package SQLPlugin;

import delta.monstarz.plugin.IPlugin;

public class SQLPlugin implements IPlugin {
	@Override
	public void whoAmI() {
		System.out.println("I am the SQL Plugin.");
	}
}
