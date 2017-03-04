package delta.monstarz.shared.model;


import com.google.gson.annotations.SerializedName;

/**
 * @author bradcarter
 */

public enum CardColor
{
	BLUE,
	GREEN,
	PINK,
	RED,
	YELLOW,
	BLACK,
	ORANGE,
	WHITE,
	GOLD,
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
			case "gold":
				return GOLD;



			default:
				return UNKNOWN;


		}
	}
}
