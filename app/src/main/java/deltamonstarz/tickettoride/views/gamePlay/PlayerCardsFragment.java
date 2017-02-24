package deltamonstarz.tickettoride.views.gamePlay;


import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import java.io.InputStream;

import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerCardsFragment extends Fragment {

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

		redImg = (ImageView) view.findViewById(R.id.card_image_01_red);
		whiteImg = (ImageView) view.findViewById(R.id.card_image_02_white);
		orangeImg = (ImageView) view.findViewById(R.id.card_image_03_orange);
		greenImg = (ImageView) view.findViewById(R.id.card_image_04_green);
		blueImg = (ImageView) view.findViewById(R.id.card_image_05_blue);
		blackImg = (ImageView) view.findViewById(R.id.card_image_06_black);
		yellowImg = (ImageView) view.findViewById(R.id.card_image_07_yellow);
		pinkImg = (ImageView) view.findViewById(R.id.card_image_08_pink);
		goldImg = (ImageView) view.findViewById(R.id.card_image_09_gold);

		int id;

		try {
			InputStream is = getActivity().getAssets().open("train_cards/train_card_red.PNG");
			Bitmap bm = BitmapFactory.decodeStream(is);
			redImg.setImageBitmap(bm);
		}
		catch (Exception e){

		}



		/*
		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);

		id = getResources().getIdentifier("train_card_red", "drawable", "/asset/train_cards" );
		redImg.setImageResource(id);
		*/

		return view;
	}

}
