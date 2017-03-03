package delta.monstarz.web.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.LoginCommand;
import delta.monstarz.web.ServerCommunicator;

/**
 * Created by oliphaun on 2/4/17.
 */

public class HandleLogin extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (exchange.getRequestMethod().toLowerCase().equals("post")) {
            InputStream reqBody = exchange.getRequestBody();
            String reqData = readString(reqBody);
            Args args = SerDes.deserializeArgs(reqData);
            LoginCommand command = ServerCommunicator.login(args);
            response = SerDes.serialize(command);

            sendResponse(exchange);
        }
    }
}
