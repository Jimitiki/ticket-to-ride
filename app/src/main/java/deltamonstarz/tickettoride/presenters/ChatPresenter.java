package deltamonstarz.tickettoride.presenters;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;

import java.util.Observable;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.commands.SendMessageCommand;
import deltamonstarz.tickettoride.commands.ClientSendMessageCommand;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.gamePlay.ChatDialogFragment;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;

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
	public void update(UpdateType updateType) {
		if (updateType == UpdateType.CHAT) {
			chatFragment.onReceiveMessage(model.getLastMessage());
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
		//proxy.sendCommand(model.getAuthToken(), new SendMessageCommand(model.getUsername(), model.getGameID(), message));
		new ClientSendMessageCommand(model.getUsername(), model.getGameID(), message).execute();
		//chatFragment.onReceiveMessage(message);
	}
}
