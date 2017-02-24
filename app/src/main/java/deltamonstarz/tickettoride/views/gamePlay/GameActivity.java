package deltamonstarz.tickettoride.views.gamePlay;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import deltamonstarz.tickettoride.ClientModel;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.views.LoginActivity;
import deltamonstarz.tickettoride.views.MapFragment;


public class GameActivity extends AppCompatActivity
{


	private GamePresenter presenter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		presenter = GamePresenter.getInstance();
		presenter.setActivity(this);
		startGameLobbyFragment();

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

	public void onGameUpdate(List<String> players) {}

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

	public void onGameStart() {
		System.out.print("game started");
		//TODO: Add code to start the game here
	}

	private void startGameLobbyFragment() {
		FragmentManager fm = this.getSupportFragmentManager();
		GameLobbyFragment lobbyFragment = (GameLobbyFragment) fm.findFragmentById(R.id.gameFragment);
		if (lobbyFragment == null) {
			lobbyFragment = GameLobbyFragment.newInstance();
			fm.beginTransaction()
					.add(R.id.gameFragment, lobbyFragment)
					.commit();
		}
		presenter.setLobbyFragment(lobbyFragment);
	}
}
