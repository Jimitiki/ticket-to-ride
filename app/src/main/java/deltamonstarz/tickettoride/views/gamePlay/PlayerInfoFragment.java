package deltamonstarz.tickettoride.views.gamePlay;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import delta.monstarz.shared.Image;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.PlayerInfo;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerInfoFragment extends Fragment {

	static final String BASE_PATH = "player_info_images/";

	static final String NAME = "Name: ";
	static final String POINTS = "Points: ";
	static final String DEST_CARD = "Destination Cards: ";
	static final String TRAIN_CARD = "Train Cards: ";
	static final String TRAIN_PIECES = "Train Pieces: ";

	PlayerColor color;
	GameInfoFragment gameInfoFragment;

	private ImageView turnImage;
	private ImageView longestTrainImage;

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

		turnImage = (ImageView) view.findViewById(R.id.player_turn_image);
		longestTrainImage = (ImageView) view.findViewById(R.id.longest_train_ownership_image);

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

		return view;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		gameInfoFragment.update();
	}

	public void setColor(PlayerColor color){
		this.color = color;
	}

	public void setGameInfoFragment(GameInfoFragment gameInfoFragment) {
		this.gameInfoFragment = gameInfoFragment;
	}

	public void update(PlayerInfo playerInfo){

		if (!allViewsSet()){
			return;
		}

		String points = String.valueOf(playerInfo.getScore());
		String destCardCount = String.valueOf(playerInfo.getNumDestCards());
		String trainCardCount = String.valueOf(playerInfo.getNumTrainsCards());
		String trainPiecesCount = String.valueOf(playerInfo.getNumTrains());

		setName(playerInfo.getUsername());
		setTextPoints(points);
		setTextDestCardCount(destCardCount);
		setTextTrainCardCount(trainCardCount);
		setTextTrainPiecesCount(trainPiecesCount);
		setTurnActiveImage(playerInfo.isPlayersTurn());
		setLongestTrainImage(playerInfo.isHasLongestRoute());
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

	private void setTurnActiveImage(boolean turnActive){
		if (turnActive){
			setImage(turnImage, BASE_PATH + "is_players_turn.PNG");
		}
		else{
			turnImage.setImageResource(android.R.color.transparent);
		}
	}

	private void setLongestTrainImage(boolean longestTrain){
		if (longestTrain){
			setImage(longestTrainImage, BASE_PATH + "longest_train.PNG");
		}
		else{
			longestTrainImage.setImageResource(android.R.color.transparent);
		}
	}

	void setImage(ImageView imageView, String filePath){
		try {
			InputStream is = getActivity().getAssets().open(filePath);
			Bitmap bm = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bm);
		}
		catch (IOException e){

		}
	}

	private boolean allViewsSet(){
		if (
			turnImage == null ||
			longestTrainImage == null ||
			textName == null ||
			textPoints == null ||
			textDestCardCount == null ||
			textTrainCardCount == null ||
			textTrainPiecesCount == null
				){
			return false;
		}
		else{
			return true;
		}
	}

}
