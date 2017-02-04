package delta.monstarz.server.web;
import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;

/**
 * Created by oliphaun on 2/2/17.
 */

public class ServerCommunicator implements HttpHandler {

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
//                        BaseCommand command = SerDes.deserializeCommand(reqData, "");
//                        System.out.println(command.getName());
//                        // System.out.println(reqData);
//
//                        IProcessor proc = StringProcessor.getInstance();
//                        Result res = proc.executeCommand(command);
//                        String ser = SerDes.serialize(res);
//
//                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);

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
}

