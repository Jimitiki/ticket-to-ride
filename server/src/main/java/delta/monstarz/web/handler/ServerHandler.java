package delta.monstarz.web.handler;
import java.io.*;
import java.net.*;

import com.sun.net.httpserver.*;

import delta.monstarz.model.account.PersonManager;

/**
 * Created by oliphaun on 2/2/17.
 */

public class ServerHandler implements HttpHandler {
    protected String response;
    protected static final String COMMAND_PREFIX = "delta.monstarz.server.commands.Server";

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {

                    String authToken = reqHeaders.getFirst("Authorization");
                    if (authToken.equals("afj232hj2332")) {

                        InputStream reqBody = exchange.getRequestBody();
                        String reqData = readString(reqBody);

                        OutputStream respBody = exchange.getResponseBody();
                        writeString(reqData, respBody); //ser
                        respBody.close();
                        success = true;
                    }
                }
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

    protected String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    protected void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    protected boolean checkAuth_sendHeader(HttpExchange exchange) throws IOException {
        Headers reqHeaders = exchange.getRequestHeaders();
        boolean authorized = false;
        if (reqHeaders.containsKey("Authorization")) {
            String auth = reqHeaders.getFirst("Authorization");
            PersonManager personManager = PersonManager.getInstance();
            authorized = personManager.authTokenIsValid(auth);
        }

        return authorized;
    }

    protected void sendResponse(HttpExchange exchange) throws IOException {
        try {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream respBody = exchange.getResponseBody();
            writeString(response, respBody);
            respBody.close();
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();
        }
    }
}

