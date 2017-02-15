package delta.monstarz.server.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

import delta.monstarz.server.CommandManager;
import delta.monstarz.server.commands.ServerJoinGameCommand;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.JoinGameCommand;

/**
 * Created by oliphaun on 2/8/17.
 */

public class HandleCreateGame extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            if (!checkAuth_sendHeader(exchange)) {
                return;
            }

            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);
            Args args = SerDes.deserializeArgs(reqData);
            JoinGameCommand command = ServerCommunicator.createGame(args);
	        try {
		        CommandManager.execute((ServerJoinGameCommand) command);
		        response = SerDes.serialize(command);

	        } catch (Exception e) {
		        e.printStackTrace();
	        }
	        response = null;

	        sendResponse(exchange);
        }
    }
}
