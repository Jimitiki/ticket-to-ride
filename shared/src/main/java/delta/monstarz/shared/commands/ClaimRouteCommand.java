package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Route;

public class ClaimRouteCommand extends BaseCommand {
    protected int routeID;
    protected CardColor cardsUsed;

    public ClaimRouteCommand(String username, int gameID, int routeID, CardColor color) {
        super(username, gameID);
        this.routeID = routeID;
	    this.cardsUsed = color;
        name = "ClaimRouteCommand";
    }

    @Override
    public void execute() {}
}
