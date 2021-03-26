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
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import graphs.LocationGraph.*;

public class GPSMapBuilder <E, T> {

	int buttonMode; // mode
	//boolean done;
	
	public GPSMapBuilder () throws IOException { // constructor
		
		LocationGraph<String, String> g = new LocationGraph<String, String>(); // new LocationGraph
	
		BufferedImage map = ImageIO.read(new File ("pokemonmap.jpg")); // the image of the map
		
		ArrayList<String> requiredConnections = new ArrayList<String>(); // String that gets the needed connections to do 
		
		final int WIDTH = 1200, HEIGHT = 625; // width and height of the window
		
		buttonMode = 0; // initialized to 0
		//done = false;
		
		JFrame frame = new JFrame("Map"); // new JFrame
		
		JPanel panel = new JPanel(); // new JPanel
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		JPanel innerPanel = new JPanel(); // new InnerPanel
		innerPanel.setPreferredSize(new Dimension(WIDTH, 30));
		
		//JLabel pic = new JLabel(new ImageIcon(map));
		Font myFont = new Font("Arial", Font.BOLD, 16); // font
		 
		JTextArea displayArea = new JTextArea(); // displayArea
		displayArea.setEditable(false);
		displayArea.setFont(myFont);
		displayArea.setBackground(null);
		displayArea.setText("Please click this done button when you are finished creating vertices:");  // text of the display area
		
		JButton saveButton = new JButton("Save"); // save button
		JButton doneButton = new JButton("Done"); // done button
		
		doneButton.addActionListener(new ActionListener() { // done button is clicked when finished creating vertices

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				buttonMode = 1; // after creating vertices change button mode to 1
				innerPanel.remove(doneButton); // remove the done button and add the save button
				innerPanel.add(saveButton);
				displayArea.setText("Please click this save button when you are connecting your edges:"); // change the text of the display area
				
			}
			
		});
		
		saveButton.addActionListener(new ActionListener() { // save button

			@Override
			public void actionPerformed(ActionEvent e) {

				//System.out.println(vertexLocation);
				//System.out.println(vertexName);
				
				try {
					
					//done = true;
					//frame.getContentPane().repaint();
					BufferedWriter bw = new BufferedWriter(new FileWriter("graphFile.txt")); // new buffered writer
					
					for (String i : g.vertices.keySet()) { // for each vertex
						
						for (LocationGraph<String, String>.Edge edge : g.vertices.get(i).edges) { // for each edge of the vertex
							
							//System.out.println(edge.getNeighbor(g.vertices.get(i)).toString());
							LocationGraph<String, String>.Vertex v = g.vertices.get(i); // get the current vertex
							LocationGraph<String, String>.Vertex neighbor = edge.getNeighbor(v); // get its neighbors
							
							bw.write((g.vertices.get(i).x+14)+"~"+(g.vertices.get(i).y+14)+"~"+g.vertices.get(i).info); // write the location and name on the file
							bw.newLine(); // new line
							bw.write(neighbor.toString()+"~"); // write the neighbor onto the file
							bw.newLine();
							
						}
						
					}
					
					bw.close(); // close the buffered writer
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
	
		JPanel drawPanel = new JPanel() { // draw panel
			
			public void paint (Graphics g2d) { // paint
				
				super.paint(g2d);
				
				Graphics2D g2 = (Graphics2D) g2d; // new 2d graphics
				
				g2d.drawImage(map, 0, 0, 1200-12, 600-34, this); // draws the image
				
				for (String s : g.vertices.keySet()) { // for each vertex
					
					int x = g.vertices.get(s).x; // get the x value
					int y = g.vertices.get(s).y; // get the y value
					g2d.setColor(Color.cyan); // color to cyan
					g2d.fillOval(x, y, 30, 30); // draw a circle on the vertex to mark it 
					
				}
				
				for (String s : g.vertices.keySet()) { // for each vertex
					
					for (LocationGraph<String, String>.Edge ee : g.vertices.get(s).edges) { // for each edge of each vertex
						
						LocationGraph<String, String>.Vertex v = g.vertices.get(s); // get the vertex
						LocationGraph<String, String>.Vertex neighbor = ee.getNeighbor(v); // get the neighbors of the edge of the vertex
						
						g2.setColor(Color.cyan); // set color to cyan
						g2.setStroke(new BasicStroke(8)); // size of the line
						g2.drawLine(v.x+14, v.y+14, neighbor.x+14, neighbor.y+14); // draws a line for each connection
						
					}
					
				}
										
			}
							
			
		};
		
		//drawPanel.add(pic);
		
		drawPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT)); // size of drawPanel
		
		drawPanel.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
				if (buttonMode == 0) { // if user is creating vertices
					
					String name = JOptionPane.showInputDialog("What will the name of this location be?"); // gets the user inputed name of the vertex
					g.addVertex(name, e.getX(), e.getY()); // add the vertex to vertices
					
				} else { // if the user is now creating edges
					
					int x = e.getX(); // get the user x
					int y = e.getY(); // get the user y
					
					for (String s : g.vertices.keySet()) { // for each vertex
						 
						int xLoc = g.vertices.get(s).x; // x and y value of the vertex
						int yLoc = g.vertices.get(s).y;
						 
						if (isOn(x, y, xLoc, yLoc)) { // if the user is clicking on the vertex
							
							requiredConnections.add(g.vertices.get(s).info); // add the vertex to required connections
							
							if (requiredConnections.size() == 2) { // if the user has clicked on two vertices
								
								g.connect(requiredConnections.get(0), requiredConnections.get(1)); // connect them
								
								//System.out.println(requiredConnections);
								requiredConnections.clear(); // clears required connections
								
							}
							
							
						}
							
						
					}
					
				}
			
				frame.getContentPane().repaint(); //repaint
				
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
			
		innerPanel.add(displayArea);
		innerPanel.add(doneButton);
		
		panel.add(drawPanel);
		panel.add(innerPanel);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		
	}
	
	public boolean isOn (int x, int y, int xLoc, int yLoc) { // is on method 
		
		int centerX = (xLoc+(30/2)); // center of the circle of the vertex
		int centerY = (yLoc+(30/2));
		
		// uses distance formula to check if the user mouse is inside the circle of the vertex
		int d1 = (int)(Math.sqrt(Math.pow((centerX-(xLoc+(30/2))), 2) + Math.pow((centerY-yLoc), 2)));
		int d2 = (int)(Math.sqrt(Math.pow((centerX-x), 2) + Math.pow((centerY-y), 2)));
		
		if (d2 <= d1) {
			
			return true;
			
		} else {
			
			return false;
			
		}
	
	}	

	
	public static void main (String[] args) throws IOException {
		
		GPSMapBuilder mapBuilder = new GPSMapBuilder();
		
	}
	
}