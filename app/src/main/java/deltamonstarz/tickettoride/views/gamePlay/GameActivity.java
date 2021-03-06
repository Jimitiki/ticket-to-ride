package deltamonstarz.tickettoride.views.gamePlay;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Toast;

import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.views.LoginActivity;


public class GameActivity extends AppCompatActivity
{
	private GamePresenter presenter;
	private GameLobbyFragment lobbyFragment;
	private GameFragment gameFragment;
	private GameResultsFragment resultsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);

		presenter = GamePresenter.getInstance();
		presenter.setActivity(this);
		onJoinGame();

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

	public void logout() {
		Intent intent = new Intent(getBaseContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
		finish();
	}

	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void onGameStart(String mapPath) {
		System.out.print("game started");
		FragmentManager fm = this.getSupportFragmentManager();
		gameFragment = GameFragment.newInstance();
		gameFragment.setMapImagePath(mapPath);
		fm.beginTransaction()
				.replace(R.id.fragmentContainer, gameFragment)
				.commit();
		presenter.setGameFragment(gameFragment);
		gameFragment.setActivity(this);
		gameFragment.setPresenter(presenter);
	}

	public void onGameEnd() {
		System.out.print("game ended");
		FragmentManager fm = this.getSupportFragmentManager();
		resultsFragment = GameResultsFragment.newInstance();
		fm.beginTransaction()
				.replace(R.id.fragmentContainer, resultsFragment)
				.commit();
	}

	private void onJoinGame() {
		FragmentManager fm = this.getSupportFragmentManager();
		lobbyFragment = (GameLobbyFragment) fm.findFragmentById(R.id.fragmentContainer);
		if (lobbyFragment == null) {
			lobbyFragment = GameLobbyFragment.newInstance();
			fm.beginTransaction()
					.add(R.id.fragmentContainer, lobbyFragment)
					.commit();
		}
		presenter.setLobbyFragment(lobbyFragment);
	}
}
