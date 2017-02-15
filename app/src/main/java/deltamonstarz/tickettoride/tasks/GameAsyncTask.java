package deltamonstarz.tickettoride.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import deltamonstarz.tickettoride.commands.ClientAuthBadCommand;
import deltamonstarz.tickettoride.commands.ConnectionErrorCommand;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class GameAsyncTask extends AsyncTask<String, Integer, Integer> {
	@Override
	protected Integer doInBackground(String... params) {
		try {
			HttpURLConnection http = HTTPRequests.POST(params[0], params[1], params[2]);
			return http.getResponseCode();
		}
		catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	protected void onPostExecute(Integer status) {
		switch (status) {
			case HttpURLConnection.HTTP_UNAUTHORIZED:
				new ClientAuthBadCommand("").execute();
				break;
			case HttpURLConnection.HTTP_INTERNAL_ERROR:
			case -1:
				new ConnectionErrorCommand("").execute();
		}
	}
}
