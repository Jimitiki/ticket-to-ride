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
}
