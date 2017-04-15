package delta.monstarz.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
	public static IPersistenceFactory loadPlugin(String jarLocation, String className) throws Exception{
		File f = new File(jarLocation);
		URL[] url = new URL[]{f.toURL()};
		ClassLoader classLoader = PluginLoader.class.getClassLoader();
		URLClassLoader urlCl = new URLClassLoader(new URL[]{f.toURL()}, classLoader);
		Class pluginClass = urlCl.loadClass(className);
		Object o = pluginClass.newInstance();
		if (o instanceof IPersistenceFactory) {
			return (IPersistenceFactory) o;
		} else {
			IPlugin plugin = (IPlugin) o;
			return ((IPersistenceFactory) new TestPlugin());
		}
	}


	private static class TestPlugin implements IPlugin {
		@Override
		public void whoAmI() {
			System.out.println("I am a test plugin");
		}
	}
}
