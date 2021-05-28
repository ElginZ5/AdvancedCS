package ChessAI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ChessGame {

	final int ROWS = 8, COLS = 8, WIDTH = 560, HEIGHT = 660, MARGIN = 30, PIECE_SIZE = 70, MAX_DEPTH = 6;
	static boolean side = false; // the side of the chessboard (false is black, true is white)
	Color boardC1 = new Color(120, 143, 97), boardC2 = new Color(244, 245, 223); // new colors for the tiles of the board
	int currentTurn = -1; // 1 is white -1 is black
	int compCT = 1; // computer turn (for the calculating best move algorithm)
	boolean isComputer = true; // true if playing with a computer false if playing with two players (computer is always white)
	String bestMove = ""; // best move
	boolean whiteAlive = true; // if the white king is alive
	boolean blackAlive = true; // if the black king is alive

	HashMap<Integer, String> tileLoc = new HashMap<Integer, String>(); // the location of each tile (64 tiles) (takes in a tile and returns the x and y)
	HashMap<String, Integer> locTile = new HashMap<String, Integer>(); // the location to each tile (takes in the x and y and returns the tile)
	HashMap<Integer, String> tilePiece = new HashMap<Integer, String>(); // the current chess piece on the tile (takes in a tile and returns the chess piece)
	
	ArrayList<ChessPiece> pieces = new ArrayList<ChessPiece>(); // the arraylist containing all the pieces on the board
	ArrayList<Integer> userMoveW = new ArrayList<Integer>(); // the move for the white user
	ArrayList<Integer> userTileW = new ArrayList<Integer>(); // the tile for the white user
	ArrayList<Integer> userMoveB = new ArrayList<Integer>(); // the move for the black user
	ArrayList<Integer> userTileB = new ArrayList<Integer>(); // the tile for the black user
	
	//images of the chess pieces
	BufferedImage pawnWhite;
	BufferedImage pawnBlack;
	BufferedImage bishopWhite;
	BufferedImage bishopBlack;
	BufferedImage castleWhite;
	BufferedImage castleBlack;
	BufferedImage knightWhite;
	BufferedImage knightBlack;
	BufferedImage kingWhite;
	BufferedImage kingBlack;
	BufferedImage queenWhite;
	BufferedImage queenBlack;
	
	boolean once = true; // boolean to only setup the board once, not any more times
	
	public ChessGame () throws IOException {
		
		// sets up the images
		pawnWhite = ImageIO.read(new File("images//PawnWhite.png"));
		pawnBlack = ImageIO.read(new File("images//PawnBlack.png"));
		bishopWhite = ImageIO.read(new File("images//BishopWhite.png"));
		bishopBlack = ImageIO.read(new File("images//BishopBlack.png"));
		castleWhite = ImageIO.read(new File("images//CastleWhite.png"));
		castleBlack = ImageIO.read(new File("images//CastleBlack.png"));
		knightWhite = ImageIO.read(new File("images//KnightWhite.png"));
		knightBlack = ImageIO.read(new File("images//KnightBlack.png"));
		kingWhite = ImageIO.read(new File("images//KingWhite.png"));
		kingBlack = ImageIO.read(new File("images//KingBlack.png"));
		queenWhite = ImageIO.read(new File("images//QueenWhite.png"));
		queenBlack = ImageIO.read(new File("images//QueenBlack.png"));
		
		int[][] chessBoard = new int[COLS][ROWS]; // 2d array chessBoard
		
		JFrame frame = new JFrame("Chess");

		JPanel panel = new JPanel();
		JPanel innerPanel = new JPanel();
		JPanel bottomPanel = new JPanel();
		
		JButton newGame = new JButton("New Game"); // new game button
		newGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) { // resets the board
				// TODO Auto-generated method stub
				//System.out.println("Yay");
				blackAlive = true;
				whiteAlive = true;
				pieces = new ArrayList<ChessPiece>();
				tilePiece = new HashMap<Integer,String>();
				once = true;
				frame.getContentPane().repaint();
				
			}
			
		});
		
		JPanel drawPanel = new JPanel() {
			
			public void paint(Graphics g) {
				
				super.paint(g);
				Graphics2D g2d = (Graphics2D) g;
				
				if (blackAlive == false) { // if the black King is dead
					
					g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
					g2d.setColor(Color.black);
					g2d.drawString("WHITE WINS", 133, 270); // displays white wins
					
				} else if (whiteAlive == false) { // if the white king is dead
					
					g2d.setFont(new Font("TimesRoman", Font.PLAIN, 50));
					g2d.setColor(Color.black);
					g2d.drawString("BLACK WINS", 133, 270); // displays black wins
					
				} else {
					// if white and black kings are still alive
					drawBoard(chessBoard, g2d); // draw board
					setupBoard(chessBoard); // setup board
					drawPieces(chessBoard); // draws each piece
					
					try {
						if (isComputer && currentTurn == 1 && side == true) { // if the computer is on and the turn is white and the side is white
							computerMove(); // computer moves
							changeTurn(); // changes turn back to black
							frame.getContentPane().repaint(); // repaint
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					for (int i = 0; i < pieces.size(); i++) {
					
						pieces.get(i).draw(g2d); // draws each piece in the arraylist pieces
					
					}
					
				}
				
			}
			
		};
		
		drawPanel.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) { // mouse clicked
				// TODO Auto-generated method stub
			if (whiteAlive && blackAlive) { // if none of the kings are dead
				if (!isComputer) { // if there are two players
					
					if (currentTurn == 1) { // if the current turn is white
					
						if (userMoveW.size() == 0) { // if the user hasnt clicked on a piece yet
						
							int xLoc = e.getX(); // get the location of user click 
							int yLoc = e.getY();
					
							xLoc = getXLoc(xLoc);
							yLoc = getYLoc(yLoc);
					
							//System.out.println(""+xLoc);
							//System.out.println(xLoc+" "+yLoc);
							int tile = locTile.get(""+xLoc+","+yLoc); // gets the tile based on the location
							//System.out.println(tile);
							String piece = tilePiece.get(tile); // gets the piece based on the tile
					 
							if (piece != null) { // if the user actually clicked on a tile with a piece on it 
						
								for (int i = 0; i < pieces.size(); i++) { // goes through each piece
								
									if (pieces.get(i).tile == tile) { 
										if (pieces.get(i).side==side) { // if the user clicked on a white piece
											userMoveW.add(tile); //add the piece and tile to the arrayLists
											userTileW.add(tile);
										
										}
									}
								
								}
							
							} 
					
							frame.getContentPane().repaint();
					
						} else if (userMoveW.size() == 1) { // if the user has already clicked on a valid piece
					
							int xLoc = e.getX();
							int yLoc = e.getY();
					
							xLoc = getXLoc(xLoc);
							yLoc = getYLoc(yLoc);
						
							int tile = locTile.get(""+xLoc+","+yLoc); // gets the tile and piece based on where the user clicked
							String piece = tilePiece.get(tile);
					//System.out.println(piece);
					 
							if (piece == null) { // if there isnt a piece on the tile the user clicked on
						
								userMoveW.add(tile); // add it the the moves and tiles
								userTileW.add(tile); 
						
								for (int i = 0; i < pieces.size(); i++) {
								
									if (pieces.get(i).tile == userTileW.get(0)) { // if the piece in pieces is the first piece the user clicked on
									
										pieces.get(i).canMove(userTileW.get(0), userTileW.get(1), pieces); // checks if the piece can move to the tile the user clicked on
										//System.out.println(pieces.get(i).move);
									
										if (pieces.get(i).move) { // if it can
									
											//System.out.println("yes");
											pieces.get(i).move(userTileW.get(1)); // move the piece to the tile the user clicked on
											tilePiece.remove(userTileW.get(0)); // remove the piece from tilepiece
											tilePiece.put(userTileW.get(1), pieces.get(i).name); // add the piece and its new tile to tile piece
											pieces.get(i).tile = userTileW.get(1); // update the tile of the piece
											frame.getContentPane().repaint();
											changeTurn(); // changes turn
											//System.out.println("SIDE:"+side);
									
										} else {
									
											userMoveW.clear(); // clears both moves and tiles
											userTileW.clear();
									
										}
									
										break;
									
									}
									
								}
						
								frame.getContentPane().repaint();
						
						
							} else { // if the tile the user clicks on has a piece on it
						
								boolean kingDead = false; // boolean to check if the piece is the opposing king
								boolean done = false; // boolean done
								Object obj = null; // new object obj
								userMoveW.add(tile); // adds the move/tile
								userTileW.add(tile);
									
								for (int j = 0; j < pieces.size(); j++) {
							
									if (pieces.get(j).tile == userTileW.get(1) && pieces.get(j).side!=side) { // if the piece the user clicked on is not the same side as the user
										
										if (pieces.get(j).name.equals("King")) // check if the piece is the enemy king
											kingDead = true;
										
										obj = pieces.get(j); // obj is equal to the piece the user clicked on
										done = true; // set done to true
										pieces.remove(j); // remove the piece the user clicked on
										tilePiece.remove(userTileW.get(1)); // remove the tile of the piece the user clicked on
								
									}
							
								}
						
								for (int i = 0; i < pieces.size(); i++) {
								
									if (pieces.get(i).tile == userTileW.get(0)) { 
								
										pieces.get(i).canMove(userTileW.get(0), userTileW.get(1), pieces); // uses canMove to check if the piece can move to the tile the user clicked on
								
										if (pieces.get(i).move && done) { // if it can move there and the piece has already been removed
											
											if (kingDead) // if the piece was the king
												blackAlive =false; // set blackAlive to false to show that the black king is dead
											
											pieces.get(i).move(userTileW.get(1)); //moves the piece
											tilePiece.remove(userTileW.get(0)); // removes the tile
											tilePiece.put(userTileW.get(1), pieces.get(i).name); // updates the tile of the piece
											pieces.get(i).tile = userTileW.get(1); // updates the tile of the piece
											changeTurn();// changes turn
											//System.out.println("SIDE:"+side);
											
										} else if (pieces.get(i).move && pieces.get(i).side != side) { // if the piece was not removed and the piece is not the same side
									
											pieces.get(i).move(userTileW.get(1)); // moves the piece
											tilePiece.remove(userTileW.get(0)); // removes the tile
											tilePiece.put(userTileW.get(1), pieces.get(i).name); // updates the tile
											pieces.get(i).tile = userTileW.get(1); // updates the tile
											frame.getContentPane().repaint();
											changeTurn(); //change turn
											//System.out.println("SIDE:"+side);
									
										} else { // else
									
											if (done) { // if done is true 
										
												pieces.add((ChessPiece) obj); // add the piece(obj) back because the piece could not move ther
												done = false; // set done to false and obj to null
												obj = null;
										
											}	
										
											userMoveW.clear(); // clears userMoveW and userTileW
											userTileW.clear();
									
										}	
									
										break;
										
									}
									
								}
						
						
							}
					
							// clears userMoveW and userTileW
							userMoveW.clear();
							userTileW.clear();
							frame.getContentPane().repaint();
					
						
						}
					
					} 
				}
				
				if (currentTurn == -1){ // if the turn is for the black pieces
					// same process as the white pieces
					if (userMoveB.size() == 0) {
						
						int xLoc = e.getX();
						int yLoc = e.getY();
						
						xLoc = getXLoc(xLoc);
						yLoc = getYLoc(yLoc);
						
						//System.out.println(""+xLoc);
						//System.out.println(xLoc+" "+yLoc);
						int tile = locTile.get(""+xLoc+","+yLoc);
						//System.out.println(tile);
						String piece = tilePiece.get(tile);
						 
						if (piece != null) {
							
							for (int i = 0; i < pieces.size(); i++) {
								
								if (pieces.get(i).tile == tile) {
									if (pieces.get(i).side==side) {
										userMoveB.add(tile);
										userTileB.add(tile);
									}
								}
								
							}
						} 
						
						frame.getContentPane().repaint();
						
					} else if (userMoveB.size() == 1) {
						
						int xLoc = e.getX();
						int yLoc = e.getY();
						
						xLoc = getXLoc(xLoc);
						yLoc = getYLoc(yLoc);
						
						int tile = locTile.get(""+xLoc+","+yLoc);
						String piece = tilePiece.get(tile);
						//System.out.println(piece);
						 
						if (piece == null) {
							
							userMoveB.add(tile);
							userTileB.add(tile);
							
											
							for (int i = 0; i < pieces.size(); i++) {
									
								if (pieces.get(i).tile == userTileB.get(0)) {
										
									pieces.get(i).canMove(userTileB.get(0), userTileB.get(1), pieces);
									//System.out.println(pieces.get(i).move);
									
									if (pieces.get(i).move) {
									
										//System.out.println("yes");
										pieces.get(i).move(userTileB.get(1));
										tilePiece.remove(userTileB.get(0));
										tilePiece.put(userTileB.get(1), pieces.get(i).name);
										pieces.get(i).tile = userTileB.get(1);
										changeTurn();
										//System.out.println("SIDE:"+side);
										
									} else {
										
										userMoveB.clear();
										userTileB.clear();
										
									}
										
									break;
										
								}
										
							}
							
							frame.getContentPane().repaint();
							
							
						} else {
							
							boolean kingDead = false;
							boolean done = false;
							Object obj = null;
							userMoveB.add(tile);
							userTileB.add(tile);
							
							String piece1 = tilePiece.get(userTileB.get(0));
							
							for (int j = 0; j < pieces.size(); j++) {
								
								if (pieces.get(j).tile == userTileB.get(1) && pieces.get(j).side!=side) {
								
									//System.out.println("piece"+pieces.get(j).side);
									//System.out.println(side);
									if (pieces.get(j).name.equals("King"))
										kingDead = true;
									obj = pieces.get(j);
									done = true;
									pieces.remove(obj);
									tilePiece.remove(userTileB.get(1));
									//System.out.println("removed");
									
								}
								
							}
							
							for (int i = 0; i < pieces.size(); i++) {
									
								if (pieces.get(i).tile == userTileB.get(0)) {
									
									pieces.get(i).canMove(userTileB.get(0), userTileB.get(1), pieces);
									
									if (pieces.get(i).move && done) {
										
										if (kingDead)
											whiteAlive = false;
										//System.out.println("yes");
										pieces.get(i).move(userTileB.get(1));
										tilePiece.remove(userTileB.get(0));
										tilePiece.put(userTileB.get(1), pieces.get(i).name);
										pieces.get(i).tile = userTileB.get(1);
										changeTurn();
										//System.out.println("SIDE:"+side);
										
									} else if (pieces.get(i).move && pieces.get(i).side != side) {
										
										//System.out.println("yes");
										pieces.get(i).move(userTileB.get(1));
										tilePiece.remove(userTileB.get(0));
										tilePiece.put(userTileB.get(1), pieces.get(i).name);
										pieces.get(i).tile = userTileB.get(1);
										changeTurn();
										//System.out.println("SIDE:"+side);
										
									} else {
									 	
										if (done) {
											
											pieces.add((ChessPiece) obj);
											done = false;
											obj = null;
											
										}	
										
										userMoveB.clear();
										userTileB.clear();
										
									}
										
									break;
										
								}
										
							}
							
							
						}
						
						userMoveB.clear();
						userTileB.clear();
						frame.getContentPane().repaint();
						
					}
					
					
					
				}
				
				frame.getContentPane().repaint();
				
			}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		// setup panels and frame
		
		drawPanel.setPreferredSize(new Dimension(WIDTH, 630));
		//innerPanel.setPreferredSize(new Dimension(WIDTH, 630));
		
		innerPanel.add(drawPanel);
		panel.add(newGame);
		panel.add(innerPanel);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		frame.getContentPane().repaint();
		
	}
	
	public void drawBoard (int[][] chessBoard, Graphics2D g2d) { // draw board
		
		// draw board draws every other tile green / beige
		
		for (int r = 0; r < ROWS; r++) {
			
			for (int c = 0; c < COLS; c++) {
				
				if ((r % 2 == 0 && c % 2 == 0)) {
					
					g2d.setColor(boardC1);
					g2d.fillRect(((c*70)), ((r*70)), WIDTH/8, (HEIGHT-MARGIN)/9);
					
				} else if (r % 2 != 0 && c % 2 != 0){
					
					g2d.setColor(boardC1);
					g2d.fillRect(((c*70)), ((r*70)), WIDTH/8, (HEIGHT-MARGIN)/9);
					
				} else {
					
					g2d.setColor(boardC2);
					g2d.fillRect(((c*70)), ((r*70)), WIDTH/8, (HEIGHT-MARGIN)/9);
					
				}
				
				//System.out.println((c*70)+" "+(r*70));
				
			}
			
		}
		
	}
	
	public void setupBoard (int[][] chessBoard) { // setup board
		
		int i = 1;
		
		for (int r = 0; r < ROWS; r++) {
			
			for (int c = 0; c < COLS; c++) {
				
				chessBoard[r][c] = i; // sets it equal to the tile
				
				String loc = ""+c*70+","+r*70;
				tileLoc.put(chessBoard[r][c], loc); // sets up tileLoc and locTile
				locTile.put(loc, chessBoard[r][c]);
				
				i++;
				
			}
			
		}
		
	}
	
	public void drawPieces (int[][] chessBoard) { // draws piece
		
		if (once) { // only if once is true
		
			for (int i = 0; i < 8; i++) { // draws each piece eight times
			
				String[] loc = tileLoc.get(chessBoard[1][i]).split(",");
				//System.out.println(loc[1]);
				pieces.add(new Pawn (Integer.parseInt(loc[0]), Integer.parseInt(loc[1]), 70, 70, 
						chessBoard[1][i], "Pawn", true, tileLoc, tilePiece, pawnWhite, 1, false));
				tilePiece.put(chessBoard[1][i], "Pawn"); // draws the white pawns by creating new pawn object
			
				String[] loc2 = tileLoc.get(chessBoard[6][i]).split(",");
				pieces.add(new Pawn (Integer.parseInt(loc2[0]), Integer.parseInt(loc2[1]), 70, 70, 
						chessBoard[6][i], "Pawn", false, tileLoc, tilePiece, pawnBlack, 1, false));
				tilePiece.put(chessBoard[6][i], "Pawn"); // draws the black pawns by creating new pawn object
				
				if (i == 2) {
					// draws the white bishops by creating new bishop object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Bishop (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Bishop", true, tileLoc, tilePiece, bishopWhite, 3, false));
					tilePiece.put(chessBoard[0][i], "Bishop"); 
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Bishop (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Bishop", false, tileLoc, tilePiece, bishopBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Bishop");
				}
				if (i == 5) {
					// draws the black bishops by creating new bishop object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Bishop (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Bishop", true, tileLoc, tilePiece, bishopWhite, 3, false));
					tilePiece.put(chessBoard[0][i], "Bishop");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Bishop (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Bishop", false, tileLoc, tilePiece, bishopBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Bishop");
				}
				if (i == 1) {
					// draws the white knights by creating new knight object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Knight (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Knight", true, tileLoc, tilePiece, knightWhite, 3, false));
					tilePiece.put(chessBoard[0][i], "Knight");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Knight (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Knight", false, tileLoc, tilePiece, knightBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Knight");
				}
				if (i == 6) {
					// draws the black knights by creating new knight object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Knight (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Knight", true, tileLoc, tilePiece, knightWhite, 3, false));
					tilePiece.put(chessBoard[0][i], "Knight");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Knight (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Knight", false, tileLoc, tilePiece, knightBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Knight");
				}
				if (i == 0) {
					// draws the white castles by creating new castle object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Castle (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Castle", true, tileLoc, tilePiece, castleWhite, 3, false));
					tilePiece.put(chessBoard[0][i], "Castle");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Castle (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Castle", false, tileLoc, tilePiece, castleBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Castle");
				}
				if (i == 7) {
					// draws the black castles by creating new castle object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Castle (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Castle", true, tileLoc, tilePiece, castleWhite, 3,false));
					tilePiece.put(chessBoard[0][i], "Castle");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Castle (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Castle", false, tileLoc, tilePiece, castleBlack, 3, false));
					tilePiece.put(chessBoard[7][i], "Castle");
				}
				if (i == 3) {
					// draws the white and black queens by creating new queen object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new Queen (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "Queen", true, tileLoc, tilePiece, queenWhite, 5, false));
					tilePiece.put(chessBoard[0][i], "Queen");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new Queen (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "Queen", false, tileLoc, tilePiece, queenBlack, 5, false));
					tilePiece.put(chessBoard[7][i], "Queen");
				}
				if (i == 4) {
					// draws the white and black kings by creating new queen object
					String[] locB = tileLoc.get(chessBoard[0][i]).split(",");
					pieces.add(new King (Integer.parseInt(locB[0]), Integer.parseInt(locB[1]), 70, 70, 
							chessBoard[0][i], "King", true, tileLoc, tilePiece, kingWhite, 100, false));
					tilePiece.put(chessBoard[0][i], "King");
					
					String[] locBB = tileLoc.get(chessBoard[7][i]).split(",");
					pieces.add(new King (Integer.parseInt(locBB[0]), Integer.parseInt(locBB[1]), 70, 70, 
							chessBoard[7][i], "King", false, tileLoc, tilePiece, kingBlack, 100, false));
					tilePiece.put(chessBoard[7][i], "King");
				}
			
			}
			
		}
		
		once = false; // sets once to false after completing this process once
		
	}
	
	public int getXLoc (int xLoc) { // sets the XLocation
		
		if (xLoc < 70)
			xLoc = 0;
		else if (xLoc >= 70 && xLoc < 140)
			xLoc = 70;
		else if (xLoc >= 140 && xLoc < 210)
			xLoc = 140;
		else if (xLoc >= 210 && xLoc < 280)
			xLoc = 210;
		else if (xLoc >= 280 && xLoc < 350) 
			xLoc = 280;
		else if (xLoc >= 350 && xLoc < 420)
			xLoc = 350;
		else if (xLoc >= 420 && xLoc < 490)
			xLoc = 420;
		else
			xLoc = 490;
		
		return xLoc;
		
	}
	
	public int getYLoc (int yLoc) { // sets the Ylocation
		
		if (yLoc < 70)
			yLoc = 0;
		else if (yLoc >= 70 && yLoc < 140)
			yLoc = 70;
		else if (yLoc >= 140 && yLoc < 210)
			yLoc = 140;
		else if (yLoc >= 210 && yLoc < 280)
			yLoc = 210;
		else if (yLoc >= 280 && yLoc < 350) 
			yLoc = 280;
		else if (yLoc >= 350 && yLoc < 420)
			yLoc = 350;
		else if (yLoc >= 420 && yLoc < 490)
			yLoc = 420;
		else
			yLoc = 490;
		
		return yLoc;
		
	}
	
	public void changeTurn() { // changes the turn

		if (side) {
			side = false;
		} else {
			side = true;
		}
		currentTurn *= -1;
	}

	public int getScore (ArrayList<ChessPiece> pieces, boolean side) { //scores the board
		
		int totalScore = 0;
		int whiteScore = 0;
		int blackScore = 0;
		
		for (int i = 0; i < pieces.size(); i++) { // as each piece takes in their own score, add up all the scores of each piece for white and black
			
			if (pieces.get(i).side) {
				
				whiteScore += pieces.get(i).score;
				
			} else {
				
				blackScore += pieces.get(i).score;
				
			}
			
		}
		
		if (side) {
			
			return totalScore += whiteScore - blackScore; // returns total score based on the side
			
		} else {
			
			totalScore += blackScore - whiteScore;
			
		}
		
		return totalScore;
		
	}

	public String bestMove (Branch currBranch, ArrayList<ChessPiece> chessBoard, // best move function for computer
			HashMap<Integer, String> tileBoard, int depth) throws IOException {
		
		boolean compSide; // compSide
		
		if (compCT == -1) // compside changes based on compCT
			compSide = false;
		else
			compSide = true;
		
		HashMap<ChessPiece, Integer> pieceTile = new HashMap<ChessPiece, Integer>(); // new hashmap to take in the piece and their tile for the comp
		
		int maxScore = Integer.MIN_VALUE; // max score
		
		Branch head = new Branch (chessBoard, tileBoard, compCT, Integer.MAX_VALUE, null); // new head branch
		
		ArrayList<String> possibleMoves = possibleMoves(chessBoard, tileBoard, pieceTile, compSide); // gets possible moves using the possible moves method
		
		for (String pm : possibleMoves) { // for each possible move
			
			if (pm == null)
				return bestMove;
			
			HashMap<Integer, String> newTileBoard = new HashMap<Integer,String>();
			ArrayList<ChessPiece> nextBoard = new ArrayList<ChessPiece>();
			
			newTileBoard = copyTiles(tileBoard, newTileBoard); // nextBoard is set as a copy of the current board and same with newTileBoard except with the current tile board
			nextBoard = copyBoard(chessBoard, nextBoard);
			
			performMove(nextBoard, newTileBoard, pieceTile, pm); // performs the move with the copied board
			
			Branch child = new Branch (nextBoard, newTileBoard, compCT, 0, head); // new child branch
			
			head.addChild(child, head); // adds the child to head
			
			//System.out.println("DEPTH:"+depth);
			getBestMove(child, nextBoard, newTileBoard, depth); // gets best move
			
			if (child.parent.score > maxScore) { // if the score of the parent of the child is greater than the max score 
				
				bestMove = pm;  // set best move to the possible move
				maxScore = child.parent.score; // sets maxScore to the score of the parent
				//System.out.println("picked: "+child.score);
				
			}
			
		}
		
		return bestMove; // returns the best move
		
	}
	
	public void getBestMove (Branch currBranch, ArrayList<ChessPiece> chessBoard, // getBestMove
			HashMap<Integer, String> tileBoard, int depth) throws IOException {
		
		int score = getScore(chessBoard, true); // scores the current board
		System.out.println(depth+":"+score);
		
		if (depth == MAX_DEPTH) { // if we have reached the max depth
			
			if (depth % 2 != 0 && score > currBranch.parent.score) { // set the parent score based on if its the computer or human
				
				currBranch.parent.score = score;
				
			} else if (depth % 2 == 0 && score < currBranch.parent.score) {
				
				currBranch.parent.score = score;
				
			}
			
			return;
			
		} 
		
		if (currBranch.score < currBranch.parent.score && depth % 2 == 0) { // if depth is not max yet
			//changes the parent score based on if human or computer
			
			currBranch.parent.score = score;
			//System.out.println("parentScore"+currBranch.parent.score);
			
		} else if (currBranch.score > currBranch.parent.score && depth % 2 != 0) {
			
			currBranch.parent.score = score;
			//System.out.println("parentScore"+currBranch.parent.score);
				
		}
		
		depth++; // depth ++
		compCT*=-1; // changes turns
		System.out.println("childScore:"+currBranch.score);
		
		getBestMove(currBranch, chessBoard, tileBoard, depth); // recursion
		
	
	}
	

	private ArrayList<String> possibleMoves(ArrayList<ChessPiece> board, HashMap<Integer, String> tilePiece, 
			HashMap<ChessPiece, Integer> pieceTile, boolean pSide) { // possible moves

		ArrayList<String> possible = new ArrayList<String>(); // stores possible moves in here
		
		for (int z = 0; z < board.size(); z++) { // pieceTiles copies each tile into it
			
			if (board.get(z) != null)
				pieceTile.put(board.get(z), board.get(z).tile);
			
		}
		
		//System.out.println(pieceTile);
		for (int i = 0; i < board.size(); i++) { // goes through the board
			
			if (board.get(i).side == pSide && board.get(i) != null) { // if the piece is the same side as the board.get(i)
				
				for (int t = 1; t <= 64; t++) { // goes through each tile
					
					if (t != pieceTile.get(board.get(i))) { // if the tile is not the tile the piece is on right now
					
						board.get(i).canMove(pieceTile.get(board.get(i)), t, board); // checks if the piece can move to that tile
					
						if (board.get(i).move) { // if it can
							
							possible.add(""+pieceTile.get(board.get(i))+","+t); // adds it to possible moves
							
							if (tilePiece.get(t) != null) { // if there is a piece on that possible tile
								
								for (int j = 0; j < board.size(); j++) {
									
									if (pieceTile.get(board.get(j)) == t) {
										
										if (board.get(j).side == pSide) { // if the piece have the same side
											
											possible.remove(possible.size()-1); // remove that move from possible moves
											
										}
										
									}
									
								}
								
							}
							
						} 
						
					}
					
				}
				
			}
			
		}
		
		//System.out.println("moves:"+possible);
		return possible; 
		
	}
	
	private ArrayList<ChessPiece> copyBoard (ArrayList<ChessPiece> initialBoard, ArrayList<ChessPiece> nextBoard) { // copies the board
		
		for (int i = 0; i < initialBoard.size(); i++) { // goes through the entire board and adds each element to nextboard
			nextBoard.add(initialBoard.get(i));
			//System.out.println("coppied");
		}
		
		return nextBoard;
		
	}
	
	private HashMap<Integer, String> copyTiles (HashMap<Integer, String> initialBoard, HashMap<Integer, String> newTilePiece) { // copies the tile board
		
		for (Integer i : initialBoard.keySet()) { // goes through each element and adds it to new board
			
			newTilePiece.put(i, initialBoard.get(i));
			
		}
		
		return newTilePiece;
		
	}
	
	private void performMove (ArrayList<ChessPiece> chessBoard, HashMap<Integer, String> newTileBoard,
			HashMap<ChessPiece, Integer> pieceTile, String move) { // perform move
		
		String[] moves = move.split(",");
		int userTile = Integer.parseInt(moves[1]); // gets the current piece tile and the possible tile through splitting the give string
		int tile = Integer.parseInt(moves[0]);
		
		for (int z = 0; z < chessBoard.size(); z++) { // copies each tile to peace tile
			
			if (chessBoard.get(z) != null)
				pieceTile.put(chessBoard.get(z), chessBoard.get(z).tile);
			
		}
		
		if (newTileBoard.get(userTile) == null) { // if the possible tile has no piece on it 
				
			for (int i = 0; i < chessBoard.size(); i++) {
				
				if (pieceTile.get(chessBoard.get(i)) == tile) { // performs the move by updating the tile in pieceTile and newTileBoard
					
					pieceTile.remove(chessBoard.get(i));
					pieceTile.put(chessBoard.get(i), tile);
					newTileBoard.remove(tile);
					newTileBoard.put(userTile, chessBoard.get(i).name);
					
				}
				
			}
				
		} else { // if the possible tile has a piece
					
			for (int j = 0; j < chessBoard.size(); j++) {
				
				if (pieceTile.get(chessBoard.get(j)) == userTile) { 
				
					chessBoard.remove(j); // remove that piece on the tile since we already know it is an enemy piece through possibleMoves
					newTileBoard.remove(userTile);
					
				}
				
			}
			
			for (int i = 0; i < chessBoard.size(); i++) { 
				
				if (pieceTile.get(chessBoard.get(i)) == tile) { // moves that piece to the possible tile by updating piece tile and tile board
					
					pieceTile.remove(chessBoard.get(i));
					pieceTile.put(chessBoard.get(i), tile);
					newTileBoard.remove(tile);
					newTileBoard.put(userTile, chessBoard.get(i).name);
					
				}
				
			}
			
		}
		
	}
	
	public void computerMove () throws IOException { // computer move method

		if (isComputer && currentTurn == 1 && side) { // if it is the computer's turn
			
			int depth = 0; // depth
			
			String move = bestMove(null, pieces, tilePiece, depth); // calls best move
			System.out.println(move);
			
			String[] moveTile = move.split(",");
			int userTile = Integer.parseInt(moveTile[1]); // splits the move into the possible tile and current tile
			int tile = Integer.parseInt(moveTile[0]);
			
			if (tilePiece.get(userTile) == null) { // same as the performMove method except actually moving the pieces this time
				
				for (int i = 0; i < pieces.size(); i++) {
					
					if (pieces.get(i).tile == tile) {
						
						pieces.get(i).move(userTile);
						tilePiece.remove(tile);
						tilePiece.put(userTile, pieces.get(i).name);
						pieces.get(i).tile = userTile;
						break;
						
					}
					
				}
				
			} else {
				
				for (int j = 0; j < pieces.size(); j++) {
			
					if (pieces.get(j).tile == userTile) {
						
						if (pieces.get(j).name.equals("King")) // checks if the computer killed the black king
							blackAlive = false;
						pieces.remove(j);
						tilePiece.remove(userTile);
				
					}
			
				}
		
				for (int i = 0; i < pieces.size(); i++) {
			
					if (pieces.get(i).tile == tile) {
				
						pieces.get(i).move(userTile);
						//pieces.remove(i);
						tilePiece.remove(tile);
						tilePiece.put(userTile, pieces.get(i).name);
						pieces.get(i).tile = userTile;
						break;
				
					}
			
				}
		
			}

		}
		
	}
	
}
