package ChessAI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Knight extends ChessPiece {

	public Knight(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // same canMove but instead checks if the knight is moving two rows/tiles up and one col/tile to the left/right
		// TODO Auto-generated method stub
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		
		int x1 = Integer.parseInt(uLoc[1]);
		int x2 = Integer.parseInt(tLoc[1]);
		int y1 = Integer.parseInt(uLoc[0]);
		int y2 = Integer.parseInt(tLoc[0]);
		
		int r = Math.abs(x2-x1);
		int c = Math.abs(y2-y1);
		
		if ((r==140 && c==70) || (r==70&&c==140)) { // if its moving two up/down and one left/right or one up/down and two left/right
			
			int count1 = 0;
			int count2 = 0;
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			for (int i = min+1; i < max; i++) {
				
				String[] lLoc = tileLoc.get(i).split(",");
				
				if ((max-min) % 6 == 0) {
					
					if (Math.abs(i-max) % 6 == 0) { // checks if there are any pieces blocking the knight
						
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
					
				} else if ((max-min) % 17 == 0 ) {
					
					if (Math.abs(i-max) % 17 == 0) {  // checks if there are any pieces blocking the knight
						
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
					
				} else if ((max-min) % 15 == 0) {
					
					if (Math.abs(i-max) % 15 == 0) { // checks if there are any pieces blocking the knight
						
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
					
				} else if ((max-min) % 10 == 0) {
					
					if (Math.abs(i-max) % 10 == 0) { // checks if there are any pieces blocking the knight
						
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
			
			if (count2 == count1) { // if there were no pieces blocking the knight
				
				move = true;
				count2 = 0;
				count1 = 0;
			
			} else {
				
				move = false;
				count2 = 0;
				count1 = 0;
				
			}	
						
		} else {
			
			move = false;
		}
		
	}

	@Override
	public void move (int userTile) { // changes the x and y of the knight based on chosen tile
		
		String[] loc = tileLoc.get(userTile).split(",");
		x = Integer.parseInt(loc[0]);
		y = Integer.parseInt(loc[1]);
		tilePiece.put(userTile, "Knight");
		
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(img, x, y, w, h, null);
	}

}
