package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GameSelectorPresenter;


public class GameSelectorActivity extends AppCompatActivity
{
	//Widgets
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private RecyclerView.Adapter mAdapter;

	//Data Members
	private GameSelectorPresenter mPresenter;


	public static Intent newIntent(Context packageContext)
	{
		return new Intent(packageContext, GameSelectorActivity.class);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_selector);

		mPresenter = GameSelectorPresenter.getInstance();
		mPresenter.setActivity(this);
		mPresenter.observe();

		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		mPresenter.pollGameList();

	}

	public void onGameListUpdate(List<GameInfo> infos) {
		mAdapter = new GameSelectionRecyclerAdapter(infos);
		mRecyclerView.setAdapter(mAdapter);
	}

	public void onLogout() {
		mPresenter.endObserve();
		Intent i = LoginActivity.newIntent(GameSelectorActivity.this);
		startActivity(i);
	}

	public void onJoinGame() {
		mPresenter.endObserve();
		Intent i = GameActivity.newIntent(GameSelectorActivity.this);
		startActivity(i);
	}
}
