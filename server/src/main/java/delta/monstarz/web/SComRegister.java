package delta.monstarz.web;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import delta.monstarz.IServer;
import delta.monstarz.Person;
import delta.monstarz.Result;
import delta.monstarz.SerDes;
import delta.monstarz.ServerFacade;

/**
 * Created by oliphaun on 2/4/17.
 */

public class SComRegister extends ServerCommunicator {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                Person peep = SerDes.deserializePerson(reqData);

                IServer serv = ServerFacade.getInstance();
                String auth = serv.register(peep);

//                String ser = SerDes.serialize(peep);

                OutputStream respBody = exchange.getResponseBody();
                writeString(auth, respBody);
                respBody.close();
                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
    }
}