package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.TrainCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class DrawTrainCardCommand extends BaseCommand {
	protected TrainCard cardDrawn;

	public TrainCard getCardDrawn() {return cardDrawn;}
	public void setCardDrawn(TrainCard cardDrawn) {
		this.cardDrawn = cardDrawn;
	}

	public DrawTrainCardCommand(String username, int gameID) {
		super(username, gameID);
		name = "DrawTrainCardCommand";
		isGlobal = false;
	}

	@Override
	public void execute() {}
}
