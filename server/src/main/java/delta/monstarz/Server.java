package delta.monstarz;
import java.io.*;
import java.net.*;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.account.PersonManager;
import delta.monstarz.plugin.IPlugin;
import delta.monstarz.plugin.PluginLoader;
import delta.monstarz.plugin.IPersistenceFactory;
import delta.monstarz.web.handler.HandleCommand;
import delta.monstarz.web.handler.HandleCreateGame;
import delta.monstarz.web.handler.HandleJoin;
import delta.monstarz.web.handler.HandleLogin;
import delta.monstarz.web.handler.HandleRegister;
import delta.monstarz.web.handler.HandleListGames;

public class Server {

	private static final int MAX_WAITING_CONNECTIONS = 12;
	public static IPersistenceFactory plugin;

	private HttpServer server;

	private void run(String portNumber) {
		System.out.println("Initializing HTTP Server");
		try {
			server = HttpServer.create(
					new InetSocketAddress(Integer.parseInt(portNumber)),
					MAX_WAITING_CONNECTIONS);
		}
		catch (IOException e) {
			e.printStackTrace();
			return;
		}

		server.setExecutor(null); // use the default executor

		System.out.println("Creating contexts");
		server.createContext("/command", new HandleCommand());
		server.createContext("/register", new HandleRegister());
		server.createContext("/login", new HandleLogin());
		server.createContext("/create", new HandleCreateGame());
		server.createContext("/join", new HandleJoin());
		server.createContext("/game", new HandleListGames());

		System.out.println("Starting server on port " + portNumber);
		server.start();
	}

	public static void main(String[] args) {

		// Todo: Read the command line arguments
//		plugin = new SerializationFactory();
		plugin = new SQLFactory();

		String portNumber;

		if (args.length != 3){
			System.out.println("Usage is: <port> <FILE.json>");
			return;
		}

		portNumber = args[0];
		GameManager.jsonGameData = args[1];


		try {
			String pluginName = args[2];
			JsonObject plugins = JSONReader.readJSON("server/src/main/assets/plugins.json");
			plugins = plugins.getAsJsonObject("plugins").getAsJsonObject(pluginName);
			String pluginLocation = plugins.get("jarLocation").getAsString();
			String className = plugins.get("className").getAsString();

			plugin = PluginLoader.loadPlugin(pluginLocation, className);

			plugin.getUserDAO().clear();

			PersonManager.getInstance().addUsers(plugin.getUserDAO().getPersons());
		} catch (Exception e) {
			e.printStackTrace();
		}

		new Server().run(portNumber);
	}
}
