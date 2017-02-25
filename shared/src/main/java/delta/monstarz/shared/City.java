package delta.monstarz.shared;

import java.awt.Point;

/**
 * Created by Brad Carter on 2/21/2017.
 */

public class City {
	//Data Members
	private String mName;
	private Point mPosition;

	//Constructor
	public City(String pName, Point pPosition)
	{
		mName = pName;
		mPosition = pPosition;
	}

	//Getters and Setters
	public String getName()
	{
		return mName;
	}

	public void setName(String pName)
	{
		mName = pName;
	}

	public Point getPosition()
	{
		return mPosition;
	}

	public void setPosition(Point pPosition)
	{
		mPosition = pPosition;
	}
}
