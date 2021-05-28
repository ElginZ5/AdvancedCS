package ChessAI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Bishop extends ChessPiece {

	public Bishop(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // same with the pawn move method except checking in the diagonal direction
		// TODO Auto-generated method stub
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		int x1 = Integer.parseInt(uLoc[1]);
		int x2 = Integer.parseInt(tLoc[1]);
		int y1 = Integer.parseInt(uLoc[0]);
		int y2 = Integer.parseInt(tLoc[0]);
		
		if ((!(uLoc[1].equals(tLoc[1])) && !(uLoc[0].equals(tLoc[0]))) &&
				((Math.abs(x2-x1) == Math.abs(y2-y1)))) {
			
			System.out.println("MOVED");
			int count1 = 0;
			int count2 = 0;
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			for (int i = min+1; i < max; i++) {
				
				if ((max-min) % 7 == 0) { // checks if there are any pieces blocking the way diagonally
					
					if (Math.abs(i-max) % 7 == 0) {
						
						System.out.println(i+": "+tilePiece.get(i));;
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) {
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--;
									
								}
							}
								
						}
						
					}
					
				} else if ((max-min) % 9 == 0) { // checks if there are any pieces blocking the way diagonally
					
					if (Math.abs(i-max) % 9 == 0) {
						
						System.out.println(i+": "+tilePiece.get(i));;
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) {
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--;
									
								}
							}
								
						}
					}
					
				}
				
				count2++;
				count1++;
				
			}
			
			if (count2 == count1) { // if there weren't any pieces blocking the way then set move to true
			
				move = true;
				
				count2 = 0;
				count1 = 0;
			
			} else {
				
				move = false;
				count2 = 0;
				count1 = 0;
				
			}
			
		}else {
			move=false;
		}
		
	}
	
	public void move (int userTile) { // moves by changing the xy 
		
		String[] loc = tileLoc.get(userTile).split(",");
		x = Integer.parseInt(loc[0]);
		y = Integer.parseInt(loc[1]);
		tilePiece.put(userTile, "Bishop");
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(img, x, y, w, h, null);
		
	}

}
