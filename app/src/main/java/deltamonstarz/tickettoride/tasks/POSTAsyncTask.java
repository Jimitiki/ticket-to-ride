package deltamonstarz.tickettoride.tasks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import delta.monstarz.shared.SerDes;
import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.commands.ConnectionErrorCommand;

public class POSTAsyncTask extends HTTPAsyncTask {
	@Override
	protected BaseCommand doInBackground(String... params) {
		try {
			HttpURLConnection http = HTTPRequests.POST(params[0], params[1], params[2]);//(

			InputStream respBody = http.getInputStream();
			String respData = readString(respBody);
			BaseCommand command = SerDes.deserializeCommand(respData, COMMAND_PREFIX);
			respBody.close();
			return command;
		}
		catch (SocketTimeoutException e){   // Port is likely not correct
			return new ConnectionErrorCommand("");
		}
		catch (IOException e) {
			e.printStackTrace();
			return new ConnectionErrorCommand("");
		}
	}

	private static void writeString(String str, OutputStream os) throws IOException {
		OutputStreamWriter sw = new OutputStreamWriter(os);
		sw.write(str);
		sw.flush();
	}
}
