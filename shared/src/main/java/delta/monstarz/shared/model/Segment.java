package delta.monstarz.shared.model;

import java.awt.Point;

/**
 * @author bradcarter
 */
public class Segment
{
	private Point position;
	private int rotation;

	public Segment(Point pPosition, int pRotation)
	{
		position = pPosition;
		rotation = pRotation;
	}

	public Point getPosition()
	{
		return position;
	}

	public void setPosition(Point pPosition)
	{
		position = pPosition;
	}

	public int getRotation()
	{
		return rotation;
	}

	public void setRotation(int pRotation)
	{
		rotation = pRotation;
	}
}
