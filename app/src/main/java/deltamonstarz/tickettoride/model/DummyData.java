package deltamonstarz.tickettoride.model;

/**
 * Created by oliphaun on 2/24/17.
 */

public class DummyData {
    private static ClientModel model = ClientModel.getInstance();

    public static void doTest() {
        model.addPlayer(model.getUsername());
        model.addPlayer("joe");
        model.addPlayer("sam");
//        model.addPlayer("dustin");
        model.startGame();

//        turn order?

//        model.
    }

}
