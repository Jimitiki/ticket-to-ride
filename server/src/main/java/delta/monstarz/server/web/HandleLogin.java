package delta.monstarz.server.web;

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
 * Created by oliphaun on 2/4/17.
 */

public class HandleLogin extends ServerHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                Args args = SerDes.deserializeArgs(reqData);

                Result res = ServerCommunicator.login(args);

	            if (res.getResultStr().equals("")){
		            // No authToken, there is an error
		            exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0); //403
	            }
	            else{
		            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0); //200
	            }


                String ser = SerDes.serialize(res);

                OutputStream respBody = exchange.getResponseBody();
                writeString(ser, respBody);
                respBody.close();
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            e.printStackTrace();
        }
    }
}
