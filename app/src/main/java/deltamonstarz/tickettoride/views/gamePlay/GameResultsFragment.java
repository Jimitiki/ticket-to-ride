package deltamonstarz.tickettoride.views.gamePlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.presenters.GameSelectorPresenter;
import deltamonstarz.tickettoride.views.GameSelectorActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameResultsFragment extends Fragment {
	private TextView playerText;
	private Button startGameButton;
	private static GamePresenter presenter;

	public GameResultsFragment() {
		// Required empty public constructor
	}

	public static GameResultsFragment newInstance() {
		GameResultsFragment fragment = new GameResultsFragment();
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
		View v = inflater.inflate(R.layout.fragment_game_results, container, false);
		playerText = (TextView) v.findViewById(R.id.playersText);
		startGameButton = (Button) v.findViewById(R.id.startGame);
		startGameButton.setEnabled(false);
		return v;
	}

	private class ResultHolder extends RecyclerView.ViewHolder {
		private TextView player;
		private TextView final_score;
		private TextView routes;
		private TextView finished_destinations;
		private TextView unfinished_destinations;

		private View view;

		/**
		 * Initializes a result holder.
		 *
		 * @param v The view that belongs the recycler view.
		 */
		ResultHolder(View v) {
			super(v);
			view = v;

			player = (TextView) v.findViewById(R.id.player);
			final_score = (TextView) v.findViewById(R.id.final_score);
			routes = (TextView) v.findViewById(R.id.routes);
			finished_destinations = (TextView) v.findViewById(R.id.finished_destinations);
			unfinished_destinations = (TextView) v.findViewById(R.id.unfinished_destinations);
		}
	}

	private class GameResultsRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
	{
		private List<GameResult> resultList;

		public GameResultsRecyclerAdapter() {
			resultList = new ArrayList<>();
		}

		@Override
		public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			// create a new view
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_results_player_list_item, parent, false);
			// set the view's size, margins, padding and layout parameters
			GameResultsFragment.ResultHolder vh = new GameResultsFragment.ResultHolder(v);
			return vh;
		}

		@Override
		public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		}

		@Override
		public int getItemCount() {
			return 0;
		}
	}

}
