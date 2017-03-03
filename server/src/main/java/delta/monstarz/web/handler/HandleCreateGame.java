package delta.monstarz.web.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

import delta.monstarz.model.CommandManager;
import delta.monstarz.model.commands.ServerJoinGameCommand;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.JoinGameCommand;
import delta.monstarz.web.ServerCommunicator;

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

	        Args args = parseArgs(exchange);
            JoinGameCommand command = ServerCommunicator.createGame(args);
	        try {
		        CommandManager.execute(new ServerJoinGameCommand(command));
		        response = SerDes.serialize(command);

	        } catch (Exception e) {
		        e.printStackTrace();
	            response = null;
	        }

	        sendResponse(exchange);
        }
    }
}
