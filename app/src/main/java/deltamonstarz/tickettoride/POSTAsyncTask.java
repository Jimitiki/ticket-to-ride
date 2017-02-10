package deltamonstarz.tickettoride;

import android.os.AsyncTask;

import delta.monstarz.shared.ICallbackHandler;
import delta.monstarz.shared.Result;
import delta.monstarz.shared.commands.BaseCommand;

public class POSTAsyncTask extends AsyncTask<String, Integer, BaseCommand> {

	@Override
	protected BaseCommand doInBackground(String... params) {
		return null;
	}

	@Override
	protected void onPostExecute(BaseCommand command) {
		command.execute();
	}
}
