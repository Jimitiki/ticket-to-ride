package delta.monstarz.shared.commands;

import delta.monstarz.shared.model.Route;

public class ClaimRouteCommand extends BaseCommand {
    protected Route route;
    protected boolean hasLongest;

    public ClaimRouteCommand(String username, int gameID, Route r) {
        super(username, gameID);
        route = r;
        name = "ClaimRouteCommand";
    }

    public void setHasLongest(boolean has) {
        hasLongest = has;
    }

    @Override
    public void execute() {}
}
