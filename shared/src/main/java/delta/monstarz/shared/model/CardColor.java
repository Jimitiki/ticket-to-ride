package delta.monstarz.shared.model;


/**
 * @author bradcarter
 */

public enum CardColor
{
	BLACK,
	BLUE,
	GOLD,
	GREEN,
	ORANGE,
	PINK,
	RED,
	WHITE,
	YELLOW,
	UNKNOWN;

	public static CardColor fromString(String s) {
		switch(s) {
			case "blue":
				return BLUE;
			case "green":
				return GREEN;
			case "pink":
				return PINK;
			case "red":
				return RED;
			case "yellow":
				return YELLOW;
			case "black":
				return BLACK;
			case "orange":
				return ORANGE;
			case "white":
				return WHITE;
			case "gray":
			case "gold":
				return GOLD;
			default:
				return UNKNOWN;
		}
	}

	@Override
	public String toString(){
		return name().toLowerCase();
	}
}
