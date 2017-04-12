package delta.monstarz.plugin;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class PluginLoader {
	public static IPlugin loadPlugin(String jarLocation, String className) throws Exception{
		File f = new File(jarLocation);
		URL[] url = new URL[]{f.toURL()};
		URLClassLoader urlCl = new URLClassLoader(new URL[]{f.toURL()}, System.class.getClassLoader());
		Class log4jClass = urlCl.loadClass(className);
		Object o = log4jClass.newInstance();
		if (o instanceof IPlugin) {
			return (IPlugin) o;
		} else {
			IPlugin plugin = (IPlugin) o;
			return ((IPlugin) new TestPlugin());
		}
	}


	private static class TestPlugin implements IPlugin {
		@Override
		public void whoAmI() {
			System.out.println("I am a test plugin");
		}
	}
}
