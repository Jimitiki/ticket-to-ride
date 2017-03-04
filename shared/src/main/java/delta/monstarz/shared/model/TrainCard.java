package delta.monstarz.shared.model;

import javax.smartcardio.Card;

/**
 * Created by oliphaun on 2/24/17.
 */

public class TrainCard {
    private int index;
    private CardColor color;
    private String imageID;

    public TrainCard(int pIndex, CardColor pColor, String pImageID)
    {
        index = pIndex;
        color = pColor;
        imageID = pImageID;
    }
}
