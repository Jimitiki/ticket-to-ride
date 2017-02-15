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
			HttpURLConnection http = HTTPRequests.GET(params[0], params[1]);
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
