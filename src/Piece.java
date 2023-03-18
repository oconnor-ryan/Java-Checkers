import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class Piece extends JPanel
{

	private static final long serialVersionUID = 1L;
	
	private Color teamColor;
    private boolean isSelected;
	private int row;
	private int col;  //should learn to keep its position
//	private boolean hasToJump; //has to jump
	private boolean isKing;
	private ArrayList<Piece> capturedPieces;
	private boolean hasToMultiJump;

	public Piece(Color color, int r, int c)
	{
		capturedPieces = new ArrayList<Piece>();
		row = r;
		col = c;
		hasToMultiJump = false;
		teamColor = color;
		isSelected = false;
		isKing = false;
	}
	
	public int getRow()
	{
		return row;
	}
	public int getCol()
	{
		return col;
	}
	
	public void hasToMultiJump(boolean b)
	{
		hasToMultiJump = b;
	}
	
	public boolean hasToMultiJump()
	{
		return hasToMultiJump;
	}
	
	public void setRow(int r)
	{
		row = r;
	}
	public void setCol(int c)
	{
		col = c;
	}
	public Color getColor()
	{
		return teamColor;
	}
	
	public void setSelected()
	{ 
		isSelected = !isSelected;
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	
	public boolean isKing()
	{
		return isKing;
	}
	
	public void setKing()
	{
		isKing = true;
	}
	
	public ArrayList<int[]> allAdvances(CheckersGame game)
	{
		ArrayList<int[]> allAdvances = new ArrayList<int[]>();
		int r0 = this.getRow();
		int c0 = this.getCol();
		int moveRow;
		if(this.getColor() == Color.RED)
		{
			moveRow = -1;
		}
		else
		{
			moveRow = 1;
		}

		for(int k = 0; k < 2; k++)
		{
			if(game.isValid(r0 + moveRow, c0 + 1) && game.get(r0 + moveRow, c0 + 1) == null)
			{
				allAdvances.add(new int[] {r0 + moveRow, c0 + 1});
			}
			if(game.isValid(r0 + moveRow, c0 - 1) && game.get(r0 + moveRow, c0 - 1) == null)
			{
				allAdvances.add(new int[] {r0 + moveRow, c0 - 1});
			}
			if(!this.isKing())
			{
				return allAdvances;
			}
			else
			{
				if(moveRow == 1)
				{
					moveRow = -1;
				}
				else
				{
					moveRow = 1;
				}
			}
		}
		return allAdvances;
	}
	
	public ArrayList<int[]> allCaptures(CheckersGame game)
	{
		ArrayList<int[]> allCaptures = new ArrayList<int[]>();
		capturedPieces = new ArrayList<Piece>();
		int r0 = this.getRow();
		int c0 = this.getCol();
		int moveRow;
		int moveCol = 1;
		if(this.getColor() == Color.RED)
		{
			moveRow = -1;
		}
		else
		{
			moveRow = 1;
		}
	
		for(int k = 0; k < 4; k++)// skips check from r1 c1 mouse input
		{
			if(k == 2 && this.isKing())
			{
				if(moveRow == 1)
				{
					moveRow = -1;
				}
				else
				{
					moveRow = 1;
				}
			}
			
			if(game.isValid(r0 + 2*moveRow, c0 + 2*moveCol) && game.get(r0 + 2*moveRow, c0 + 2*moveCol) == null)
			{
				Piece other = game.get(r0 + moveRow, c0 + moveCol);
				if(other != null && other.getColor() != this.getColor())
				{
					allCaptures.add(new int[] {r0 + 2*moveRow, c0 + 2*moveCol});
					capturedPieces.add(other);
				}
			}
			
			if(!this.isKing() && k > 1)
			{
				return allCaptures;
			}

			if(moveCol == 1)
			{
				moveCol = -1;
			}
			else 
			{
				moveCol = 1;
			}
			
		}
			
		return allCaptures;
	}
	
	//find out how to set all hasToJump pieces for each turn; on reds turn, blue pieces cannot be marked as pink and vise versa
	//include for loop that sets the hasToJump pieces depending on whose turn it is
	public void move(CheckersGame game, int r1, int c1)
	{
		int r0 = this.getRow();
		int c0 = this.getCol();
		
		if(this.getColor() == Color.RED && !game.isXTurn())
		{
			return;
		}
		else if(this.getColor() != Color.RED && game.isXTurn())
		{
			return;
		}
	
		if(allCaptures(game).size() > 0)
		{
			hasToMultiJump = true;
			for(int k = 0; k < allCaptures(game).size(); k++)
			{
				if(r1 == allCaptures(game).get(k)[0] && c1 == allCaptures(game).get(k)[1])
				{
					this.setRow(r1);
					this.setCol(c1);
					game.add(this);
					game.remove(r0,c0);
					Piece captured = capturedPieces.get(k);
					game.remove(captured.getRow(), captured.getCol());
					if(allCaptures(game).size() > 0)
					{
						hasToMultiJump = true;
					}
					else
					{
						game.nextTurn();
						hasToMultiJump = false;
					}
					
					if(this.getColor() == Color.RED && this.getRow() == 0)
					{
						this.setKing();
		//				game.nextTurn();
						hasToMultiJump = false;
					}
					else if(this.getColor() != Color.RED && this.getRow() == game.getRows() - 1)
					{
						this.setKing();
		//				game.nextTurn();
						hasToMultiJump = false;
					}
					return;	
				}
			}
			return;
		}
		else if(allAdvances(game).size() > 0)
		{
	//		System.out.println(Arrays.deepToString(allAdvances(game).toArray()));
			hasToMultiJump = false;
			for(int k = 0; k < allAdvances(game).size(); k++)
			{
				if(r1 == allAdvances(game).get(k)[0] && c1 == allAdvances(game).get(k)[1])
				{
					this.setRow(r1);
					this.setCol(c1);
					game.add(this);
					game.remove(r0,c0);
					game.nextTurn();
					
				}
			}
		}
			
			
			
		if(r1 == 0 && this.getColor() == Color.RED)
		{
			this.setKing();
		//	game.nextTurn();
			return;
		}
		else if(r1 == game.getRows() - 1 && this.getColor() != Color.RED)
		{
			this.setKing();
		//	game.nextTurn();
			return;
		} 

//		if(!this.hasToJump())
//		{
//			game.nextTurn();
//		}
		
	}

	
	public void drawPiece(Graphics g, GuiLocation loc, int xSize, int ySize)
	{
		int x = loc.getX();
		int y = loc.getY();
		g.setColor(teamColor);
		
		if(isSelected)
		{
			g.setColor(Color.GREEN);
		}
		
		g.fillOval(x,y,xSize,ySize);
		if(isKing)
		{
			g.setColor(Color.YELLOW);
			g.fillOval(x + xSize/4, y + ySize/4, xSize/2, ySize/2);
		}
		
	}
	
	
	
}

