package delta.monstarz.shared.commands;

import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.DestCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class SelectDestCardsCommand extends BaseCommand {
	protected ArrayList<DestCard> selection;
	protected ArrayList<DestCard> discard;

	public List<DestCard> getChoices() {
		return selection;
	}
	public void setChoices(ArrayList<DestCard> choices) {
		this.selection = choices;
	}

	public SelectDestCardsCommand(String username, int gameID, ArrayList<DestCard> selection, ArrayList<DestCard> discard) {
		super(username, gameID);
		this.selection = selection;
		this.discard = discard;
		name = "SelectDestCardsCommand";
	}

	@Override
	public void execute() {}
}