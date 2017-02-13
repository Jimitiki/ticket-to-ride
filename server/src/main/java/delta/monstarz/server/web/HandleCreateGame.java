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
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String auth = reqHeaders.getFirst("Authorization");

                    InputStream reqBody = exchange.getRequestBody();
                    String reqData = readString(reqBody);
                    Args args = SerDes.deserializeArgs(reqData);
                    CreateGameCommand command = ServerCommunicator.createGame(args, auth);
                    String ser = SerDes.serialize(command);

                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //200
                    OutputStream respBody = exchange.getResponseBody();
                    writeString(ser, respBody);
                    respBody.close();
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
    }
}
