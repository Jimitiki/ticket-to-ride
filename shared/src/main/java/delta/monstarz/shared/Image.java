package delta.monstarz.shared;

import java.io.File;

/**
 * This is a bean describing an image. It contains an image file and the height and width of the image
 *
 * @author bradcarter
 */
public class Image
{
	//Instance Variables
	private File mFile;
	private int mWidht;
	private int mHeight;

	//Constructors
	public Image(File pFile, int pWidht, int pHeight)
	{
		mFile = pFile;
		mWidht = pWidht;
		mHeight = pHeight;
	}

	//Getters and Setters
	public File getFile()
	{
		return mFile;
	}

	public void setFile(File pFile)
	{
		mFile = pFile;
	}

	public int getWidht()
	{
		return mWidht;
	}

	public void setWidht(int pWidht)
	{
		mWidht = pWidht;
	}

	public int getHeight()
	{
		return mHeight;
	}

	public void setHeight(int pHeight)
	{
		mHeight = pHeight;
	}
}
