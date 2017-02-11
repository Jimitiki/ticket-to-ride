package deltamonstarz.tickettoride.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;

public class GETAsyncTask extends POSTAsyncTask {

	@Override
	protected BaseCommand doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);

			HttpURLConnection http = (HttpURLConnection)url.openConnection();

			http.setRequestMethod("GET");
			http.setDoOutput(false);	// There is no request body

			http.addRequestProperty("Authorization", params[1]);
			http.addRequestProperty("Accept", "application/json");

			http.connect();
			InputStream respBody = http.getInputStream();
			String respData = readString(respBody);
			return SerDes.deserializeCommand(respData, COMMAND_PREFIX);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
