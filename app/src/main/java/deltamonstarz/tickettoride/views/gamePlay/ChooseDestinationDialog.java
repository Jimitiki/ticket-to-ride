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
import deltamonstarz.tickettoride.presenters.DestinationCardPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDestinationDialog extends DialogFragment {
	static final String TITLE = "Choose A Destination";

	DestinationCardPresenter presenter;

	Button cancel;
	Button accept;
	Button card1;
	Button card2;
	Button card3;

	Boolean card1Selected = false;
	Boolean card2Selected = false;
	Boolean card3Selected = false;

	ArrayList<DestCard> destCards;

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


		card1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card1Selected = !card1Selected;
				processCardClick(v, card1Selected, 1);
			}
		});

		card2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card2Selected = !card2Selected;
				processCardClick(v, card2Selected, 2);
			}
		});

		card3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card3Selected = !card3Selected;
				processCardClick(v, card3Selected, 3);
			}
		});

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		presenter = DestinationCardPresenter.getInstance();
		presenter.setChooseDestinationDialog(this);
		presenter.drawCards();
	}

	public void setDestCards(ArrayList<DestCard> destCards, int minRequired){
		this.cardCount = destCards.size();
		this.minRequired = minRequired;
		this.destCards = destCards;

		if (this.destCards.size() > 0) {
			card1.setText(destCards.get(0).toString());
			//card1.setText("(#1) Dest1 - Dest 1");
		}

		if (this.destCards.size() > 1) {
			card2.setText(destCards.get(1).toString());
			//card2.setText("(#2) Dest2 - Dest 2");
		}

		if (this.destCards.size() > 2) {
			card3.setText(destCards.get(2).toString());
			//card3.setText("(#3) Dest3 - Dest 3");
		}
	}


	private void processCardClick(View view, boolean selected, int card){

		// We only process a card click if the cards have been set
		// and we the specified card has a corresponding destCard
		if (destCards == null || destCards.size() < card) {
			return;
		}


		if (selected) {
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
		} else {
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
		}
	}

	private void acceptClick(){

		if (destCards == null){
			Toast.makeText(getActivity(), "Waiting for cards from server", Toast.LENGTH_LONG).show();
			return;
		}


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

		ArrayList<DestCard> keepCards = new ArrayList<>();
		ArrayList<DestCard> returnCards = new ArrayList<>();

		if (destCards.size() > 0) {
			if (card1Selected) {
				keepCards.add(destCards.get(0));
			} else {
				returnCards.add(destCards.get(0));
			}
		}

		if (destCards.size() > 1) {
			if (card2Selected) {
				keepCards.add(destCards.get(1));
			} else {
				returnCards.add(destCards.get(1));
			}
		}

		if (destCards.size() > 2) {
			if (card3Selected) {
				keepCards.add(destCards.get(2));
			} else {
				returnCards.add(destCards.get(2));
			}
		}

		presenter.reportSelection(keepCards, returnCards);

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
