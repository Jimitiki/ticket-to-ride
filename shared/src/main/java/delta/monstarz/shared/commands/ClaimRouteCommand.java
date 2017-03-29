package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.Route;

public class ClaimRouteCommand extends BaseCommand {
    protected int routeID;
    protected CardColor cardsUsed;
    protected int goldCardCount;

    public ClaimRouteCommand(String username, int gameID, int routeID, CardColor color, int goldCardCount) {
        super(username, gameID);
        isGlobal = true;
        this.routeID = routeID;
	    this.cardsUsed = color;
	    this.goldCardCount = goldCardCount;
        name = "ClaimRouteCommand";
    }

    @Override
    public void execute() {}
}
