package delta.monstarz.server.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.CreateGameCommand;

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
            CreateGameCommand command = ServerCommunicator.createGame(args);
            response = SerDes.serialize(command);

            sendResponse(exchange);
        }
    }
}
