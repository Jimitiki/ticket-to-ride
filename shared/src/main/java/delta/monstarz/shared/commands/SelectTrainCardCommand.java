package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.TrainCard;

public class SelectTrainCardCommand extends BaseCommand {
	protected int cardSpot;
	protected TrainCard replacementCard;

	public SelectTrainCardCommand(String username, int gameID, int cardSpot) {
		super(username, gameID);
		this.cardSpot = cardSpot;
		isGlobal = true;
		name = "SelectTrainCardCommand";
	}

	public int getCardSpot() {
		return cardSpot;
	}

	public TrainCard getReplacementCard() {
		return replacementCard;
	}

	public void setReplacementCard(TrainCard replacementCard) {
		this.replacementCard = replacementCard;
	}

	@Override
	public void execute() {}
}
