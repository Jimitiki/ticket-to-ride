package delta.monstarz;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

import delta.monstarz.web.handler.HandleCommand;
import delta.monstarz.web.handler.HandleCreateGame;
import delta.monstarz.web.handler.HandleJoin;
import delta.monstarz.web.handler.HandleLogin;
import delta.monstarz.web.handler.HandleRegister;
import delta.monstarz.web.handler.HandleListGames;

public class Server {

	private static final int MAX_WAITING_CONNECTIONS = 12;

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

		System.out.println("Starting server");
		server.start();
	}

	public static void main(String[] args) {
		System.out.println("I'm a server. Currently I just authenticate and list games");
		String portNumber;
		if(args.length == 0) {
			portNumber = "8080";
		} else {
			portNumber = args[0];
		}

		new Server().run(portNumber);
	}
}
