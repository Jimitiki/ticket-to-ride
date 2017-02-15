package deltamonstarz.tickettoride.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

public class POSTAsyncTask extends HTTPAsyncTask {

	@Override
	protected BaseCommand doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);

			HttpURLConnection http = (HttpURLConnection)url.openConnection();

			http.setRequestMethod("POST");
			http.setDoOutput(true);


			http.addRequestProperty("Authorization", params[1]);
			http.addRequestProperty("Content-Type", "application/json");
			http.addRequestProperty("Accept", "application/json");

			http.connect();

			OutputStream reqBody = http.getOutputStream();
			writeString(params[2], reqBody);
			reqBody.close();

			InputStream respBody = http.getInputStream();
			String respData = readString(respBody);
			System.out.print(respData);
			return SerDes.deserializeCommand(respData, COMMAND_PREFIX);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static void writeString(String str, OutputStream os) throws IOException {
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(str);
		sw.flush();
	}
}
