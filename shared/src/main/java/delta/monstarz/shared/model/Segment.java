package delta.monstarz.shared.model;

import com.google.gson.JsonObject;

public class Segment {
	private int x;
	private int y;
	private int rotation;

	Segment(JsonObject jsonSegment) {
		x = jsonSegment.get("x").getAsInt();
		y = jsonSegment.get("y").getAsInt();
		rotation = jsonSegment.get("rotation").getAsInt();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getRotation() {
		return rotation;
	}
}
