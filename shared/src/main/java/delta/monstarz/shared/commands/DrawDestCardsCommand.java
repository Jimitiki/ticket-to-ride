package delta.monstarz.shared.commands;

import java.util.ArrayList;

import delta.monstarz.shared.model.DestCard;

public class DrawDestCardsCommand extends BaseCommand {
	protected ArrayList<DestCard> choices;
	protected int mustKeep;

	public List<DestCard> getChoices() {
		return choices;
	}
	public void setChoices(ArrayList<DestCard> choices) {
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
