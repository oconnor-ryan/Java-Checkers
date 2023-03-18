import java.awt.*;

import javax.swing.*;

public class BoardPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	private boolean isHorizontal;
	
	//game and GuiLoc have corresponding rows and cols
	private CheckersGame game;
	private GuiLocation[][] locs;
	
	public BoardPanel(int rows, int cols)
	{
		game = new CheckersGame();
		locs = new GuiLocation[rows][cols];
		isHorizontal = false;  //choose to flip board onto its side
		game.setBoard();
		MouseHandler mouse = new MouseHandler(locs, game, this);
		addMouseListener(mouse);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		int w = getWidth();
		int h = getHeight();
		int xSize = w/10;
		int ySize = h/10;
		
		if(isHorizontal)
		{
			for(int x = w/10, r = 0; x < w/10 + 8*xSize; x += xSize, r++)
			{
				for(int y = h/10, c = 0; y < h/10 + 8*ySize; y += ySize, c++)
				{
					g.setColor(Color.WHITE);
					g.drawRect(x, y, xSize, ySize);
					locs[r][c] = new GuiLocation(x,y,xSize,ySize);
	
					if(game.get(r, c) != null)
					{
						game.get(r, c).drawPiece(g, locs[r][c], xSize, ySize);
					}
				}
			}
		}
		else
		{
			for(int x = w/10, r = 0; x < w/10 + 8*xSize; x += xSize, r++)
			{
				for(int y = h/10, c = 0; y < h/10 + 8*ySize; y += ySize, c++)
				{
					g.setColor(Color.WHITE);
					g.drawRect(x, y, xSize, ySize);
					locs[c][r] = new GuiLocation(x,y,xSize,ySize);
	
					if(game.get(c, r) != null)
					{
						game.get(c, r).drawPiece(g, locs[c][r], xSize, ySize);
					}
				}
			}
		}
		
		if(game.otherWon())
		{
			g.setColor(Color.BLUE);
			g.setFont(new Font("serif", w/11, h/11));
			g.drawString("Blue Won!", w/3, h);
		}
		else if(game.xWon())
		{
			g.setColor(Color.RED);
			g.setFont(new Font("serif", w/11, h/11));
			g.drawString("Red Won!", w/3, h);
		}
		else if(game.isXTurn())
		{
			g.setColor(Color.RED);
			g.setFont(new Font("serif", w/11, h/11));
			g.drawString("Red's Turn", w/3, h);
		}
		else
		{
			g.setColor(Color.BLUE);
			g.setFont(new Font("serif", w/11, h/11));
			g.drawString("Blue's Turn", w/3, h);
		}
	}
}
