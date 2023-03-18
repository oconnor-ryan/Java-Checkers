
public class GuiLocation 
{
	private int x;
	private int y;
	private int xSize;
	private int ySize;

	
	public GuiLocation(int x, int y, int xSize, int ySize)
	{
		this.x = x;
		this.y = y;
		this.xSize = xSize;
		this.ySize = ySize;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}

	public boolean contains(int eX, int eY)
	{
		return eX > x && eX < x + xSize && eY > y && eY < y + ySize;
	}
}
