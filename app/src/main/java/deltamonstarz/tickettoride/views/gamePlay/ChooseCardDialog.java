package deltamonstarz.tickettoride.views.gamePlay;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.presenters.ChooseCardPresenter;
import deltamonstarz.tickettoride.presenters.DestinationCardPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseCardDialog extends DialogFragment {
	static final String TITLE = "Card Selection";
	static final String BASE_PATH_TRAIN = "card_images/train_card_";
	static final String BASE_PATH = "card_images/";

	private ChooseCardPresenter presenter;

	Button cancel;
	Button accept;

	ImageView card0Image;
	ImageView card1Image;
	ImageView card2Image;
	ImageView card3Image;
	ImageView card4Image;
	ImageView deckCardImage;
	ImageView destinationCardsImage;

	CardColor card0Color;
	CardColor card1Color;
	CardColor card2Color;
	CardColor card3Color;
	CardColor card4Color;







	public ChooseCardDialog() {
		// Required empty public constructor
	}

	@TargetApi(21)
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(TITLE);

		builder.setView(buildView());

		return builder.create();
	}

	private View buildView(){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_choose_card_dialog, null);

		accept = (Button) view.findViewById(R.id.accept_button);
		cancel = (Button) view.findViewById(R.id.cancel_button);

		card0Image = (ImageView) view.findViewById(R.id.choose_card_0);
		card1Image = (ImageView) view.findViewById(R.id.choose_card_1);
		card2Image = (ImageView) view.findViewById(R.id.choose_card_2);
		card3Image = (ImageView) view.findViewById(R.id.choose_card_3);
		card4Image = (ImageView) view.findViewById(R.id.choose_card_4);
		deckCardImage = (ImageView) view.findViewById(R.id.choose_deck_card);
		destinationCardsImage = (ImageView) view.findViewById(R.id.choose_destination_card);


		accept.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				acceptClick();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cancelClick();
			}
		});

		card0Image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processTrainCardClick(0);
			}
		});

		card1Image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processTrainCardClick(1);
			}
		});

		card2Image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processTrainCardClick(2);
			}
		});

		card3Image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processTrainCardClick(3);
			}
		});

		card4Image.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processTrainCardClick(4);
			}
		});

		deckCardImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processDeckCardClick();
			}
		});

		destinationCardsImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				processDestinationCardClick();
			}
		});


		// Todo: Use the real card images based on what it is holding

		List<TrainCard> cards = ClientModel.getInstance().getGame().getFaceUpCards();
		setCards(cards);


		setImage(deckCardImage, BASE_PATH_TRAIN + "back.PNG");
		setImage(destinationCardsImage, BASE_PATH + "destination_card_back_multiple.PNG");

		return view;
	}

	public void setCards(List<TrainCard> cards){
		for (int i = 0; i < cards.size(); i++){
			setTrainCard(i, cards.get(i).getColor());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		presenter = ChooseCardPresenter.getInstance();
		presenter.setChooseCardDialog(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		presenter.setChooseCardDialog(null);
	}

	private void processTrainCardClick(int cardIndex){
		presenter.drawFaceUpCard(cardIndex);
	}

	private void processDeckCardClick(){

	}

	private void processDestinationCardClick(){
		// Todo: Swap out to the destination selection dialog
	}

	private void processCardClick(View view, boolean selected){
	}

	private void acceptClick(){
		dismiss(); // Close dialog
	}

	private void cancelClick(){
		dismiss(); // Close dialog
	}

	private void reportSelection(){
		// Todo: Send info back to presenter
	}

	private void setTrainCard(int card, CardColor color){
		switch (card){
			case 0:
				card0Color = color;
				setImage(card0Image, getFilePath(color));
				break;
			case 1:
				card1Color = color;
				setImage(card1Image, getFilePath(color));
				break;
			case 2:
				card2Color = color;
				setImage(card2Image, getFilePath(color));
				break;
			case 3:
				card3Color = color;
				setImage(card3Image, getFilePath(color));
				break;
			case 4:
				card4Color = color;
				setImage(card4Image, getFilePath(color));
				break;
			default:

		}
	}

	private String getFilePath(CardColor color){
		switch (color){
			case BLUE:
				return BASE_PATH_TRAIN + "blue.PNG";
			case GREEN:
				return BASE_PATH_TRAIN + "green.PNG";
			case RED:
				return BASE_PATH_TRAIN + "red.PNG";
			case YELLOW:
				return BASE_PATH_TRAIN + "yellow.PNG";
			case BLACK:
				return BASE_PATH_TRAIN + "black.PNG";
			case ORANGE:
				return BASE_PATH_TRAIN + "orange.PNG";
			case WHITE:
				return BASE_PATH_TRAIN + "white.PNG";
			case GOLD:
				return BASE_PATH_TRAIN + "gold.PNG";
			case PINK:
				return BASE_PATH_TRAIN + "pink.PNG";
			case UNKNOWN:
			default:
				return BASE_PATH_TRAIN + "back.PNG";
		}
	}

	private void setImage(ImageView imageView, String filePath){
		try {
			InputStream is = getActivity().getAssets().open(filePath);
			Bitmap bm = BitmapFactory.decodeStream(is);
			imageView.setImageBitmap(bm);
			imageView.setRotation(90);
		}
		catch (IOException e){

		}
	}

}
