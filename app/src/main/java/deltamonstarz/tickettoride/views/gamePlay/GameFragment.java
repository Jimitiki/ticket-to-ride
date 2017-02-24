package deltamonstarz.tickettoride.views.gamePlay;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.GamePresenter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
	private static GamePresenter presenter;

	public GameFragment() {}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment GameFragment.
	 */
	public static GameFragment newInstance() {
		GameFragment fragment = new GameFragment();
		presenter = GamePresenter.getInstance();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		return v;
	}
}
