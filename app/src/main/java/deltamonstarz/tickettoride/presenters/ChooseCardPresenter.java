package deltamonstarz.tickettoride.presenters;

import android.service.chooser.ChooserTargetService;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import delta.monstarz.shared.commands.DrawTrainCardCommand;
import delta.monstarz.shared.commands.SelectTrainCardCommand;
import delta.monstarz.shared.model.TrainCard;
import deltamonstarz.tickettoride.ServerProxy;
import deltamonstarz.tickettoride.model.ClientModel;
import deltamonstarz.tickettoride.model.UpdateType;
import deltamonstarz.tickettoride.views.gamePlay.ChooseCardDialog;

/**
 * Created by lyman126 on 3/18/17.
 */

public class ChooseCardPresenter extends BasePresenter {
	private static ChooseCardPresenter presenter;
	private ChooseCardDialog chooseCardDialog;

	private ChooseCardPresenter() {
		super();
	}

	public static ChooseCardPresenter getInstance() {
		if (presenter == null){
			presenter = new ChooseCardPresenter();
		}
		return presenter;
	}


	@Override
	public void update(UpdateType updateType) {


		if (chooseCardDialog == null){
			return;
		}

		switch(updateType){
			case FACE_UP_CARD:
				List<TrainCard> cards = model.getGame().getFaceUpCards();
				chooseCardDialog.setCards(cards);
				break;

			case REPORT_DRAWN_CARD:
				chooseCardDialog.reportCardType(ClientModel.getInstance().getGame().getMostRecentCardColor());
				break;
		}

	}

	public void drawDeckCard(){
		DrawTrainCardCommand command = new DrawTrainCardCommand(model.getUsername(), model.getGame().getGameID());
		ServerProxy.getInstance().sendCommand(model.getAuthToken(), command);
	}

	public void drawFaceUpCard(int index){
		SelectTrainCardCommand command = new SelectTrainCardCommand(model.getUsername(), model.getGameID(), index);
		ServerProxy.getInstance().sendCommand(model.getAuthToken(), command);
	}

	public void setChooseCardDialog(ChooseCardDialog chooseCardDialog) {
		this.chooseCardDialog = chooseCardDialog;
	}

	@Override
	public void onConnectionError() {

	}

	@Override
	public void logOut() {

	}

	@Override
	public AppCompatActivity getActivity() {
		return null;
	}
}
