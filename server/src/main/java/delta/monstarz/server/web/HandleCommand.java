package delta.monstarz.server.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import delta.monstarz.server.CommandManager;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;
import delta.monstarz.shared.commands.JoinGameCommand;

public class HandleCommand extends ServerHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (!checkAuth_sendHeader(exchange)) {
			return;
		}

		if (exchange.getRequestMethod().toLowerCase().equals("get")) { //getting command list for a game
			Map<String, String> query = QueryParser.parseQuery(exchange.getRequestURI().getRawQuery());
			CommandListCommand command = new CommandListCommand(query.get("username"));//ServerCommunicator.listGames(auth, query.get("username"));
			response = SerDes.serialize(command);
		} else if (exchange.getRequestMethod().toLowerCase().equals("post")) { //sending a command to a game
			InputStream reqBody = exchange.getRequestBody();
			String reqData = readString(reqBody);
			BaseCommand command = SerDes.deserializeCommand(reqData, COMMAND_PREFIX);
			try {
				CommandManager.execute(command);
			} catch (Exception e) {
			}
			BaseCommand clientcommand = new JoinGameCommand(command.getUsername(), command.getGameID());

			response = null;
		}
		sendResponse(exchange);
	}


}
