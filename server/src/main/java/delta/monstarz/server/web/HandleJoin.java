package delta.monstarz.server.web;

import com.sun.corba.se.spi.activation.Server;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import delta.monstarz.server.CommandManager;
import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import delta.monstarz.shared.commands.CommandListCommand;
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

	        InputStream reqBody = exchange.getRequestBody();
	        String reqData = readString(reqBody);
	        Args args = SerDes.deserializeArgs(reqData);
	        JoinGameCommand command = new JoinGameCommand(args.getStr1(), Integer.parseInt(args.getStr2()));
	        try {
	            CommandManager.execute(command);
	        } catch (Exception e) {
	        }

	        response = SerDes.serialize(command);

            sendResponse(exchange);
        }
    }
}
