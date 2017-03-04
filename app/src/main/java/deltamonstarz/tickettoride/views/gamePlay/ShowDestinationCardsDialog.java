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
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.views.GameSelectorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowDestinationCardsDialog extends DialogFragment {
	static final String TITLE = "Your Destination Cards";

	Button close;
	private RecyclerView mRecyclerView;
	private LinearLayoutManager mLayoutManger;
	private ShowDestination myAdapter;
	private List<DestCard> list;

	public ShowDestinationCardsDialog() {
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
		View view = inflater.inflate(R.layout.fragment_show_destination_cards_dialog, null);

		close = (Button) view.findViewById(R.id.close_button);

		close.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});


		mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

		mRecyclerView.setHasFixedSize(true);

		mLayoutManger = new LinearLayoutManager(getContext());
		mRecyclerView.setLayoutManager(mLayoutManger);





		myAdapter = new ShowDestination(list);
		mRecyclerView.setAdapter(myAdapter);

		return view;
	}

	public void setDestCardList(List<DestCard> list){
		this.list = list;
	}

	private class DestItemHolder extends RecyclerView.ViewHolder {

		private Button text;

		public DestItemHolder(View itemView) {
			super(itemView);

			text = (Button) itemView.findViewById(R.id.destination_list_item);
			text.setBackgroundColor(getResources().getColor(R.color.grayButton));

		}
	}


	private class ShowDestination extends RecyclerView.Adapter<DestItemHolder>{
		public List<DestCard> mList;

		public ShowDestination(List<DestCard> list){
			mList = list;
		}

		public List<DestCard> getDestList(){
			return mList;
		}

		@Override
		public DestItemHolder onCreateViewHolder(ViewGroup parent, int viewType){
			// create a new view
			View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_list_item, parent, false);
			DestItemHolder vh = new DestItemHolder(v);
			return vh;
		}

		@Override
		public void onBindViewHolder(final DestItemHolder holder, int position){
			holder.text.setText(mList.get(position).toString());
		}

		@Override
		public int getItemCount(){
			return mList.size();
		}
	}
}


