package delta.monstarz.web.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import delta.monstarz.model.CommandManager;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;
import delta.monstarz.web.QueryParser;

public class HandleCommand extends ServerHandler {
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		if (!checkAuth_sendHeader(exchange)) {
			return;
		}

		if (exchange.getRequestMethod().toLowerCase().equals("get")) { //getting command list for a game
			Map<String, String> query = QueryParser.parseQuery(exchange.getRequestURI().getRawQuery());
			CommandListCommand command = new CommandListCommand(query.get("username"));
			command.setCommands(CommandManager.getCommands(Integer.parseInt(query.get("gameID")), query.get("username"),
					Integer.parseInt(query.get("curCommand"))));
			response = SerDes.serialize(command);
		} else if (exchange.getRequestMethod().toLowerCase().equals("post")) { //sending a command to a game
			InputStream reqBody = exchange.getRequestBody();
			String reqData = readString(reqBody);
			BaseCommand command = SerDes.deserializeCommand(reqData, COMMAND_PREFIX);
			try {
				CommandManager.execute(command);
			} catch (Exception e) {
				e.printStackTrace();
			}

			response = "{success: ok}";
		}
		sendResponse(exchange);
	}


}
