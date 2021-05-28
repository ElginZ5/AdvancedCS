package ChessAI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class Queen extends ChessPiece {

	public Queen(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}
	
	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // same canMove but checks if queen is moving vertically, horizantally, or diagonally
		// TODO Auto-generated method stub
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		int x1 = Integer.parseInt(uLoc[1]);
		int x2 = Integer.parseInt(tLoc[1]);
		int y1 = Integer.parseInt(uLoc[0]);
		int y2 = Integer.parseInt(tLoc[0]);
		
		if ((uLoc[0].equals(tLoc[0]) || (uLoc[1].equals(tLoc[1])) ||
				((Math.abs(x2-x1) == Math.abs(y2-y1))))) {
					
			int count1 = 0;
			int count2 = 0;
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			String[] maLoc = tileLoc.get(max).split(",");
			String[] miLoc = tileLoc.get(min).split(",");
			
			for (int i = min+1; i < max; i++) {
				
				String[] lLoc = tileLoc.get(i).split(",");
				
				if ((max-min) % 8 == 0) { // checks if there are any pieces in its vertical path
					
					if (Math.abs(i-max) % 8 == 0) {
						
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
					
				} else if ((max-min) % 7 == 0 ) { // checks if there are any pieces in its diagonal path
					
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
					 
				} else if ((max-min) % 9 == 0) { // checks if there are any pieces in its diagonal path
					
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
					
				} else if (maLoc[1].equals(miLoc[1])) { // checks if there are any pieces in its horizontal path
					
					if (lLoc[1].equals(maLoc[1])) {
						
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
			
			if (count2 == count1) { // if there were no pieces blocking the way 
			
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
	public void move (int userTile) { // moves by changing x and y based on the chosen tile 
		
		String[] loc = tileLoc.get(userTile).split(",");
		x = Integer.parseInt(loc[0]);
		y = Integer.parseInt(loc[1]);
		tilePiece.put(userTile, "Queen");
		
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(img, x, y, w, h, null);
	}

}
