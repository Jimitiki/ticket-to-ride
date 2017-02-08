package delta.monstarz.server.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import delta.monstarz.shared.Args;
import delta.monstarz.shared.Person;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.SerDes;

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
                    Result res = ServerCommunicator.createGame(args, auth);

//                        BaseCommand command = SerDes.deserializeCommand(reqData, "");
//                        System.out.println(command.getName());
//                        // System.out.println(reqData);
//
//                        IProcessor proc = StringProcessor.getInstance();
//                        Result res = proc.executeCommand(command);
//                        String ser = SerDes.serialize(res);
//
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

                    if (res.getResultStr().equals("")){
                        // No authToken, there is an error
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_CONFLICT, 0); //409
                    }
                    else{
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //200
                    }

                    String ser = SerDes.serialize(res);

                    OutputStream respBody = exchange.getResponseBody();
                    writeString(ser, respBody); //ser
                    respBody.close();
                }
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
    }
}
