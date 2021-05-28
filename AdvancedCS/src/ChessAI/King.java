package ChessAI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public class King extends ChessPiece {

	public King(int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		super(x, y, w, h, tile, name, side, tileLoc, tilePiece, img, score, move);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void canMove(int tile, int userTile, ArrayList<ChessPiece> pieces) { // same can move but checks if the king is moving one tile up/down/left/right or moving one tile diagonally
		// TODO Auto-generated method stub
		
		String[] uLoc = tileLoc.get(userTile).split(",");
		String[] tLoc = tileLoc.get(tile).split(",");
		
		int x1 = Integer.parseInt(uLoc[1]);
		int x2 = Integer.parseInt(tLoc[1]);
		int y1 = Integer.parseInt(uLoc[0]);
		int y2 = Integer.parseInt(tLoc[0]);
		
		int r = Math.abs(x2-x1);
		int c = Math.abs(y2-y1);
		
		if ((r==70&&c==0) || (r==0&&c==70) || (r==70&&c==70)) {
		
	/*	if ((Math.abs(userTile - tile) == 7 || Math.abs(userTile - tile) == 9 ||
				Math.abs(userTile - tile) == 8 || Math.abs(userTile - tile) == 1)) {*/
			
			int count1 = 0;
			int count2 = 0;
			
			int max = Math.max(userTile, tile);
			int min = Math.min(userTile, tile);
			
			for (int i = min+1; i < max; i++) { // if there is a piece in its way
				
				if ((max-min) == 7) {
					
					if (Math.abs(i-max) == 7) {
						
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) {
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--;
									
								}
							}
								
						}
						
					}
					
				} else if ((max-min) == 8) { // if there is a piece in its way
					
					if (Math.abs(i-max) == 8) {
						
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) {
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--;
									
								}
							}
								
						}
						
					}
					
				} else if ((max-min) == 9) { // if there is a piece in its way
					
					if (Math.abs(i-max) == 9) {
						
						for (int z = 0; z < pieces.size(); z++) {
							if ((tilePiece.get(i) != null)) {
								if (pieces.get(z).tile == i) {
									if (pieces.get(z).side == ChessGame.side || pieces.get(z).side != ChessGame.side)
										count2--;
									
								}
							}
								
						}
						
					}
					
				} else if ((max-min) == 1) { // if there is a piece in its way
					
					if (Math.abs(i-max) == 1) {
						
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
			
			if (count2 == count1) { // if there were no pieces in its way than move is true
				
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
	public void move (int userTile) { // moves the king based on the x y of chosen tile
		
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
