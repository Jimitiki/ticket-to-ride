package delta.monstarz.shared.commands;

import java.util.List;

import delta.monstarz.shared.GameInfo;
import delta.monstarz.shared.model.Route;

/**
 * Created by oliphaun on 2/22/17.
 */

public abstract class ClaimRouteCommand extends BaseCommand {
    protected Route route;
    protected boolean hasLongest;

    public ClaimRouteCommand(String username, Route r) {
        super(username);
        route = r;
    }

    public void setHasLongest(boolean has) {
        hasLongest = has;
    }

//    @Override
//    public void execute() {}
}
