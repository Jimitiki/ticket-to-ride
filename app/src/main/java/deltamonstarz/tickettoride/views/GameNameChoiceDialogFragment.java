package deltamonstarz.tickettoride.views;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
	private EditText gameNameInput;

	@TargetApi(21)
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.routeSelectionTitle);

		builder.setView(buildView());

		return builder.create();
	}

	public View buildView() {
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.game_name_choice_dialog, null);

		gameNameInput = (EditText) view.findViewById(R.id.game_name_input);

		(view.findViewById(R.id.submit_name)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String name = gameNameInput.getText().toString();
				if (!name.equals("")) {
					mListener.onComplete(name);
					dismiss();
				}
			}
		});

		(view.findViewById(R.id.cancel_dialog)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		return view;

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
