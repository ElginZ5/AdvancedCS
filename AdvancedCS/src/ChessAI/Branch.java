package ChessAI;

import java.util.ArrayList;
import java.util.HashMap;

public class Branch {
	
	public ArrayList<ChessPiece> pieces;
	public HashMap<Integer, String> tilePiece;
	public Branch parent;
	public int score, currentTurn;
	
	public Branch (ArrayList<ChessPiece> pieces, HashMap<Integer, String> tilePiece, int currentTurn, int score, Branch parent) {
		
		this.pieces = pieces;
		this.tilePiece = tilePiece;
		this.currentTurn = currentTurn;
		this.score = score;
		this.parent = parent;
		
	}
	
	public void addChild (Branch child, Branch parent) {
		
		child.parent = parent;
		
	}

}
