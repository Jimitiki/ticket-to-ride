import pluginInterface.IPlugin;

public class SerializePlugin implements IPlugin {
	@Override
	public void whoAmI() {
		System.out.println("I am the serialize plugin");
	}
}
