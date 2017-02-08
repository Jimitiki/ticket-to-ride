package deltamonstarz.tickettoride;

import android.os.AsyncTask;

import delta.monstarz.shared.ICallbackHandler;
import delta.monstarz.shared.Result;

public class POSTAsyncTask extends AsyncTask<String, Integer, Result> {
	private ICallbackHandler callbackHandler;

	public void setCallbackHandler(ICallbackHandler callbackHandler) {
		this.callbackHandler = callbackHandler;
	}

	@Override
	protected Result doInBackground(String... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		callbackHandler.execute(result);
	}
}
