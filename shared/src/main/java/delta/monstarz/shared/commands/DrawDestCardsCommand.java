package delta.monstarz.shared.commands;

import java.util.Collection;

import delta.monstarz.shared.model.DestCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class DrawDestCardsCommand extends BaseCommand {
	protected Collection<DestCard> choices;
	protected int mustKeep;

	public Collection<DestCard> getChoices() {
		return choices;
	}
	public void setChoices(Collection<DestCard> choices) {
		this.choices = choices;
	}
	public int getMustKeep() {
		return mustKeep;
	}
	public void setMustKeep(int mustKeep) {
		this.mustKeep = mustKeep;
	}

	public DrawDestCardsCommand(String username, int gameID) {
		super(username, gameID);
	}

	@Override
	public void execute() {}
}
