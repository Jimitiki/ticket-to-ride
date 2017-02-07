package deltamonstarz.tickettoride;
import java.io.*;
import java.net.*;

import delta.monstarz.shared.Result;
import delta.monstarz.shared.SerDes;

/**
 * Created by oliphaun on 2/3/17.
 */

public class ClientCommunicator {

    public static Result connectAndSend(String serverHost, String serverPort, String path, String reqData) {
        // System.out.println("http://" + serverHost + ":" + serverPort + path);
        try {
            URL url = new URL("http://" + serverHost + ":" + serverPort + path);

            HttpURLConnection http = (HttpURLConnection)url.openConnection();

            http.setRequestMethod("POST");
            http.setDoOutput(true); // There is a request body

            http.addRequestProperty("Authorization", "afj232hj2332");
            http.addRequestProperty("Content-Type", "application/json");
            http.addRequestProperty("Accept", "application/json");

            http.connect();

            OutputStream reqBody = http.getOutputStream();
            writeString(reqData, reqBody);
            reqBody.close();

            if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
                InputStream respBody = http.getInputStream();
                String respData = readString(respBody);
                Result res = SerDes.deserializeResult(respData);
                return res;
                // System.out.println(respData);
            } else if (http.getResponseCode() == HttpURLConnection.HTTP_CONFLICT) {
                Result res = new Result();
                res.setResultStr("");
	            return res;
            }
	        else if (http.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
			    Result res = new Result();
			    res.setResultStr("");
			    return res;
		    }
            else {
                System.out.println("ERROR: " + http.getResponseMessage());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null; //new Result();
    }

    private static String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    private static void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
