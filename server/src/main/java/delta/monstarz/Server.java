package delta.monstarz;
import java.io.*;
import java.net.*;

import com.google.gson.JsonObject;
import com.sun.net.httpserver.*;

import delta.monstarz.model.GameManager;
import delta.monstarz.model.account.PersonManager;
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
		ArgumentParser parser = new ArgumentParser(args);
		if (!parser.validArgs()){
			System.out.println("Usage is: -p <pluginName ('serialize' or 'sqlite')> -d <diffCount> -c (optional)");
			return;
		}

		String portNumber = "8080";
		GameManager.jsonGameData = "server/src/main/assets/preferences.json";

		try {
			String pluginName = parser.getPluginName();
			JsonObject plugins = JSONReader.readJSON("server/src/main/assets/plugins.json");
			plugins = plugins.getAsJsonObject("plugins").getAsJsonObject(pluginName);
			String pluginLocation = plugins.get("jarLocation").getAsString();
			String className = plugins.get("className").getAsString();

			plugin = PluginLoader.loadPlugin(pluginLocation, className);

			PersonManager.getInstance().addUsers(plugin.getUserDAO().getPersons());
			GameManager.getInstance().addGames(plugin.getGameDAO().getGames());
		} catch (Exception e) {
			e.printStackTrace();
		}
		int diffCount = parser.getDiffCount();
		boolean clear = parser.getClear();
		new Server().run(portNumber);
	}

	private static class ArgumentParser {
		private boolean clear = false;
		private String pluginName = null;
		private int diffCount = -1;

		ArgumentParser(String[] args) {
			for (int i = 0; i < args.length; i++) {
				switch (args[i]) {
					case "-p":
						pluginName = args[++i];
						break;
					case "-d":
						diffCount = Integer.valueOf(args[++i]);
						break;
					case "-c":
						clear = true;
				}
			}
		}

		boolean getClear() {
			return clear;
		}

		String getPluginName() {
			return pluginName;
		}

		int getDiffCount() {
			return diffCount;
		}

		boolean validArgs() {
			return pluginName != null && diffCount >= 0;
		}
	}
}
