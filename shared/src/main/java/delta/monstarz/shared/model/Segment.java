package delta.monstarz.shared.model;

import java.awt.Point;

/**
 * @author bradcarter
 */
public class Segment
{

//	private Point position;
	private int x;
	private int y;
	private int rotation;

	public Segment(int x, int y, int pRotation)
	{
//		position = pPosition;
		this.x = x;
		this.y = y;
		rotation = pRotation;
	}

//	public Point getPosition()
//	{
//		return position;
//	}
//
//	public void setPosition(Point pPosition)
//	{
//		position = pPosition;
//	}

	public int getRotation()
	{
		return rotation;
	}

	public void setRotation(int pRotation)
	{
		rotation = pRotation;
	}
}
