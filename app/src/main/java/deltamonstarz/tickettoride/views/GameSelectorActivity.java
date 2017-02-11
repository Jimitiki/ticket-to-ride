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

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.R;


public class GameSelectorActivity extends AppCompatActivity
{
	//Widgets
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private RecyclerView.Adapter mAdapter;


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

		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);

		// specify an adapter (see also next example)
		GameInfo[] array = new GameInfo[]
		{
			new GameInfo("Name", "Owner", 0, new Date(), 2, false),
			new GameInfo("Team Cap", "Steve", 1, new Date(), 3, false),
			new GameInfo("Bikini Bottom", "Owner", 2, new Date(), 4, true),
			new GameInfo("Team Iron", "Owner", 3, new Date(), 4, false),
			new GameInfo("Ooo", "Owner", 4, new Date(), 5, true),
		};
		ArrayList<GameInfo> myDataset = new ArrayList<>(Arrays.asList(array));;
		mAdapter = new GameSelectionRecyclerAdapter(myDataset);
		mRecyclerView.setAdapter(mAdapter);
	}
}
