package deltamonstarz.tickettoride.views.gamePlay;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.PlayerInfo;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.model.ClientModel;

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

		blue = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_01_blue);
		if (blue == null){
			blue = new PlayerInfoFragment();
			blue.setColor(PlayerColor.BLUE);
			blue.setGameInfoFragment(this);
			fm.beginTransaction().add(R.id.player_info_fragment_01_blue, blue).commit();
		}

		green = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_02_green);
		if (green == null){
			green = new PlayerInfoFragment();
			green.setColor(PlayerColor.GREEN);
			green.setGameInfoFragment(this);
			fm.beginTransaction().add(R.id.player_info_fragment_02_green, green).commit();
		}

		red = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_03_red);
		if (red == null){
			red = new PlayerInfoFragment();
			red.setColor(PlayerColor.RED);
			red.setGameInfoFragment(this);
			fm.beginTransaction().add(R.id.player_info_fragment_03_red, red).commit();
		}

		yellow = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_04_yellow);
		if (yellow == null){
			yellow = new PlayerInfoFragment();
			yellow.setColor(PlayerColor.YELLOW);
			yellow.setGameInfoFragment(this);
			fm.beginTransaction().add(R.id.player_info_fragment_04_yellow, yellow).commit();
		}

		black = (PlayerInfoFragment) fm.findFragmentById(R.id.player_info_fragment_05_black);
		if (black == null){
			black = new PlayerInfoFragment();
			black.setColor(PlayerColor.BLACK);
			black.setGameInfoFragment(this);
			fm.beginTransaction().add(R.id.player_info_fragment_05_black, black).commit();
		}


		update();

	}


	public void update(){
		for (PlayerInfo playerInfo: ClientModel.getInstance().getGame().getPlayerInfos()) {
			switch(playerInfo.getPlayerColor()){
				case BLUE:
					blue.update(playerInfo);
					break;
				case GREEN:
					green.update(playerInfo);
					break;
				case RED:
					red.update(playerInfo);
					break;
				case YELLOW:
					yellow.update(playerInfo);
					break;
				case BLACK:
					black.update(playerInfo);
					break;
			}
		}
	}
}
