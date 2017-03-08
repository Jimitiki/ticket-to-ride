package deltamonstarz.tickettoride.model;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

import delta.monstarz.shared.Message;
import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.PlayerInfo;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.commands.ClientDrawTrainCardCommand;
import deltamonstarz.tickettoride.commands.ClientSendMessageCommand;
import deltamonstarz.tickettoride.commands.ClientUpdatePlayerInfoCommand;
import deltamonstarz.tickettoride.presenters.GamePresenter;
import deltamonstarz.tickettoride.views.gamePlay.GameFragment;

/**
 * Created by lyman126 on 3/8/17.
 */

public class DemoUtility {

	public static int index = 0;
	private static Context appContext;
	private static ClientModel model = ClientModel.getInstance();

	public static void nextDemo(Context context){
		appContext = context;
		executeDemo(index);
		index++;
		appContext = null;
	}

	private static void executeDemo(int i){
		switch(i){
			case 0:
				demo0();
				break;
			case 1:
				demo1();
				break;

			case 2:
				demo2();
				break;

			case 3:
				demo3();
				break;

			case 4:
				demo4();
				break;

			case 5:
				demo5();
				break;

			case 6:
				demo6();
				break;

			case 7:
				demo7();
				break;
			case 8:
				demo8();
				break;
		}
	}

	private static void showToast(String text){
		Toast.makeText(appContext, text, Toast.LENGTH_SHORT).show();
	}

	private static void demo0(){

		// Draw gold card
		ClientDrawTrainCardCommand commandCard = new ClientDrawTrainCardCommand(model.getUsername(), model.getGameID(), 0);
		commandCard.setCardDrawn(new TrainCard(CardColor.GOLD));
		commandCard.execute();

		// Update player info
		PlayerInfo changedInfo = null;
		for (PlayerInfo playerInfo:model.getPlayerInfos()){
			if (playerInfo.getUsername().equals(model.getUsername())){
				changedInfo = playerInfo;
			}
		}

		changedInfo.setNumTrainsCards(changedInfo.getNumTrainsCards() + 1);
		ClientUpdatePlayerInfoCommand commandInfo = new ClientUpdatePlayerInfoCommand(changedInfo);
		commandInfo.execute();

		showToast("Drew a new gold train card");

	}

	private static void demo1(){
		// Opponent draws two train cards

		List<PlayerInfo> infos = model.getPlayerInfos();

		PlayerInfo playerInfo = null;
		for (int i = 0; i < infos.size(); i++){
			if (!model.getUsername().equals(infos.get(i).getUsername())){
				playerInfo = infos.get(i);
				i = infos.size();
			}
		}


		playerInfo.setNumTrainsCards(playerInfo.getNumTrainsCards() + 2);
		ClientUpdatePlayerInfoCommand command = new ClientUpdatePlayerInfoCommand(playerInfo);
		command.execute();

		showToast("Opponent drew two train cards");
	}

	private static void demo2(){
		GameFragment fragment = GamePresenter.getInstance().getGameFragment();
		fragment.openChat();
		final Handler handler = new Handler();
		List<PlayerInfo> players = model.getPlayerInfos();
		for (int i = 0; i < players.size(); i++) {
			PlayerInfo playerInfo = players.get(i);
			final String username = playerInfo.getUsername();
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					new ClientSendMessageCommand(username, model.getGameID(), new Message("Hello, I am " + username, username)).execute();
				}
			}, i * 200);
		}
	}

	private static void demo3(){
		// Set our points to 5

		// Update player info
		PlayerInfo changedInfo = null;
		for (PlayerInfo playerInfo:model.getPlayerInfos()){
			if (playerInfo.getUsername().equals(model.getUsername())){
				changedInfo = playerInfo;
			}
		}

		changedInfo.setScore(changedInfo.getScore() + 5);
		ClientUpdatePlayerInfoCommand commandInfo = new ClientUpdatePlayerInfoCommand(changedInfo);
		commandInfo.execute();

		showToast("Current player's score incremented by 5");

	}

	private static void demo4(){
		// Update player info
		PlayerInfo changedInfo = null;
		for (PlayerInfo playerInfo:model.getPlayerInfos()){
			if (playerInfo.getUsername().equals(model.getUsername())){
				changedInfo = playerInfo;
			}
		}

		changedInfo.setNumTrains(changedInfo.getNumTrains() - 4);
		ClientUpdatePlayerInfoCommand commandInfo = new ClientUpdatePlayerInfoCommand(changedInfo);
		commandInfo.execute();

		showToast("Current player's trains decremented by 4");
	}

	private static void demo5(){
		// Update player info
		PlayerInfo changedInfo = null;
		for (PlayerInfo playerInfo:model.getPlayerInfos()){
			if (playerInfo.getUsername().equals(model.getUsername())){
				changedInfo = playerInfo;
			}
		}

		changedInfo.setHasLongestRoute(true);
		ClientUpdatePlayerInfoCommand commandInfo = new ClientUpdatePlayerInfoCommand(changedInfo);
		commandInfo.execute();

		showToast("Current player now has longest route");
	}

	private static void demo6(){
		// Update player info
		PlayerInfo changedInfo = null;
		for (PlayerInfo playerInfo:model.getPlayerInfos()){
			if (playerInfo.getUsername().equals(model.getUsername())){
				changedInfo = playerInfo;
			}
		}

		changedInfo.setPlayersTurn(true);
		ClientUpdatePlayerInfoCommand commandInfo = new ClientUpdatePlayerInfoCommand(changedInfo);
		commandInfo.execute();

		showToast("It is now the current player's turn");
	}

	private static void demo7(){

	}

	private static void demo8(){

	}
}
