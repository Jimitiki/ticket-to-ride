package delta.monstarz.shared.model;

/**
 * @author bradcarter
 */

public enum PlayerColor
{
	BLUE (0),
	GREEN (1),
	RED (2),
	YELLOW (3),
	BLACK (4);

	private int value;

	private PlayerColor(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static PlayerColor getColorByValue(int value) {
		switch (value) {
			case 0:
				return BLUE;
			case 1:
				return GREEN;
			case 2:
				return RED;
			case 3:
				return YELLOW;
			case 4:
				return BLACK;
		}
		return null;
	}
}
