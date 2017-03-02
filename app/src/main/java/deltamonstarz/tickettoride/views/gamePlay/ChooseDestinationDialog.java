package deltamonstarz.tickettoride.views.gamePlay;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDestinationDialog extends DialogFragment {
	static final String TITLE = "Choose A Destination";

	Button cancel;
	Button accept;
	Button card1;
	Button card2;
	Button card3;

	Boolean card1Selected = false;
	Boolean card2Selected = false;
	Boolean card3Selected = false;

	ArrayList<DestCard> destCards = new ArrayList<>();

	int cardCount = 0;
	int minRequired = 0;


	public ChooseDestinationDialog() {
		// Required empty public constructor
	}

	@TargetApi(21)
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(TITLE);


		builder.setView(buildView());

		/*
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
			}
		});

		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});
		*/


		return builder.create();

	}

	private View buildView(){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_choose_destination_dialog, null);

		accept = (Button) view.findViewById(R.id.accept_button);
		cancel = (Button) view.findViewById(R.id.cancel_button);

		card1 = (Button) view.findViewById(R.id.destination_card_selection_01);
		card2 = (Button) view.findViewById(R.id.destination_card_selection_02);
		card3 = (Button) view.findViewById(R.id.destination_card_selection_03);

		card1.setBackgroundColor(getResources().getColor(R.color.grayButton));
		card2.setBackgroundColor(getResources().getColor(R.color.grayButton));
		card3.setBackgroundColor(getResources().getColor(R.color.grayButton));

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


		// ToDo: Use the destination cards to set the correct text
		if (cardCount > 0) {

			//card1.setText(destCards.get(0).toString());
			card1.setText("(#1) Dest1 - Dest 1");

			card1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					card1Selected = !card1Selected;
					processCardClick(v, card1Selected);
				}
			});
		}

		if (cardCount > 1) {

			//card2.setText(destCards.get(1).toString());
			card2.setText("(#2) Dest2 - Dest 2");

			card2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					card2Selected = !card2Selected;
					processCardClick(v, card2Selected);
				}
			});
		}

		if (cardCount > 2) {

			//card3.setText(destCards.get(2).toString());
			card3.setText("(#3) Dest3 - Dest 3");

			card3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					card3Selected = !card3Selected;
					processCardClick(v, card3Selected);
				}
			});
		}

		return view;
	}

	public void setCounts(int cardCount, int minRequired){
		this.cardCount = cardCount;
		this.minRequired = minRequired;
	}

	public void setDestCards(ArrayList<DestCard> destCards){
		this.destCards = destCards;
	}


	private void processCardClick(View view, boolean selected){
		//selected = !selected; // Flip

		if (selected){
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
		}
		else{
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
		}
	}

	private void acceptClick(){
		// Not enough selected
		if (getSelectedCount() < minRequired){
			String text = "Choose at least " + String.valueOf(minRequired);
			if (minRequired > 1) {
				text += " cards.";
			}
			else{
				text += " card";
			}
			Toast.makeText(getActivity(), text, Toast.LENGTH_LONG).show();
		}
		else{

			reportSelection();
			dismiss(); // Close dialog
		}
	}

	private void cancelClick(){
		dismiss(); // Close dialog
	}

	private void reportSelection(){
		// Todo: Send info back to presenter
	}

	private int getSelectedCount(){
		int count = 0;
		if (card1Selected){
			count++;
		}
		if (card2Selected){
			count++;
		}
		if (card3Selected){
			count++;
		}
		return count;
	}


}
