package deltamonstarz.tickettoride.views.gamePlay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import delta.monstarz.shared.model.PlayerColor;
import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerInfoFragment extends Fragment {

	static final String NAME = "Name: ";
	static final String POINTS = "Points: ";
	static final String DEST_CARD = "Destination Cards: ";
	static final String TRAIN_CARD = "Train Cards: ";
	static final String TRAIN_PIECES = "Train Pieces: ";

	PlayerColor color;

	private TextView textName;
	private TextView textPoints;
	private TextView textDestCardCount;
	private TextView textTrainCardCount;
	private TextView textTrainPiecesCount;

	public PlayerInfoFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_player_info, container, false);

		textName = (TextView) view.findViewById(R.id.player_info_name);
		textPoints = (TextView) view.findViewById(R.id.player_info_points);
		textDestCardCount = (TextView) view.findViewById(R.id.player_info_dest_cards);
		textTrainCardCount = (TextView) view.findViewById(R.id.player_info_train_cards);
		textTrainPiecesCount = (TextView) view.findViewById(R.id.player_info_train_pieces);

		if (color == PlayerColor.BLUE){
			view.setBackgroundColor(getResources().getColor(R.color.player_blue));
		}
		else if (color == PlayerColor.GREEN){
			view.setBackgroundColor(getResources().getColor(R.color.player_green));
		}
		else if (color == PlayerColor.RED){
			view.setBackgroundColor(getResources().getColor(R.color.player_red));
		}
		else if (color == PlayerColor.YELLOW){
			view.setBackgroundColor(getResources().getColor(R.color.player_yellow));
		}
		else if (color == PlayerColor.BLACK){
			view.setBackgroundColor(getResources().getColor(R.color.player_black));
		}

		setName("A name");
		setTextPoints("42");
		setTextDestCardCount("3");
		setTextTrainCardCount("14");
		setTextTrainPiecesCount("40");

		return view;
	}

	public void setColor(PlayerColor color){
		this.color = color;
	}

	public void update(){
		View view = getView();
	}

	private void setName(String name){
		textName.setText(NAME + name);
	}

	private void setTextPoints(String points){
		textPoints.setText(POINTS + points);
	}

	private void setTextDestCardCount(String count){
		textDestCardCount.setText(DEST_CARD + count);
	}

	private void setTextTrainCardCount(String count){
		textTrainCardCount.setText(TRAIN_CARD + count);
	}

	private void setTextTrainPiecesCount(String count){
		textTrainPiecesCount.setText(TRAIN_PIECES + count);
	}

}
