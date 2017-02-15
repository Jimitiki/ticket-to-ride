package deltamonstarz.tickettoride.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.commands.ConnectionErrorCommand;

public class GETAsyncTask extends HTTPAsyncTask {


	@Override
	protected BaseCommand doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);

			HttpURLConnection http = (HttpURLConnection)url.openConnection();

			http.setRequestMethod("GET");
			http.setDoOutput(false);	// There is no request body

			http.addRequestProperty("Authorization", params[1]);
			http.addRequestProperty("Accept", "application/json");

			http.setConnectTimeout(TIMEOUT);
			http.connect();
			InputStream respBody = http.getInputStream();
			String respData = readString(respBody);
			return SerDes.deserializeCommand(respData, COMMAND_PREFIX);
		}
		catch (ConnectException e){ // Server address is likely not correct
			return new ConnectionErrorCommand("");
		}
		catch (SocketTimeoutException e){   // Port is likely not correct
			// Stuff
			return new ConnectionErrorCommand("");
		}
		catch (IOException e) {
			e.printStackTrace();
			return new ConnectionErrorCommand("");
		}
	}
}
