package ChessAI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Pawn extends ChessPiece {
	
	public Pawn(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // canMove method that checks if the piece can move
		// TODO Auto-generated method stub
		
		int count1 = 0;
		int count2 = 0;
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		
		int x1 = Integer.parseInt(uLoc[1]);
		int x2 = Integer.parseInt(tLoc[1]);
		int y1 = Integer.parseInt(uLoc[0]);
		int y2 = Integer.parseInt(tLoc[0]);
		
		int r = Math.abs(x2-x1);
		int c = Math.abs(y2-y1);
		
		if ((r==70 && c==0)) { // if the piece is moving forward one tile (the only action it can take)
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			for (int i = min+1; i < max; i++) {
				
				if ((max-min) == 8) {
					
					if (Math.abs(i-max) - 8 == 0) { // goes infront of the pawn
						
						System.out.println(i+": "+tilePiece.get(i));;
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) { // if there are pieces in the path of the pawn 
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--; // minus one from count
									
								}
							}
								
						}
						
					}
					
				}
				
				count2++; // adds count 2 and count 1 
				count1++;
				
			}
			
			if (count2 == count1) { // if count2 is count1 (there were no pieces in the path of the pawn
			
				if (side == true) { // if its the white side
					
					if (userTile > tile) // makes sure the pawn can only move forward based on the side
						move = true;
					else
						move = false;
					
				} else {
					
					if (userTile < tile)
						move = true;
					else
						move = false;
					
				}
				
				count2 = 0;
				count1 = 0;
				
			} else { // if there was a piece blocking the pawn
				
				move = false; // return move as false
				count2 = 0;
				count1 = 0;
				
			}
			
		} else {
			
			move = false;
			
		}
		
	}
	
	@Override
	public void move (int userTile) { // moves the pawn
		// TODO Auto-generated method stub
		
		// changes the x and y to move the pawn
		String[] loc = tileLoc.get(userTile).split(","); // gets the x y of the tile the user/computer chose to move to using tileLoc
		System.out.println(x+" : "+y);
		x = Integer.parseInt(loc[0]);
		y = Integer.parseInt(loc[1]);
		//System.out.println(x+" : "+y);

	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
		g2d.drawImage(img, x, y, w, h, null); // draws the pawn image
//		/g2d.drawOval(70, 70, 140, 140);
		
	}

	

}
