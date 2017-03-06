package delta.monstarz.web.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.AuthBadCommand;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.web.QueryParser;
import delta.monstarz.web.ServerCommunicator;

/**
 * Created by oliphaun on 2/10/17.
 */

public class HandleListGames extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if (exchange.getRequestMethod().toLowerCase().equals("get")) {

	        BaseCommand command;
	        Map<String, String> query = QueryParser.parseQuery(exchange.getRequestURI().getRawQuery());

            if (!checkAuth_sendHeader(exchange)) {
                // Send auth bad command
	            command = new AuthBadCommand(query.get("username"));
            }
            else {
	            // Send gameList
                command = ServerCommunicator.listGames(query.get("username"));

            }

	        response = SerDes.serialize(command);
	        sendResponse(exchange);
        }
    }
}
