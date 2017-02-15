package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Player;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.presenters.GameSelectorPresenter;


public class GameActivity extends AppCompatActivity
{

	private TextView mPlayersText;

	private GamePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		presenter = GamePresenter.getInstance();
		presenter.setActivity(this);
		presenter.PollGameHistory();

		mPlayersText = (TextView) findViewById(R.id.playersText);
	}

	public static Intent newIntent(Context packageContext)
	{
		return new Intent(packageContext, GameActivity.class);
	}

	public void onLeaveGame() {}

	public void onGameUpdate(List<String> players1) {

		List<Player> players = new ArrayList<>();
		players.add(new Player("Brad"));
		players.add(new Player("Chris"));
		players.add(new Player("Trevor"));
		players.add(new Player("Alex"));


		StringBuilder sb = new StringBuilder();
		sb.append("Player Count: ");
		sb.append(players.size());
		sb.append(". Players: ");
		for(int i = 0; i < players.size(); i++)
		{
			Player player = players.get(i);
			sb.append(player.getUsername());
			sb.append(i < sb.length() - 1 ? ", " : "");
		}
		sb.append(".");
		mPlayersText.setText(sb.toString());

	}

	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_LONG);
		toast.show();
	}
}
