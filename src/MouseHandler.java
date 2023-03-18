import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener
{
	private GuiLocation[][] locs;
	private CheckersGame game;
	private Piece pickedPiece;
	private BoardPanel panel;
	
	public MouseHandler(GuiLocation[][] locs, CheckersGame game, BoardPanel panel)
	{
		this.panel = panel;
		this.game = game;
		this.locs = locs; 
		pickedPiece = null;
	}
	
	public int[] validMouseClick(int x, int y)
	{
		for(int r = 0; r < locs.length; r++)
		{
			for(int c = 0; c < locs[0].length; c++)
			{
				if(locs[r][c].contains(x,y))
				{
					return new int[] {r,c};
				}
			}
		}
		
		return null;
	}
	
	public void mousePressed(MouseEvent e)
	{
		int x = e.getX();
		int y = e.getY();
		

		if(validMouseClick(x,y) != null)
		{
			int r = validMouseClick(x,y)[0];
			int c = validMouseClick(x,y)[1];
			if(game.get(r,c) != null)
			{ 
				if(pickedPiece == null || (!pickedPiece.isSelected() && pickedPiece != null))
				{
					if(game.pieceHasToJump().size() > 0)
					{
						if(game.pieceHasToMultiJump() != null)
						{
							if(game.pieceHasToMultiJump().getRow() == r && game.pieceHasToMultiJump().getCol() == c)
							{
								pickedPiece = game.pieceHasToMultiJump(); //prevents other pieces from jumping during multiJump
								pickedPiece.setSelected();
							}
						}
						else
						{
							for(int k = 0; k < game.pieceHasToJump().size(); k++)
							{
								if(game.pieceHasToJump().get(k).getRow() == r && game.pieceHasToJump().get(k).getCol() == c)
								{
									pickedPiece = game.pieceHasToJump().get(k); //player is forced to multiJump
									pickedPiece.setSelected();
									break;
								}
							}		
						}
					}
					else
					{
						pickedPiece = game.get(r,c);
						pickedPiece.setSelected();
					}
				}
				else if(pickedPiece != null)
				{
					pickedPiece.setSelected();
				}
			}
			else //click on null location
			{
				if(pickedPiece == null)
				{
					return;
				}
				if(pickedPiece.isSelected())
				{
					pickedPiece.move(game, r, c);
					pickedPiece.setSelected();
					pickedPiece = null;
				}		
			}
			panel.repaint();
			return;
		}
	}
	public void mouseClicked(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
}


