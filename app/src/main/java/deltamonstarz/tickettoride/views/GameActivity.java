package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
	private Button mStartGameButton;

	private GamePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		presenter = GamePresenter.getInstance();
		presenter.setActivity(this);

		mPlayersText = (TextView) findViewById(R.id.playersText);
		mStartGameButton = (Button) findViewById(R.id.startGame);
		mStartGameButton.setEnabled(false);
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
		if (players.size() > 1) {
			mStartGameButton.setEnabled(true);
			mStartGameButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					presenter.startGame();
				}
			});
		}
	}

	public void onGameStart() {
		Toast toast = Toast.makeText(this, "Game Started", Toast.LENGTH_LONG);
		toast.show();
	}

	public void logout() {
		Intent intent = new Intent(getBaseContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_LONG);
		toast.show();
	}
}
