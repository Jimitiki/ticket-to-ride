package deltamonstarz.tickettoride.tasks;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class HTTPRequests {
	protected static final int TIMEOUT = 2000;

	public static HttpURLConnection POST(String address, String authToken, String params) throws IOException {
		URL url = new URL(address);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();

		http.setRequestMethod("POST");
		http.setDoOutput(true);
		http.addRequestProperty("Content-Type", "application/json");
		connect(http, authToken);

		OutputStream reqBody = http.getOutputStream();
		writeString(params, reqBody);
		reqBody.close();
		return http;

	}

	public static HttpURLConnection GET(String address, String authToken) throws IOException {
		URL url = new URL(address);
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.setDoOutput(false);
		connect(http, authToken);
		return http;
	}

	private static void connect(HttpURLConnection http, String authToken) throws IOException{

		http.addRequestProperty("Authorization", authToken);
		http.addRequestProperty("Accept", "application/json");

		http.setConnectTimeout(TIMEOUT);
		http.connect();
	}

	private static void writeString(String str, OutputStream os) throws IOException {
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(str);
		sw.flush();
	}
}
