package deltamonstarz.tickettoride.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import delta.monstarz.shared.commands.BaseCommand;
import deltamonstarz.tickettoride.presenters.LoginPresenter;

abstract class HTTPAsyncTask extends AsyncTask<String, Integer, BaseCommand> {
	final static String COMMAND_PREFIX = "deltamonstarz.tickettoride.commands.Client";
	protected static final int TIMEOUT = 4000;

	@Override
	protected void onPostExecute(BaseCommand command) {
		if (command != null) {
			command.execute();
		}
	}

	static String readString(InputStream is) throws IOException {
		StringBuilder sb = new StringBuilder();
		InputStreamReader sr = new InputStreamReader(is);
		char[] buf = new char[1024];
		int len;
		while ((len = sr.read(buf)) > 0) {
			sb.append(buf, 0, len);
		}
		return sb.toString();
	}
}
