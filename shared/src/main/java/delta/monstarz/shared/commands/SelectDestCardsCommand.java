package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.model.DestCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class SelectDestCardsCommand extends BaseCommand {
	protected List<DestCard> selection;
	protected List<DestCard> discard;

	public List<DestCard> getChoices() {
		return selection;
	}
	public void setChoices(List<DestCard> choices) {
		this.selection = choices;
	}

	public SelectDestCardsCommand(String username, int gameID, List<DestCard> selection, List<DestCard> discard) {
		super(username, gameID);
		this.selection = selection;
		this.discard = discard;
	}

	@Override
	public void execute() {}
}