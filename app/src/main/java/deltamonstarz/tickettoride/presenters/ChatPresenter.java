package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.Observable;

import delta.monstarz.shared.Message;
import deltamonstarz.tickettoride.views.gamePlay.ChatDialogFragment;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;

/**
 * Created by cwjohn42 on 2/27/17.
 */

public class ChatPresenter extends BasePresenter {
	private static ChatPresenter presenter;
	private GameActivity activity;
	private ChatDialogFragment chatFragment;

	private ChatPresenter() {}

	public static ChatPresenter getInstance() {
		if (presenter == null) {
			presenter = new ChatPresenter();
		}
		return presenter;
	}

	public void setChatFragment(ChatDialogFragment chatFragment) {
		this.chatFragment = chatFragment;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Message) {
			Message message = (Message) arg;
		}
	}

	@Override
	public void onConnectionError() {

	}

	@Override
	public void logOut() {

	}

	@Override
	public AppCompatActivity getActivity() {
		return null;
	}

	public void sendMessage(String chat) {
		Message message = new Message(chat, model.getUsername());

	}
}
