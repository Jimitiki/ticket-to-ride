package deltamonstarz.tickettoride.views.gamePlay;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;

import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerCardsFragment extends Fragment {

	static final String BASE_PATH = "card_images/train_card_";

	TextView redCount;
	TextView whiteCount;
	TextView orangeCount;
	TextView greenCount;
	TextView blueCount;
	TextView blackCount;
	TextView yellowCount;
	TextView pinkCount;
	TextView goldCount;

	ImageView redImg;
	ImageView whiteImg;
	ImageView orangeImg;
	ImageView greenImg;
	ImageView blueImg;
	ImageView blackImg;
	ImageView yellowImg;
	ImageView pinkImg;
	ImageView goldImg;

	public PlayerCardsFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_player_cards, container, false);

		redCount = (TextView) view.findViewById(R.id.card_count_01_red);
		whiteCount = (TextView) view.findViewById(R.id.card_count_02_white);
		orangeCount = (TextView) view.findViewById(R.id.card_count_03_orange);
		greenCount = (TextView) view.findViewById(R.id.card_count_04_green);
		blueCount = (TextView) view.findViewById(R.id.card_count_05_blue);
		blackCount = (TextView) view.findViewById(R.id.card_count_06_black);
		yellowCount = (TextView) view.findViewById(R.id.card_count_07_yellow);
		pinkCount = (TextView) view.findViewById(R.id.card_count_08_pink);
		goldCount = (TextView) view.findViewById(R.id.card_count_09_gold);


		redImg = (ImageView) view.findViewById(R.id.card_image_01_red);
		whiteImg = (ImageView) view.findViewById(R.id.card_image_02_white);
		orangeImg = (ImageView) view.findViewById(R.id.card_image_03_orange);
		greenImg = (ImageView) view.findViewById(R.id.card_image_04_green);
		blueImg = (ImageView) view.findViewById(R.id.card_image_05_blue);
		blackImg = (ImageView) view.findViewById(R.id.card_image_06_black);
		yellowImg = (ImageView) view.findViewById(R.id.card_image_07_yellow);
		pinkImg = (ImageView) view.findViewById(R.id.card_image_08_pink);
		goldImg = (ImageView) view.findViewById(R.id.card_image_09_gold);

		setCardCounts();
		setCardImages();


		return view;
	}

	void setCardCounts(){
		// Todo: Access the model to set the values

		redCount.setText("10");
		whiteCount.setText("20");
		orangeCount.setText("30");
		greenCount.setText("40");
		blueCount.setText("50");
		blackCount.setText("60");
		yellowCount.setText("70");
		pinkCount.setText("80");
		goldCount.setText("90");
	}

	void setCardImages(){
		setImage(redImg, BASE_PATH + "red.PNG");
		setImage(whiteImg, BASE_PATH + "white.PNG");
		setImage(orangeImg, BASE_PATH + "orange.PNG");
		setImage(greenImg, BASE_PATH + "green.PNG");
		setImage(blueImg, BASE_PATH + "blue.PNG");
		setImage(blackImg, BASE_PATH + "black.PNG");
		setImage(yellowImg, BASE_PATH + "yellow.PNG");
		setImage(pinkImg, BASE_PATH + "pink.PNG");
		setImage(goldImg, BASE_PATH + "gold.PNG");
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

}
