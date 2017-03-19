package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Route;

public class ClaimRouteCommand extends BaseCommand {
    protected Route route;
	protected CardColor cardsUsed;
    protected boolean hasLongest;

    public ClaimRouteCommand(String username, int gameID, Route route, CardColor color) {
        super(username, gameID);
        this.route = route;
	    this.cardsUsed = color;
        name = "ClaimRouteCommand";
    }

    public void setHasLongest(boolean has) {
        hasLongest = has;
    }

    @Override
    public void execute() {}
}
