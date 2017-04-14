package delta.monstarz.shared.model;

import java.io.Serializable;

/**
 * Created by oliphaun on 2/24/17.
 */

public class TrainCard implements Serializable {
    private CardColor color;

    public TrainCard(CardColor pColor)
    {
        color = pColor;
    }

	public CardColor getColor() {
		return color;
	}

	@Override
	public String toString() {
		return color.toString() + " Card";
	}
}
