package delta.monstarz.server.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.Map;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;

public class HandleCommand extends ServerHandler {
	private static final String COMMAND_PREFIX = "delta.monstarz.server.commands.Server";

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		try {
			Headers reqHeaders = exchange.getRequestHeaders();
			if (reqHeaders.containsKey("Authorization")) {
				String auth = reqHeaders.getFirst("Authorization");
				if (exchange.getRequestMethod().toLowerCase().equals("get")) {
					Map<String, String> query = QueryParser.parseQuery(exchange.getRequestURI().getRawQuery());

					CommandListCommand command = new CommandListCommand(query.get("username"));//ServerCommunicator.listGames(auth, query.get("username"));
					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //200

					String ser = SerDes.serialize(command);

					OutputStream respBody = exchange.getResponseBody();
					writeString(ser, respBody);
					respBody.close();
				} else if (exchange.getRequestMethod().toLowerCase().equals("post")) {
					InputStream reqBody = exchange.getRequestBody();
					String reqData = readString(reqBody);
					BaseCommand command = SerDes.deserializeCommand(reqData, COMMAND_PREFIX);

					exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //200
					OutputStream respBody = exchange.getResponseBody();
					writeString(null, respBody);
					respBody.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
			exchange.getResponseBody().close();
		}
	}


}
