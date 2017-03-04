package delta.monstarz.shared.commands;

import java.util.Collection;
import java.util.List;

import delta.monstarz.shared.model.DestCard;

/**
 * Created by oliphaun on 2/25/17.
 */

public class DrawDestCardsCommand extends BaseCommand {
	protected List<DestCard> choices;
	protected int mustKeep = 1;

	public List<DestCard> getChoices() {
		return choices;
	}
	public void setChoices(List<DestCard> choices) {
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
		name = "DrawDestCardsCommand";
	}

	@Override
	public void execute() {}
}
