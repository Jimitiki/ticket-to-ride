package deltamonstarz.tickettoride.views.gamePlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameLobbyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameLobbyFragment extends Fragment {
	private TextView playerText;
	private Button startGameButton;
	private static GamePresenter presenter;

	public GameLobbyFragment() {
		// Required empty public constructor
	}

	public static GameLobbyFragment newInstance() {
		GameLobbyFragment fragment = new GameLobbyFragment();
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
		// Inflate the layout for this fragment
		View v = inflater.inflate(R.layout.fragment_game_lobby, container, false);
		playerText = (TextView) v.findViewById(R.id.playersText);
		startGameButton = (Button) v.findViewById(R.id.startGame);
		startGameButton.setEnabled(false);
		return v;
	}

	public void onPlayerJoin(List<String> players) {
		StringBuilder sb = new StringBuilder("Players: ");
		for(int i = 0; i < players.size(); i++)
		{
			sb.append(players.get(i));
			sb.append(i < players.size() - 1 ? ", " : "");
		}
		sb.append(".");
		playerText.setText(sb.toString());
		if (players.size() > 1 && !ClientModel.getInstance().isStarted()) {
			startGameButton.setEnabled(true);
			startGameButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					presenter.startGame();
				}
			});
		}
	}
}
