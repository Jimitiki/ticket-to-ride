package deltamonstarz.tickettoride.views.gamePlay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Message;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.ChatPresenter;

public class ChatDialogFragment extends DialogFragment {
	private GameActivity activity;
	private Button sendButton;
	private EditText messageText;
	private RecyclerView messageListView;
	private MessageListAdapter adapter;
	private ChatPresenter presenter;

	public ChatDialogFragment() {
		presenter = ChatPresenter.getInstance();
	}

	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_chat_dialog, container, false);
		sendButton = (Button) v.findViewById(R.id.sendMessageButton);
		messageText = (EditText) v.findViewById(R.id.messageText);
		sendButton.setOnClickListener(new OnSendClickListener());

		messageListView = (RecyclerView) v.findViewById(R.id.messagesView);
		messageListView.setHasFixedSize(true);

		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
		//linearLayoutManager.setStackFromEnd(true);
		messageListView.setLayoutManager(linearLayoutManager);

		presenter.setChatFragment(this);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeAdapter();
		adapter.setMessages(presenter.getMessages());

	}

	public void onReceiveMessage(Message message) {
		initializeAdapter();
		adapter.addMessage(message);
		adapter.notifyDataSetChanged();
	}

	private void initializeAdapter() {
		if (messageListView.getAdapter() == null) {
			adapter = new MessageListAdapter();
			messageListView.setAdapter(adapter);
		}
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

	private class MessageContainer extends RecyclerView.ViewHolder {
		private TextView usernameText;
		private TextView messageText;
		private View view;

		public MessageContainer(View itemView) {
			super(itemView);
			view = itemView;

			usernameText = (TextView) view.findViewById(R.id.username);
			messageText = (TextView) view.findViewById(R.id.messageText);
		}

		void setMessage(Message message) {
			usernameText.setText(message.getUsername());
			messageText.setText(message.getMessage());
		}
	}

	private class MessageListAdapter extends RecyclerView.Adapter<MessageContainer> {
		List<Message> messages;

		MessageListAdapter() {
			messages = new ArrayList<>();
		}

		@Override
		public MessageContainer onCreateViewHolder(ViewGroup parent, int viewType) {
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_list_item, parent, false);
			return new MessageContainer(v);
		}

		@Override
		public void onBindViewHolder(MessageContainer holder, int position) {
			holder.setMessage(messages.get(position));
		}

		@Override
		public int getItemCount() {
			return messages.size();
		}

		void addMessage(Message message) {
			messages.add(message);
		}

		void setMessages(List<Message> messages) {
			this.messages = messages;
		}
	}
}
