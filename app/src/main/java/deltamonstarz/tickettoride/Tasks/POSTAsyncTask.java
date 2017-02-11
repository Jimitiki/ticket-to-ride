package deltamonstarz.tickettoride.Tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;

public class POSTAsyncTask extends HTTPAsyncTask {

	@Override
	protected BaseCommand doInBackground(String... params) {
		try {
			URL url = new URL(params[0]);

			HttpURLConnection http = (HttpURLConnection)url.openConnection();

			http.setRequestMethod("POST");
			http.setDoOutput(true); // There is a request body


			http.addRequestProperty("Authorization", params[1]);
			http.addRequestProperty("Content-Type", "application/json");
			http.addRequestProperty("Accept", "application/json");

			http.connect();

			OutputStream reqBody = http.getOutputStream();
			writeString(params[2], reqBody);
			reqBody.close();

			InputStream respBody = http.getInputStream();
			String respData = readString(respBody);
			System.out.print("respData");
			return SerDes.deserializeCommand(respData, COMMAND_PREFIX);

//			if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
//				InputStream respBody = http.getInputStream();
//				String respData = readString(respBody);
//				BaseCommand command = SerDes.deserializeCommand(respData, COMMAND_PREFIX);
//				return command;
//				// System.out.println(respData);
//			} else if (http.getResponseCode() == HttpURLConnection.HTTP_CONFLICT) {
//
//				BaseCommand command = SerDes.deserializeCommand(respData, COMMAND_PREFIX);
//				return command;
//			}
//			else if (http.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
//				Result res = new Result();
//				res.setResultStr("");
//				return command;
//			}
//			else {
//				System.out.println("ERROR: " + http.getResponseMessage());
//			}
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
