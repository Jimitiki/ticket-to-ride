package deltamonstarz.tickettoride.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.EditText;

import deltamonstarz.tickettoride.R;

/**
 * Created by Trevor on 2/15/2017.
 */

public class GameNameChoiceDialogFragment extends DialogFragment {
	public static interface OnCompleteListener {
		public abstract void onComplete(String name);
	}

	private static String TITLE = "Choose Game Name";

	private OnCompleteListener mListener;

	@TargetApi(21)
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(TITLE);


		builder.setView(R.layout.game_name_choice_dialog);


		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User clicked OK button
				Dialog dialog2 = Dialog.class.cast(dialog);
				EditText input = (EditText) dialog2.findViewById(R.id.game_name_input);
				String name = input.getText().toString();
				mListener.onComplete(name);
			}
		});
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				// User cancelled the dialog
			}
		});

		return builder.create();

	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		try {
			this.mListener = (OnCompleteListener)context;
		}
		catch (final ClassCastException e) {
			throw new ClassCastException(context.toString() + " must implement OnCompleteListener");
		}
	}
}
