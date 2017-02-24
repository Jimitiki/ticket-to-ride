package deltamonstarz.tickettoride.views.gamePlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
	private static GamePresenter presenter;
	private Button drawCard;
	private Button placeTrain;
	private Button viewCards;
	private Button viewHistory;
	private Button viewChat;
	private Button demo;

	public GameFragment() {}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment GameFragment.
	 */
	public static GameFragment newInstance() {
		GameFragment fragment = new GameFragment();
		presenter = GamePresenter.getInstance();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		drawCard = (Button) v.findViewById(R.id.draw_card_button);
		placeTrain = (Button) v.findViewById(R.id.place_train_button);
		viewCards = (Button) v.findViewById(R.id.destination_cards_button);
		viewHistory = (Button) v.findViewById(R.id.history_button);
		viewChat = (Button) v.findViewById(R.id.chat_button);
		demo = (Button) v.findViewById(R.id.demo_button);

		drawCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("drawing card");
			}
		});

		placeTrain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("claiming route");
			}
		});

		viewCards.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("opening destination card view");
			}
		});

		viewHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("opening history view");
			}
		});

		viewChat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("opening chat");
			}
		});

		demo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				advanceDemo();
			}
		});

		return v;
	}

	private void advanceDemo() {

	}
}
