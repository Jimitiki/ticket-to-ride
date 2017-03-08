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
import java.util.HashMap;
import java.util.Map;

import delta.monstarz.shared.model.CardColor;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.model.ClientModel;

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

		setCardImages();

		update();

		return view;
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
			e.printStackTrace();
		}
	}

	public void update(){
		HashMap<CardColor, Integer> cards = ClientModel.getInstance().getGame().getMe().getTrainCards();

		for (Map.Entry<CardColor, Integer> entry: cards.entrySet()){
			setValue(entry.getKey(), entry.getValue());
		}

	}

	private void setValue(CardColor color, int count){
		String num = String.valueOf(count);
		switch(color){
			case RED:
				redCount.setText(num);
				break;
			case WHITE:
			whiteCount.setText(num);
				break;
			case ORANGE:
				orangeCount.setText(num);
				break;
			case GREEN:
				greenCount.setText(num);
				break;
			case BLUE:
				blueCount.setText(num);
				break;
			case BLACK:
				blackCount.setText(num);
				break;
			case YELLOW:
				yellowCount.setText(num);
				break;
			case PINK:
				pinkCount.setText(num);
				break;
			case GOLD:
				goldCount.setText(num);
				break;
		}
	}

}
