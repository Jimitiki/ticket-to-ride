package deltamonstarz.tickettoride.views;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import delta.monstarz.shared.GameInfo;
import deltamonstarz.tickettoride.R;


/**
 * @author bradcarter
 */
public class GameSelectionRecyclerAdapter extends RecyclerView.Adapter<GameSelectionRecyclerAdapter.ViewHolder>
{
	private List<GameInfo> mGameList;

	public class ViewHolder extends RecyclerView.ViewHolder {
		public TextView gameName;
		public TextView gameOwner;
		public TextView gameId;
		public TextView gameStartTime;
		public TextView playerCount;

		public ViewHolder(View v) {
			super(v);
			gameName = (TextView) v.findViewById(R.id.nameText);
			gameOwner = (TextView) v.findViewById(R.id.ownerText);
			gameId = (TextView) v.findViewById(R.id.gameIdText);
			gameStartTime = (TextView) v.findViewById(R.id.startTimeText);
			playerCount = (TextView) v.findViewById(R.id.playerCountText);
		}
	}

	public GameSelectionRecyclerAdapter(List<GameInfo> myGameList) {
		mGameList = myGameList;
	}

	// Create new views (invoked by the layout manager)
	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		// create a new view
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_selector_recycler_view, parent, false);
		v.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(this.getClass().getName(), "Game info view touched.");
			}
		});
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(final ViewHolder holder, int position) {

		GameInfo info = mGameList.get(position);
		holder.gameName.setText(info.getName());
		holder.gameId.setText(String.valueOf(info.getGameID()));
		holder.gameOwner.setText(info.getOwnerName());
		holder.gameStartTime.setText(info.getStartTime().toString());
		holder.playerCount.setText(String.valueOf(info.getPlayerCount()));
	}

	// Return the size of your dataset (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return mGameList.size();
	}
}
