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

import org.w3c.dom.Text;

import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseDestinationDialog extends DialogFragment {
	static final String TITLE = "Choose A Destination";

	Button card1;
	Button card2;
	Button card3;

	Boolean card1Selected = false;
	Boolean card2Selected = false;
	Boolean card3Selected = false;


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


		return builder.create();

	}

	private View buildView(){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_choose_destination_dialog, null);

		card1 = (Button) view.findViewById(R.id.destination_card_selection_01);
		card2 = (Button) view.findViewById(R.id.destination_card_selection_02);
		card3 = (Button) view.findViewById(R.id.destination_card_selection_03);

		card1.setBackgroundColor(getResources().getColor(R.color.grayButton));
		card2.setBackgroundColor(getResources().getColor(R.color.grayButton));
		card3.setBackgroundColor(getResources().getColor(R.color.grayButton));

		card1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card1Selected = !card1Selected;
				processCardClick(v, card1Selected);
			}
		});

		card2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card2Selected = !card2Selected;
				processCardClick(v, card2Selected);
			}
		});

		card3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				card3Selected = !card3Selected;
				processCardClick(v, card3Selected);
			}
		});

		return view;
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


}
