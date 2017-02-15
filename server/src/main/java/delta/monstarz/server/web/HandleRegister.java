package delta.monstarz.server.web;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.LoginCommand;

/**
 * Created by oliphaun on 2/4/17.
 */

public class HandleRegister extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);
            Args args = SerDes.deserializeArgs(reqData);
            LoginCommand command = ServerCommunicator.register(args);
            response = SerDes.serialize(command);
            sendResponse(exchange);
        }
    }
}
