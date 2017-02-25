package delta.monstarz.shared.commands;

import java.util.Collection;
import delta.monstarz.shared.model.DestCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class SelectDestCardsCommand extends BaseCommand {
	protected Collection<DestCard> selection;

	public Collection<DestCard> getChoices() {
		return selection;
	}
	public void setChoices(Collection<DestCard> choices) {
		this.selection = choices;
	}

	public SelectDestCardsCommand(String username, int gameID, Collection<DestCard> selection) {
		super(username, gameID);
		this.selection = selection;
	}

	@Override
	public void execute() {}
}