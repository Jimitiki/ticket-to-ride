package deltamonstarz.tickettoride.tasks;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;

import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.commands.ClientAuthBadCommand;
import deltamonstarz.tickettoride.commands.ClientClaimRouteCommand;
import deltamonstarz.tickettoride.commands.ClientDrawTrainCardCommand;
import deltamonstarz.tickettoride.commands.ConnectionErrorCommand;
import deltamonstarz.tickettoride.model.ClientModel;

/**
 * Created by cwjohn42 on 2/15/17.
 */

public class DemoAsyncTask extends AsyncTask<String, Integer, Integer> {
	@Override
	protected Integer doInBackground(String... params) {
		try {
			Thread.sleep(4000);
			TrainCard card1 = new TrainCard(CardColor.BLACK);
			TrainCard card2 = new TrainCard(CardColor.BLUE);
			ClientModel model = ClientModel.getInstance();
			DrawTrainCardCommand command = new DrawTrainCardCommand(model.getUsername(), model.getGameID(), 1);
			ClientDrawTrainCardCommand cli_command = (ClientDrawTrainCardCommand) command;
			cli_command.setCardDrawn(card1);
			cli_command.execute();
			cli_command.execute();
			cli_command.execute();
			cli_command.setCardDrawn(card2);
			cli_command.execute();
		} catch(Exception e) {

		}
		return 200;
	}

	@Override
	protected void onPostExecute(Integer status) {
	}
}
