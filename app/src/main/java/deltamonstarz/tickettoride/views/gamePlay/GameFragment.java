package deltamonstarz.tickettoride.views.gamePlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.DestCard;
import delta.monstarz.shared.model.Player;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.PlayerInfo;
import delta.monstarz.shared.model.Route;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.commands.ClientUpdatePlayerInfoCommand;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.model.DemoUtility;
import deltamonstarz.tickettoride.presenters.ChatPresenter;
import deltamonstarz.tickettoride.presenters.DestinationCardPresenter;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.presenters.RoutePresenter;
import deltamonstarz.tickettoride.views.GameNameChoiceDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GameFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GameFragment extends Fragment {
	public Handler handler;

	private GamePresenter presenter;
	private GameActivity activity;
	private PlayerCardsFragment playerCardsFragment;
	private GameInfoFragment gameInfoFragment;

	private static MapView mapView;
	private Button drawCard;
	private Button placeTrain;
	private Button viewCards;
	private Button viewHistory;
	private Button viewChat;
	private Button demo;

	private String mapImagePath;

	public GameFragment() {}

	/**
	 * Use this factory method to create a new instance of
	 * this fragment using the provided parameters.
	 *
	 * @return A new instance of fragment GameFragment.
	 */
	public static GameFragment newInstance() {
		return new GameFragment();
	}

	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	public void setMapImagePath(String mapImagePath) {
		this.mapImagePath = mapImagePath;
	}

	public void setPresenter(GamePresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FragmentManager fm = getChildFragmentManager();

		// Add PlayerCard Fragment
		playerCardsFragment = (PlayerCardsFragment) fm.findFragmentById(R.id.player_card_fragment);
		if (playerCardsFragment == null){
			playerCardsFragment = new PlayerCardsFragment();
			fm.beginTransaction().add(R.id.player_card_fragment, playerCardsFragment).commit();
		}

		// Add Game/Player Info Fragment
		gameInfoFragment = (GameInfoFragment) fm.findFragmentById(R.id.players_info_fragment);
		if (gameInfoFragment == null){
			gameInfoFragment = new GameInfoFragment();
			fm.beginTransaction().add(R.id.players_info_fragment, gameInfoFragment).commit();
		}

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_game, container, false);
		drawCard = (Button) v.findViewById(R.id.draw_card_button);
		placeTrain = (Button) v.findViewById(R.id.place_train_button);
		viewCards = (Button) v.findViewById(R.id.destination_cards_button);
		viewHistory = (Button) v.findViewById(R.id.history_button);
		viewChat = (Button) v.findViewById(R.id.chat_button);
		demo = (Button) v.findViewById(R.id.demo_button);

		mapView = (MapView) v.findViewById(R.id.mapView);

		mapView.setActivity(activity);
		mapView.generateBitmap(mapImagePath, 0, 0);

		drawCard.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.print("drawing card");
				Player player = ClientModel.getInstance().getGame().getMe();

				if (player.getDestCards().size() < 2 || player.mustDrawDestinationCard()){
					launchDestinationChooserDialog();
				}
				else{
					launchChooseCardDialog();
				}

			}
		});

		placeTrain.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = activity.getSupportFragmentManager();
				RouteSelectionFragment dialog = new RouteSelectionFragment();
				dialog.setPresenter(new RoutePresenter());
				try {
					dialog.show(fragmentManager, "claim_route_dialog");
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		});

		viewCards.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("opening destination card view");
				launchShowDestinationCardsDialog();
			}
		});

		viewHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("opening history view");
			}
		});

		viewChat.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				openChat();
			}
		});

		demo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RoutePresenter routePresenter = new RoutePresenter();
				List<Route> routes = routePresenter.getAvailableRoutes();
				Route route = routes.get(routes.size() - 1);
				if (route != null) {
					CardColor color = routePresenter.getUsableCards(route.getID()).keySet().iterator().next();
				}
				activity.onGameEnd();
			}
		});

		if (ClientModel.getInstance().getGame().getMe().getDestCards().size() <= 1) {
			disableButtons();
		}

		handler = new Handler(Looper.getMainLooper()){
			@Override
			public void handleMessage(android.os.Message msg) {
				String text = (String) msg.obj;
				Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
			}
		};

		DemoUtility.index = 0;


		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mapView.setClaimedRoutes(presenter.getClaimedRoutes());
	}

	public void enableButtons(){
		if (placeTrain != null) {
			placeTrain.setEnabled(true);
			viewCards.setEnabled(true);
			viewHistory.setEnabled(true);
			viewChat.setEnabled(true);
			demo.setEnabled(true);
		}
	}

	public void disableButtons(){
		placeTrain.setEnabled(false);
		viewCards.setEnabled(false);
		viewHistory.setEnabled(false);
		viewChat.setEnabled(false);
		demo.setEnabled(false);
	}

	private void openChat() {
		System.out.println("opening chat");
		FragmentManager manager = activity.getSupportFragmentManager();
		ChatDialogFragment dialog = new ChatDialogFragment();
		dialog.setActivity(activity);
		dialog.show(manager, "chat_dialog");
	}

	private void advanceDemo() {
		//DemoUtility.nextDemo(getContext());
	}

	private void launchChooseCardDialog(){
		FragmentManager manager = activity.getSupportFragmentManager();
		ChooseCardDialog dialog = new ChooseCardDialog();


		dialog.show(manager, "choose_card_dialog");
	}

	public void launchDestinationChooserDialog(){
		FragmentManager manager = activity.getSupportFragmentManager();
		ChooseDestinationDialog dialog = new ChooseDestinationDialog();

		dialog.show(manager, "choose_destination_dialog");
	}

	private void launchShowDestinationCardsDialog(){
		FragmentManager manager = activity.getSupportFragmentManager();
		ShowDestinationCardsDialog dialog = new ShowDestinationCardsDialog();
		dialog.setDestCardList(presenter.getDestinationCards());

		dialog.show(manager, "show_destination_cards_dialog");
	}

	public void updatePlayerInfo(){
		if (gameInfoFragment != null) {
			gameInfoFragment.update();
		}
	}

	public void updateCardCounts() {
		if (playerCardsFragment != null) {
			playerCardsFragment.update();
		}
	}

	public void onRouteClaimed(List<Route> routes) {
		if (mapView != null) {
			mapView.setClaimedRoutes(routes);
			mapView.redraw();
		}
	}
}
