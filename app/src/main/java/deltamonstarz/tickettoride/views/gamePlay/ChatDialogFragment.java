package deltamonstarz.tickettoride.views.gamePlay;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import delta.monstarz.shared.Message;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.ChatPresenter;

public class ChatDialogFragment extends DialogFragment {
	private Button sendButton;
	private EditText messageText;
	private ChatPresenter presenter;

	public ChatDialogFragment() {
		presenter = ChatPresenter.getInstance();
	}

//	@TargetApi(21)
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//
//
//		builder.setView(R.layout.fragment_chat_dialog);
//		presenter = ChatPresenter.getInstance();
//		Dialog dialog = new Dialog();//builder.create();
////		dialog.setCanceledOnTouchOutside(false);
////		dialog.setCancelable(false);
//		return dialog;
//	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_chat_dialog, container, false);
		sendButton = (Button) v.findViewById(R.id.sendMessageButton);
		messageText = (EditText) v.findViewById(R.id.messageText);
		sendButton.setOnClickListener(new OnSendClickListener());
		return v;
	}

	public void onReceiveMessage(Message message) {

	}

	private class OnSendClickListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			String message = messageText.getText().toString();
			System.out.println(message);
			presenter.sendMessage(message);
			messageText.setText("");
		}
	}
}
