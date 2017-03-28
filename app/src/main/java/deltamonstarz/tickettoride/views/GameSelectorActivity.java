package deltamonstarz.tickettoride.views;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GameSelectorPresenter;
import deltamonstarz.tickettoride.views.gamePlay.GameActivity;

/**
 * Created and javadoc'd by Brad
 * Operates the User Interface for the Game Selection Screen.
 */
public class GameSelectorActivity extends AppCompatActivity implements GameNameChoiceDialogFragment.OnCompleteListener
{
	//Widgets
	private Button mCreateGameButton;
	private Button mLogoutButton;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManager;
	private GameSelectionRecyclerAdapter mAdapter;

	//Data Members
	private GameSelectorPresenter mPresenter;

	/**
	 * Creates an intent that can be used to start an instance of this activity.
	 *
	 * @pre none
	 * @post none
	 * @invariant none
	 * @param packageContext The Context object used to create the
	 * @return The intent
	 */
	public static Intent newIntent(Context packageContext)
	{
		return new Intent(packageContext, GameSelectorActivity.class);
	}

	/**
	 * Initializes the activity.
	 *
	 * @pre none
	 * @post Sets the layout to activity_game_selector
	 * @post The activity will be associated with the GameSelectorPresenter
	 * @post The recycler view will display an empty list
	 * @invariant none
	 * @param savedInstanceState A saved state used to recreate the activity
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_selector);

		mPresenter = GameSelectorPresenter.getInstance();
		mPresenter.setActivity(this);

		mCreateGameButton = (Button) findViewById(R.id.createGameButton);
		mCreateGameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				makeGameClick();
			}
		});

		mLogoutButton = (Button) findViewById(R.id.logoutButton);
		mLogoutButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				logout();
			}
		});

		mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

		// use this setting to improve performance if you know that changes
		// in content do not change the layout size of the RecyclerView
		mRecyclerView.setHasFixedSize(true);

		// use a linear layout manager
		mLayoutManager = new LinearLayoutManager(this);
		mRecyclerView.setLayoutManager(mLayoutManager);
	}

	/**
	 * Resumes the activity
	 *
	 * @pre The activity was paused
	 * @post The activity will be active
	 * @invariant none
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mPresenter.onResume();
	}

	/**
	 * Pauses the activity
	 *
	 * @pre The activity was active
	 * @post The activity will be paused
	 * @invariant none
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mPresenter.onPause();
	}

	/**
	 * Sets the content of the recycler view to be infos.
	 *
	 * @pre The activity was initialized
	 * @post The recycler view will be populated with infos
	 * @invariant none
  	 * @param infos The list of game infos objects used to populate the recycler view.
	 */
	public void onGameListUpdate(List<GameInfo> infos) {

		//Set Adapter if null
		if(mRecyclerView.getAdapter() == null) {
			mAdapter = new GameSelectionRecyclerAdapter();
			mRecyclerView.setAdapter(mAdapter);
		}

		//Update list and notify
		mAdapter.getGameList().clear();
		mAdapter.getGameList().addAll(infos);
		mAdapter.notifyDataSetChanged();
	}

	/**
	 * Logs the user out.
	 *
	 * @pre The activity was initialized
	 * @post The user will be taken back to the login screen
	 * @invariant none
	 */
	public void logout() {
		Intent intent = new Intent(getBaseContext(), LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

	/**
	 * Starts a game activity
	 *
	 * @pre The activity was initialized
	 * @post A Game Activity will take over for this one.
	 * @invariant none
	 */
	public void onJoinGame() {
		Intent i = GameActivity.newIntent(GameSelectorActivity.this);
		startActivity(i);
	}

	/**
	 * Displays a game creation dialogue.
	 *
	 * @pre The activity was initialized
	 * @post A game creation dialogue will be displayed
	 * @invariant none
	 */
	private void makeGameClick(){
		FragmentManager manager = getSupportFragmentManager();
		GameNameChoiceDialogFragment dialog = new GameNameChoiceDialogFragment();
		dialog.show(manager, "choose_game_name__dialog");
	}

	/**
	 * Notifies the user of a connection error.
	 *
	 * @pre A connection error happened.
	 * @post A Toast will be displayed.
	 * @invariant none
	 */
	public void onConnectionError() {
		Toast toast = Toast.makeText(this, "Network Error: Could not connect to server", Toast.LENGTH_LONG);
		toast.show();
	}

	@Override
	public void onComplete(String name) {
		mPresenter.createGame(name);
	}

	/**
	 * Provides an interface between the game information objects and the views in a recycler view.
	 */
	private class GameHolder extends RecyclerView.ViewHolder {
		private TextView gameName;
		private TextView gameOwner;
		private TextView gameStarted;
		private TextView playerCount;

		private GameInfo gameInfo;
		private View view;

		/**
		 * Initializes a game holder.
		 * A click listener will be set on the view to start a game.
		 *
		 * @param v The view that belongs the recycler view.
		 */
		GameHolder(View v) {
			super(v);
			view = v;

			gameName = (TextView) v.findViewById(R.id.nameText);
			gameOwner = (TextView) v.findViewById(R.id.ownerText);
			gameStarted = (TextView) v.findViewById(R.id.gameStarted);
			playerCount = (TextView) v.findViewById(R.id.playerCountText);

			v.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					GameSelectorPresenter.getInstance().joinGame(gameInfo.getGameID());
				}
			});
		}
	}

	/**
	 * Defines how the dataset is bound to the recycler adapter.
	 */
	private class GameSelectionRecyclerAdapter extends RecyclerView.Adapter<GameHolder>
	{
		private List<GameInfo> mGameList;

		/**
		 * Initializes the recycler view adapter.
		 *
		 * @pre none
		 * @post The recycler view will be empty.
		 * @invariant none
		 */
		public GameSelectionRecyclerAdapter() {
			mGameList = new ArrayList<>();
		}

		public List<GameInfo> getGameList() {
			return mGameList;
		}

		/**
		 * Creates new views. (Invoked by the layout manager.)
		 *
		 * @pre none
		 * @post A view will be added to the recycler.
		 * @invariant none
		 */
		@Override
		public GameHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// create a new view
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_selector_recycler_view, parent, false);
			// set the view's size, margins, padding and layout parameters
			GameHolder vh = new GameHolder(v);
			return vh;
		}

		/**
		 * Replaces the contents of a view in the recycler. (Invoked by the layout manager.)
		 *
		 * @pre position is in the range of mGameList
		 * @post holder will be bound to the data of the element at position.
		 * @invariant none
		 * @param holder The view holder being bound.
		 * @param position The position of the data being bound.
		 */
		@Override
		public void onBindViewHolder(final GameHolder holder, int position) {
			holder.gameInfo = mGameList.get(position);

			holder.gameName.setText(holder.gameInfo.getName());
			holder.gameOwner.setText(holder.gameInfo.getOwnerName());
			holder.playerCount.setText(String.valueOf(holder.gameInfo.getPlayerCount()));

			// Has the game started?
			if (holder.gameInfo.isGameStarted()) {
				holder.gameStarted.setText("Yes (Rejoin)");
				holder.view.setBackgroundColor(getResources().getColor(R.color.greenButton));
			}
			// Is the player in a game that has not yet stared?
			else if (holder.gameInfo.getPlayers().contains(ClientModel.getInstance().getUsername())){
				holder.gameStarted.setText("No (Rejoin)");
			}
			else{
				holder.gameStarted.setText("No");
			}
		}

		/**
		 * Returns the size of the dataset. (Invoked by the layout manager.)
		 *
		 * @pre none
		 * @post none
		 * @invariant return value will be >= 0
		 * @return the size of the dataset.
		 */
		@Override
		public int getItemCount() {
			return mGameList.size();
		}
	}

}
