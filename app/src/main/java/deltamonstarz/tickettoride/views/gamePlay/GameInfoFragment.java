package deltamonstarz.tickettoride.views.gamePlay;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import delta.monstarz.shared.model.PlayerColor;
import deltamonstarz.tickettoride.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameInfoFragment extends Fragment {

	PlayerInfoFragment blue;
	PlayerInfoFragment green;
	PlayerInfoFragment red;
	PlayerInfoFragment yellow;
	PlayerInfoFragment black;

	public GameInfoFragment() {
		// Required empty public constructor
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_game_info, container, false);



		return view;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Add each Fragment

		// Add Game/Player Info Fragment
		FragmentManager fm = getChildFragmentManager();

		// Todo: Change setColor() to an enum value
		blue = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_01_blue);
		if (blue == null){
			blue = new PlayerInfoFragment();
			blue.setColor(PlayerColor.BLUE);
			fm.beginTransaction().add(R.id.player_info_fragment_01_blue, blue).commit();
		}

		green = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_02_green);
		if (green == null){
			green = new PlayerInfoFragment();
			green.setColor(PlayerColor.GREEN);
			fm.beginTransaction().add(R.id.player_info_fragment_02_green, green).commit();
		}

		red = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_03_red);
		if (red == null){
			red = new PlayerInfoFragment();
			red.setColor(PlayerColor.RED);
			fm.beginTransaction().add(R.id.player_info_fragment_03_red, red).commit();
		}

		yellow = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_04_yellow);
		if (yellow == null){
			yellow = new PlayerInfoFragment();
			yellow.setColor(PlayerColor.YELLOW);
			fm.beginTransaction().add(R.id.player_info_fragment_04_yellow, yellow).commit();
		}

		black = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_05_black);
		if (black == null){
			black = new PlayerInfoFragment();
			black.setColor(PlayerColor.BLACK);
			fm.beginTransaction().add(R.id.player_info_fragment_05_black, black).commit();
		}



	}
}
