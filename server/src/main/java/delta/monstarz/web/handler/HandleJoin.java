package delta.monstarz.web.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

import delta.monstarz.model.CommandManager;
import delta.monstarz.commands.ServerJoinGameCommand;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.JoinGameCommand;

/**
 * Created by oliphaun on 2/14/17.
 */
public class HandleJoin extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            if (!checkAuth_sendHeader(exchange)) {
                return;
            }

	        Args args = parseArgs(exchange);
	        JoinGameCommand command = new JoinGameCommand(args.getStr1(), Integer.parseInt(args.getStr2()));
	        try {
	            CommandManager.execute(new ServerJoinGameCommand(command));
	        } catch (Exception e) {
	        }

	        response = SerDes.serialize(command);

            sendResponse(exchange);
        }
    }
}
