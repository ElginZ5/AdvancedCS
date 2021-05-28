package ChessAI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Castle extends ChessPiece {

	public Castle(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // same as pawn canMove method except checks if the castle is either moving horizantally or vertically
		// TODO Auto-generated method stub
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		
		if ((uLoc[0].equals(tLoc[0])) || // if the x value stays the same from current tile to chosen tile (horizontally)
				(uLoc[1].equals(tLoc[1]))) { // if the y value stays the same from current tile to chosen tile (vertically)
			
			int count1 = 0;
			int count2 = 0;
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			String[] maLoc = tileLoc.get(max).split(",");
			String[] miLoc = tileLoc.get(min).split(",");
			
			for (int i = min+1; i < max; i++) {
				
				String[] lLoc = tileLoc.get(i).split(",");
				
				if (maLoc[0].equals(miLoc[0])) { // checks if there are any pieces in its horizontal path
					
					if (lLoc[0].equals(miLoc[0])) {
						
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
					
				} else if (maLoc[1].equals(miLoc[1])) {
					
					if (lLoc[1].equals(maLoc[1])) { // checks if there are any pieces in its vertical path
						
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
			
			if (count2 == count1) { // if there were no pieces blocking the way move is true
			
				move = true;
			
				count2 = 0;
				count1 = 0;
			
			} else {
				
				move = false;
				count2 = 0;
				count1 = 0;
				
			}
			
		}else {
			move = false;
		}
		
	}
	
	public void move (int userTile) { // moves it based on the x and y of the chosen tile 
		
		String[] loc = tileLoc.get(userTile).split(",");
		x = Integer.parseInt(loc[0]);
		y = Integer.parseInt(loc[1]);
		tilePiece.put(userTile, "Castle");
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(img, x, y, w, h, null);
	}

}
