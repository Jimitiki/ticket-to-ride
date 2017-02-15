package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Player;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;


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

		mPlayersText = (TextView) findViewById(R.id.playersText);
	}

	public static Intent newIntent(Context packageContext)
	{
		return new Intent(packageContext, GameActivity.class);
	}

	@Override
	protected void onResume() {
		super.onResume();
		presenter.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		presenter.onPause();
	}

	public void onGameUpdate(List<String> players) {
		StringBuilder sb = new StringBuilder("Players: ");
		for(int i = 0; i < players.size(); i++)
		{
			sb.append(players.get(i));
			sb.append(i < players.size() - 1 ? ", " : "");
		}
		sb.append(".");
		mPlayersText.setText(sb.toString());

	}

	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_LONG);
		toast.show();
	}
}
