package djikstras;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import graphs.LocationGraph.Edge;

public class GPS <E, T> {
	
	boolean hover; // boolean hover
	int xLoc, yLoc; // x loc and y loc
	
	ArrayList<Object> vertexPath; // path
	
	public GPS () throws IOException {
		
		LocationGraph<String, String> g = new LocationGraph<String, String>(); // new location graph
	
		BufferedImage map = ImageIO.read(new File ("pokemonmap.jpg")); // the map
		
		ArrayList<String> vertexInfo = new ArrayList<String>(); // arraylist vertex info
		vertexPath = new ArrayList<Object>(); // initializes vertex path
		
		final int WIDTH = 1200, HEIGHT = 625; // width and height of the window
		
		hover = false; // set hover to false
		xLoc = 0; // initializes x and y loc
		yLoc = 0;
		
		Font myFont = new Font("Arial", Font.BOLD, 12); // two fonts
		Font font = new Font("Helvetica", Font.BOLD, 30);
		
		Color myColor = new Color (45, 183, 252, 160); // sets color to be a little transparent
		
		JFrame frame = new JFrame("GPS Pokemon Map"); // new JFrame
		
		JPanel panel = new JPanel(); // new JPanels
		JPanel innerPanel = new JPanel();
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		JButton refreshButton = new JButton("Refresh"); // refresh button
		
		JTextArea displayArea = new JTextArea(); // display area 
		displayArea.setEditable(false);
		displayArea.setFont(myFont);
		displayArea.setBackground(null);
		displayArea.setText("Hover your mouse over a city to see its name. "
				+ "Click on two cities to see the shortest path between them."
				+ " Press this button to find the shortest path for two other cities:"); 
		
		refreshButton.addActionListener(new ActionListener() { // refresh button

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				vertexPath.clear(); // clears vertex path and vertex info and also clears x and y loc 
				vertexInfo.clear();
				xLoc = 0;
				yLoc = 0;
				frame.getContentPane().repaint();
				
			}
			
		});
		
		JPanel drawPanel = new JPanel() { // drawpanel
			
			public void paint (Graphics g2d) {
				
				super.paint(g2d);
				
				Graphics2D g2 = (Graphics2D) g2d;
				
				g2d.drawImage(map, 0, 0, 1200-12, 600-34, this); // draws the map
				
				for (String s : g.vertices.keySet()) { // for each vertex
					
					int x = g.vertices.get(s).x; // gets y and x location
					int y = g.vertices.get(s).y;
					g2d.setColor(Color.cyan);
					g2d.fillOval(x, y, 30, 30); // draws the circle
					
				}
				
				for (String s : g.vertices.keySet()) { // for each vertex
					
					for (LocationGraph<String, String>.Edge ee : g.vertices.get(s).edges) { // for each edge of each vertex
						
						LocationGraph<String, String>.Vertex v = g.vertices.get(s); // gets the vertex
						LocationGraph<String, String>.Vertex neighbor = ee.getNeighbor(v); // gets the neighbors of the edges of the vertex
						
							
						g2.setColor(Color.cyan);
						g2.setStroke(new BasicStroke(8));
						g2.drawLine(v.x+14, v.y+14, neighbor.x+14, neighbor.y+14); // draws a connecting line between each connected vertex
							
			
						
					}
					
				}
				
				if (vertexPath != null) { // if vertex path is not null
					
					for (int i = 0; i < vertexPath.size(); i+=2) { // goes through the vertex path
						
						int x = g.vertices.get(vertexPath.get(i)).x; // gets the x and y location of the vertex
						int y = g.vertices.get(vertexPath.get(i)).y;
						g2d.setColor(Color.red); // set it to red
						g2d.fillOval(x, y, 30, 30); // draw it over the blue circle
						
					}
					
					for (int i = 0; i < vertexPath.size(); i+=2) { // for each vertex in vertex path
	
						for (LocationGraph<String, String>.Edge ee : g.vertices.get(vertexPath.get(i)).edges) { // for each edge of each vertex
							
							LocationGraph<String, String>.Vertex v = g.vertices.get(vertexPath.get(i)); // gets the vertex
							LocationGraph<String, String>.Vertex neighbor = ee.getNeighbor(v); // gets the neighbor of the edge of the vertex

							if (vertexPath.contains(neighbor.toString())) { // if vertex path contains the neighbor
								
								g2.setColor(Color.red); // sets it to red
								g2.setStroke(new BasicStroke(8));
								g2.drawLine(v.x+14, v.y+14, neighbor.x+14, neighbor.y+14); // draws the connecting path red over the blue connecting path 
								
								double distance = Math.floor(ee.label); // gets the distance
								g2.setFont(myFont); // font
								
								if (distance > 0) { // makes it so the distance can be easily read
									
									if (Math.abs(v.x-neighbor.x) < 40) { // if the two vertices' x values are around the same (if the connecting line will be vertical)
										
										g2.drawString(distance+"", (((v.x+14)+(neighbor.x+14))/2)-35, // move the text so it can be easily read
											(((v.y+14)+(neighbor.y+14))/2));
									
									} else if (Math.abs(v.y-neighbor.y) < 40) { // if the two vertices' y values are around the same (if the connecting line will be horizontal)
										
										g2.drawString(distance+"", (((v.x+14)+(neighbor.x+14))/2)-15, 
												(((v.y+14)+(neighbor.y+14))/2)-17); // move the text so it can be easily read
										
									} else { // else
										
										g2.drawString(distance+"", (((v.x+14)+(neighbor.x+14))/2)-13, // make it so can be read easier
												(((v.y+14)+(neighbor.y+14))/2)-20);
										
									}
									
								}
								
							}
								
						}
						
					}
					
				}
				
				if (hover) { // if hover is true
					
					for (String s : g.vertices.keySet()) { // for each vertex
					
						int x = g.vertices.get(s).x; // gets the x and y location of the mouse
						int y = g.vertices.get(s).y;
						
						if (isOn (xLoc, yLoc, x, y)) { // if the mouse is on a vertex
							
							g2d.setColor(myColor); // sets the background of a rectangle to be light blue
							g2d.fillRect(0, 0, 260, 100); // draws the rectangle
							g2d.setFont(font); // set font
							g2d.setColor(Color.white); // change color to white
							g2d.drawString(g.vertices.get(s).info.toString(), 7, 60); // displays the name of that vertex
							
						}
						
					}
					
					
				}
				
										
			}
							
			
		};
		
		drawPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		drawPanel.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
				frame.getContentPane().repaint();
				
				int x = e.getX(); // gets the x and y location of user mouse
				int y = e.getY();
				
				for (String s : g.vertices.keySet()) { // for each vertex
					 
					int xLoc = g.vertices.get(s).x; // x and y location of the vertex
					int yLoc = g.vertices.get(s).y;
					
					if (isOn (x, y, xLoc, yLoc)) { // if user clicks on a vertex
						
						vertexInfo.add(g.vertices.get(s).info.toString()); // adds the vertex to vertex info
						//System.out.println(vertexInfo);
						
						if (vertexInfo.size() == 2) { // if the user has clicked on two vertices
							
							vertexPath = g.Djikstra(vertexInfo.get(0), vertexInfo.get(1)); // use djikstras to get the shortest path 
							// between the two vertices and save it to vertexPath
							
							//System.out.println(vertexPath);
							
						} 
						
					}
					
				}
				
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
				hover = false;
				
			}
			
			
			
		});
		
		drawPanel.addMouseMotionListener(new MouseMotionListener () {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) { // detects if mouse is moving
				// TODO Auto-generated method stub
				
				hover = true; // if it is set hover to true
				xLoc = e.getX(); // sets x and y loc to the user x and y position
				yLoc = e.getY();
				//System.out.println("Yes");
				frame.getContentPane().repaint();
				
			}
			
			
		});
		
		innerPanel.add(displayArea);
		innerPanel.add(refreshButton);
		
		panel.add(drawPanel);
		panel.add(innerPanel);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		
		createVertices(g, frame);
		createEdges(g, frame);
		
	}
	
	public void createVertices (LocationGraph<String, String> g, JFrame frame) throws IOException { // creates vertices from text file
		
		BufferedReader s = new BufferedReader(new FileReader("graphFile.txt")); // file reader
		
		String i;
		
		while ((i = s.readLine()) != null) { // while reading the file
			
			String[] vertices; // vertex array
			
			vertices = i.split("~"); // splits it by the ~ symbol
			g.addVertex(vertices[2], Integer.parseInt(vertices[0]), Integer.parseInt(vertices[1])); // add the vertex through the file line
		
			i = s.readLine(); // skips the next line because it contains the neighbors
			
		}
		
	}
	
	public void createEdges (LocationGraph<String, String> g, JFrame frame) throws IOException { // creates edges based on the file
		
		BufferedReader s = new BufferedReader(new FileReader("graphFile.txt")); // file reader
		
		String i;
		
		while ((i = s.readLine()) != null) { // while reading the file
			
			String[] vertex; // vertex array
			
			vertex = i.split("~"); // split by the ~ symbol
			
			i = s.readLine();
			
			String[] edge; // neighbor array
			
			edge = i.split("~"); // split by the ~ symbol
					
			g.connect(vertex[2], edge[0]);	// connects the vertex and it's neighbor
				
		}
		
	}
	
	public boolean isOn (int x, int y, int xLoc, int yLoc) { // is on method to check if the user is clicking on a vertex
		
		int centerX = (xLoc+(30/2)); // center of the circle
		int centerY = (yLoc+(30/2));
		
		// uses distance formula to check whether user is clicking on the vertex
		int d1 = (int)(Math.sqrt(Math.pow((centerX-(xLoc+(30/2))), 2) + Math.pow((centerY-yLoc), 2)));
		int d2 = (int)(Math.sqrt(Math.pow((centerX-x), 2) + Math.pow((centerY-y), 2)));
		
		if (d2 <= d1) {
			
			return true;
			
		} else {
			
			return false;
			
		}
	
	}	
	
	public static void main (String[] args) throws IOException {
		
		GPS myGPS = new GPS();
		
	}

}
