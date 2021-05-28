package ChessAI;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class ChessPiece { // abstract parent class ChessPiece

	int x, y, w, h, tile;
	String name;
	boolean side;
	HashMap<Integer, String> tileLoc;
	HashMap<Integer, String> tilePiece;
	BufferedImage img;
	int score;
	boolean move;
	
	public ChessPiece (int x, int y, int w, int h, int tile, String name, boolean side, 
			HashMap tileLoc, HashMap tilePiece, BufferedImage img, int score, boolean move) {
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.tile = tile;
		this.name = name;
		this.side = side;
		this.tileLoc = tileLoc;
		this.tilePiece = tilePiece;
		this.img = img;
		this.score = score;
		this.move= move;
		
	}
	
	public abstract void canMove (int tile, int userTile, ArrayList<ChessPiece> pieces);
	public abstract void move(int userTile);
	public abstract void draw (Graphics2D g2d);
	
}
