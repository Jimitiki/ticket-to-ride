package deltamonstarz.tickettoride.presenters;

import android.service.chooser.ChooserTargetService;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import delta.monstarz.shared.model.TrainCard;
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
		if (updateType == UpdateType.FACE_UP_CARD){
			if (chooseCardDialog != null) {
				List<TrainCard> cards = model.getGame().getFaceUpCards();
				chooseCardDialog.setCards(cards);
			}
		}
	}

	public void drawDeckCard(){

	}

	public void drawFaceUpCard(int index){

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
