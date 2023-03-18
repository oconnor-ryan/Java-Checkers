import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CheckersGame
{
	private Piece[][] pieceGrid;
	private final int ROWS = 8 , COLS = 8;
	private boolean xTurn;

	public CheckersGame()
	{
		xTurn = true;
		pieceGrid = new Piece[ROWS][COLS];
		setBoard();
	}

	public void setBoard()
	{
		for(int c = 0; c < COLS; c++)
		{
			if(c % 2 == 0)
			{
				this.add(new Piece(Color.BLUE, 1, c));
			}
			else
			{		
				this.add(new Piece(Color.BLUE, 0, c));
				this.add(new Piece(Color.BLUE, 2, c));
			}	

			if(c % 2 != 0)
			{
				this.add(new Piece(Color.RED, 6, c));
			}
			else
			{		
				this.add(new Piece(Color.RED, 5, c));
				this.add(new Piece(Color.RED, 7, c));
			}	
		}	
	}
	
	public int getRows()
	{
		return ROWS;
	}
	
	public int getCols()
	{
		return COLS;
	}
	
	public void add(Piece p)
	{
		pieceGrid[p.getRow()][p.getCol()] = p;
	}
	
	public void remove(int r, int c)
	{
		pieceGrid[r][c] = null;
	}
	
	public Piece get(int r, int c)
	{
		return pieceGrid[r][c];
	}
	
	public Piece[][] getGrid()
	{
		return pieceGrid;
	}
	
	public boolean isValid(int r, int c)
	{
		return r >= 0 && r < ROWS && c >= 0 && c < COLS;
	}
	
	public boolean isOccupied(int r, int c)
	{
		return pieceGrid[r][c] != null;
	}
	
	public void nextTurn()
	{
		xTurn = !xTurn;
	}
	
	public boolean isXTurn()
	{
		return xTurn;
	}
	
	//works, but when allowed to multiJump, if another piece on the same team can Jump, it can jump too on the same turn
	//find way to force the player to only choose the piece that has been selected and can multiJump
	public ArrayList<Piece> pieceHasToJump()
	{
		ArrayList<Piece> pieces = new ArrayList<Piece>();
		for(int r = 0; r < getRows(); r++)
		{
			for(int c = 0; c < getCols(); c++)
			{
				Piece p = get(r,c);
				if(p != null)
				{
					if(p.allCaptures(this).size() > 0 && p.getColor() == Color.RED && isXTurn())
					{
						pieces.add(p);
					}
					else if(p.allCaptures(this).size() > 0 && p.getColor() != Color.RED && !isXTurn())
					{
						pieces.add(p);
					}
				}
			}
		}
		return pieces;
	}
	
	
	public Piece pieceHasToMultiJump()
	{
		for(int r = 0; r < getRows(); r++)
		{
			for(int c = 0; c < getCols(); c++)
			{
				Piece p = get(r, c);
				if(p != null)
				{
					if(p.hasToMultiJump() && p.getColor() == Color.RED && isXTurn()) 
					{
						return p;
					}
					else if(p.hasToMultiJump() && p.getColor() != Color.RED && !isXTurn())
					{
						return p;
					}
				}
			}
		}
		return null;
	}
	
	public boolean xWon()
	{
		for(int r = 0; r < ROWS; r++)
		{
			for(int c = 0; c < COLS; c++)
			{
				if(isOccupied(r,c) && pieceGrid[r][c].getColor() != Color.RED)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public boolean otherWon()
	{
		for(int r = 0; r < ROWS; r++)
		{
			for(int c = 0; c < COLS; c++)
			{
				if(isOccupied(r,c) && pieceGrid[r][c].getColor() == Color.RED)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		frame.setBounds(100,100,500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new BoardPanel(8,8);
		panel.setBackground(Color.BLACK);
		frame.getContentPane().add(panel);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
