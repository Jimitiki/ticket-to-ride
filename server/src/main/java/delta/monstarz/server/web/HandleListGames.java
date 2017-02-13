package delta.monstarz.server.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.GameListCommand;

/**
 * Created by oliphaun on 2/10/17.
 */

public class HandleListGames extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("get")) {
            if (!checkAuth_sendHeader(exchange)) {
                return;
            }

            Map<String, String> query = QueryParser.parseQuery(exchange.getRequestURI().getRawQuery());
            GameListCommand command = ServerCommunicator.listGames(query.get("username"));
            response = SerDes.serialize(command);

            sendResponse(exchange);
            System.out.println("returned game list");
        }
    }
}
