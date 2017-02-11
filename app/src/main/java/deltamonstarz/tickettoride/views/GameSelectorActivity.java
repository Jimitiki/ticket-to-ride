package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import deltamonstarz.tickettoride.R;


public class GameSelectorActivity extends AppCompatActivity
{

	public static Intent newIntent(Context packageContext)
	{
		Intent i = new Intent(packageContext, GameSelectorActivity.class);
		return i;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_selector);
	}

	public void onGameListUpdate() {}
}
