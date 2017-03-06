package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.TrainCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class DrawTrainCardCommand extends BaseCommand {
	protected int drawPileID;
	protected TrainCard cardDrawn;

	public int getDrawPileID() {return drawPileID;}
	public void setDrawPileID(int drawPileID) {this.drawPileID = drawPileID;}
	public TrainCard getCardDrawn() {return cardDrawn;}
	public void setCardDrawn(TrainCard cardDrawn) {
		this.cardDrawn = cardDrawn;
	}

	public DrawTrainCardCommand(String username, int gameID, int drawPileID) {
		super(username, gameID);
		this.drawPileID = drawPileID;
		name = "DrawTrainCardCommand";
	}

	@Override
	public void execute() {}
}
