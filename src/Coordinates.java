
public class Coordinates 
{
	public int x;
	public int y;
	public int arrX;
	public int arrY;

	public Coordinates(int xCoord, int yCoord)
	{
		this.x = xCoord;
		this.y = yCoord;
		this.arrX = xCoord * 4 + 2;
		this.arrY = -yCoord * 2 + 15;
	}


	public boolean equals(Coordinates other)
	{

		return this.x == other.x && this.y == other.y;

	}

	/**
	 * @return the x
	 */
	public int getX() 
	{
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) 
	{
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() 
	{
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) 
	{
		this.y = y;
	}

}
