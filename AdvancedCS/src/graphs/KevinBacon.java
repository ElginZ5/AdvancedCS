package graphs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class KevinBacon<E, T> {
	
	HashMap<E, Vertex> vertices; // vertices hashmap
	
	public static String mostEdges = ""; // string used for the actor with the most edges/movies
	
	public KevinBacon() throws IOException {
		
		vertices = new HashMap<E, Vertex>(); // initializing vertices in the constructor 
		
	}
	
	public void addVertex(E info) { // addVertex method
		
		vertices.put(info, new Vertex(info)); // adds the parameter (info) to vertices
		
	}
	
	public void connect(E info1, E info2, T label) { // connect method
		
		Vertex v1 = vertices.get(info1); // sets v1 to the vertex given by the user (info1)
		Vertex v2 = vertices.get(info2); // sets v1 to the vertex given by the user (info2)
		
		Edge e = new Edge (label, v1, v2); // creates a new edge
		
		v1.edges.add(e); // adds the edge to v1
		v2.edges.add(e); // adds the edge to v2
		
	}
	
	private class Edge { // edge class
		
		T label; // the label (movie)
		Vertex v1, v2; // two vertices connected by the edge
		
		public Edge (T label, Vertex v1, Vertex v2) { // constructor 
			
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
			
		}
		
		public Vertex getNeighbor (Vertex v) { // getNeighbor method
			
			if (v.info.equals(v1.info)) // gets the neighbor of the edge (either v1 or v2)
				return v2;
			else
				return v1;
			
		}
		
		public String toString () { // toString method to print the movie out 
			
			return label.toString();
			
		}
		
	}
	
	private class Vertex { // vertex class
		
		E info; // holds info
		HashSet<Edge> edges; // holds the edges it has
		
		public Vertex(E info) { // constructor 
			
			this.info = info;
			edges = new HashSet<Edge>();
			
		}	
		
		public boolean equals (Vertex other) { // equals method
			
			return info.equals(other.info); // returns true if the vertex equals another vertex
			
		}
		
		public String toString () { // toString method
			
			return info.toString(); // prints the vertex
			
		}
		
	}
	
	public ArrayList<Object> BFS(E start, E target) { // BFS search
	
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>(); // gets a list of vertices that need to be visited
		toVisit.add(vertices.get(start)); // adds the first vertex to it
		
		HashSet<Vertex> visited = new HashSet<Vertex>(); // a list of vertices that have already been visited
		visited.add(vertices.get(start)); // adds the first vertex to it
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>(); // HashMap leadsTo its a hashmap that shows the edge that the vertex leadsTo
		
		while (!toVisit.isEmpty()) { // while there are still vertices we need to visit
			
			Vertex curr = toVisit.remove(0); // vertex curr is equal to the first vertex and also removes it from the list
			
			for (Edge e : curr.edges) { // for an edge in the edges of the vertex curr
				
				Vertex neighbor = e.getNeighbor(curr); // gets the neighbor of curr
				
				if (visited.contains(neighbor)) continue; // if we have already visited neighbor, continue
				

				leadsTo.put(neighbor, e); // add neighbor as the key and the edge as the value to leads to
				
				if (neighbor.info.equals(target)) { // if the vertex neighbor is equal to our target
					
					return backtrace(neighbor, leadsTo); // returns the path by backtracing
				}
				
				else {
					toVisit.add(neighbor); // if it isnt our target add neighbor to toVisit
					visited.add(neighbor); // and add neighbor to visited
				}
			}
		}
		return null;
	}
	
	public ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) { // backtrace method
		
		Vertex curr = target; // vertex curr is initialized as target
		ArrayList<Object> path = new ArrayList<Object>(); // new arraylist path
		
		while (leadsTo.get(curr) != null) { // while the edge is not null
			path.add(0, curr.info); // adds the vertex to the path (actor)
			path.add(0, leadsTo.get(curr).label); // adds the label to the path (movie)
			curr = leadsTo.get(curr).getNeighbor(curr); // curr is set as the neighbor
		}
		path.add(0, curr.info); // adds the last vertex
		return path;
		
	}
	
	// method to create the vertices
	public void createVertices (KevinBacon g, HashMap actors, HashMap reverseActors, ArrayList<String> actorList) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("actors.txt")); // buffered reader that reads the actors text file
		
		String i;
		while ((i = s.readLine()) != null) { // reads the lines
			
			String[] actor; // array actor
			
			actor = i.split("~"); // splits the line in half based on where the ~ symbol is to separate the code and the actor name
			actors.put(actor[0], actor[1]); // adds the code as the key and the name as the value to the hashmap
			reverseActors.put(actor[1], actor[0]); // adds the name as the key and the code as the value to another hashmap
			actorList.add(actor[1]); // adds the current actor to an arraylist
			g.addVertex(actor[1]); // adds the vertex ( the actor name)
			
		}
		
		s.close(); // closes the buffered reader
		
	}
	
	// method to create the hashmap for the movies
	public void createMovieMap (HashMap movies) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("movies.txt")); // file reader
		
		String i;
		while ((i = s.readLine()) != null) { // reads each line
			
			String[] movie; // array movie
			
			movie = i.split("~"); // splits the line into 2 separate strings based on where the ~ symbol is
			movies.put(movie[0], movie[1]); // adds code as the key and the movie name as the value in the hashmap
			
		}	
		
		s.close(); // closes the buffered reader
		
	}
	
	// creates the edges
	public void generateEdges (KevinBacon g, HashMap actors, HashMap movies) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("movie-actors.txt")); // reads the movie-actors file
		
		String i;
		String curr = ""; // current movie
		String prev = ""; // previous movie
		int count = 0; // count to see the number of edges generated 
		
		ArrayList<String> act = new ArrayList<String>(); // arraylist act to store actors that need to be connected
		
		while ((i = s.readLine()) != null) { // reads each line
			
			String[] movieActors = i.split("~"); // splits each line in half to separate the movie name and the actor's name
			
			curr = movieActors[0]; // sets curr as the current movie
			
			if (curr.equals(prev)) { // if curr is equal to the previous movie
				
				act.add(movieActors[1]); // add the actor's name since the movie is still the same so we do not have to connect yet
				
			} else { // if the current movie is different than the previous movie
			
				for (int w = 0; w < act.size(); w++) { // for loop
				
					for (int p = w+1; p < act.size(); p++) {
					
						g.connect(actors.get(act.get(w)), actors.get(act.get(p)), movies.get(prev)); // connects each actor in the list by the previous movie
						count++; // adds to count since we just connected two vertices and generated an edge
					
					}
				
				}
				
				prev = movieActors[0]; // set prev to the current movie
				act.clear(); // clears the act list because we don't want to connect the same actors again
				act.add(movieActors[1]); // adds the current actor's name to the list
				
			}
			
	
		}
		
		// these for loops finish connecting the last vertices
		for (int w = 0; w < act.size(); w++) {
			
			for (int p = w+1; p < act.size(); p++) {
			
				g.connect(actors.get(act.get(w)), actors.get(act.get(p)), movies.get(prev));
			
			}
		
		}
		
		// closes the buffered reader
		s.close();
		//System.out.println(count);
	
	}
	
	public static void main (String[] args) throws IOException {
		
		HashMap<String, String> actors = new HashMap<String, String>(); // actors hashmap
		HashMap<String, String> reverseActors = new HashMap<String, String>(); // reverseactors hashmap
		HashMap<String, String> movies = new HashMap<String, String>(); // movies hashmap
		ArrayList<String> actorList = new ArrayList<String>(); // actorList arraylist
		
		KevinBacon<String, String> g = new KevinBacon<String, String>(); // creates a new KevinBacon g
		
		final int WIDTH = 700, HEIGHT = 700, PANEL_HEIGHT = 90;  // heights and width
		
		JTextArea displayAreaTSize, displayArea, displayAreaText, textContent, textContent2; // creates display and text areas
		
		Color c = new Color(237, 236, 235); // the color grey
		 
		Font myFont = new Font("Arial", Font.BOLD, 16); // the font
		
		JFrame frame = new JFrame(); // new JFrame
		 
		JPanel panel = new JPanel(); // new JPanel
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		textContent = new JTextArea(); // text area for inputing the text
		textContent.setEditable(true);
		
		textContent2 = new JTextArea(); // text area for inputing the text
		textContent2.setEditable(true);
		
		displayAreaText = new JTextArea(); // display area
		displayAreaText.setEditable(false);
		displayAreaText.setBackground(c);
		
		displayAreaText.setFont(myFont);  // display area
		displayAreaText.setText("Actor 1: ");
		
		displayAreaTSize = new JTextArea(); // display area
		displayAreaTSize.setEditable(false);
		displayAreaTSize.setBackground(c);
		
		displayAreaTSize.setFont(myFont);   // display area
		displayAreaTSize.setText("Actor 2: ");
		
		displayArea = new JTextArea(); // main display area
		displayArea.setEditable(false);
		
		displayArea.setPreferredSize(new Dimension(600, 450)); // size of the display area
		displayArea.setBackground(Color.WHITE); // the color 
		
		textContent.setPreferredSize(new Dimension(100, 20)); // size of the textboxes
		textContent2.setPreferredSize(new Dimension(100, 20));
		
		panel.setBorder(BorderFactory.createTitledBorder("Kevin Bacon Game")); // border with the name 
		
		// four buttons 
		JButton search = new JButton ("Find The Path");
		JButton sameEdges = new JButton ("Find All of the Movies Both Actors Have Been In");
		JButton mostMovies = new JButton("Find The Actor Who Has Been in the Most Movies");
		JButton sameEdges2 = new JButton("Find How Many Movies Actor 1 Has Been in With The Actor With The Most Movies");
		
		// initial text 
		displayArea.setText("Welcome! Please enter the actors' names with the first letter\n"
				+ "in their first and last names capitilized, thank you");
		
		search.addActionListener(new ActionListener() { // find path button
			
			@Override
			public void actionPerformed(ActionEvent e) {

				displayArea.setText(""); // clears the display area
				
				String actorOne = textContent.getText().trim(); // gets the two inputed actor names
				String actorTwo = textContent2.getText().trim();
				
				if (actorTwo.isEmpty() || actorOne.isEmpty() || 
						!actors.containsValue(actorOne) || !actors.containsValue(actorTwo)) { // checks if their empty or if the names don't exist 
					
					displayArea.setText("Sorry there was an error, please re-enter your actors"); // displays error message
					
				} else {
					
					ArrayList<Object> arr = new ArrayList<Object>(); // new arrayList
					arr = g.BFS(actorOne, actorTwo); // set it equal to the path of the two actors
					
					if (g.BFS(actorOne, actorTwo) != null) { // if there a path 
					
						if (arr.size() > 3) { // if the size is more than 3
							
							for (int i = 0; i < arr.size()-2; i+=2) { // for loop that runs through the list 
								
								int j = i+1;
								int w = j+1;
				
								// displays the path
								displayArea.setText(displayArea.getText()+arr.get(i)+" was in the movie "+arr.get(j)+" with "+arr.get(w)
										+"\n");
								
							}
								
						
						} else {
						
							// displays the path if the list is less than 3 
							displayArea.setText(arr.get(0)+" was in the movie "+arr.get(1)+" with "+arr.get(2));
						
						}
						
					} else {
						
						// if there is no path, displays this 
						displayArea.setText(actorOne+" has no connection with "+actorTwo);
						
					}
					
				}
				
			}
			
		});
		
		sameEdges.addActionListener(new ActionListener () { // checks which movies both actors starred in together
			
			
			public void actionPerformed (ActionEvent e) {
				
				String actorOne = textContent.getText().trim(); // gets the inputed actor names
				String actorTwo = textContent2.getText().trim();
				
				ArrayList<String> sharedEdges = new ArrayList<String>(); // creates an array list for the shared movies
				
				String firstActor = reverseActors.get(actorOne); // creates a new string that gets the code through the name
				String secondActor = reverseActors.get(actorTwo);
				
				if (actorTwo.isEmpty() || actorOne.isEmpty() || 
						!actors.containsValue(actorOne) || !actors.containsValue(actorTwo)) { // checks if there isnt an error
					
					displayArea.setText("Sorry there was an error, please re-enter your actors");
					
				} else {
				
					for (int i = 0; i < g.vertices.get(actors.get(firstActor)).edges.size(); i++) { // for loop that runs through the first actor's edges
					
						for (int j = 0; j < g.vertices.get(actors.get(secondActor)).edges.size(); j++) { // runs through the second actor's edges 
						
							if (g.vertices.get(actors.get(firstActor)).edges.toArray()[i].equals(
									g.vertices.get(actors.get(secondActor)).edges.toArray()[j])) { // if they are the same
							
								sharedEdges.add(g.vertices.get(actors.get(firstActor)).edges.toArray()[i].toString()); // then add the movie name to the shared edges array list 
							
							}
						
						
						}
					
					}
				
					if (sharedEdges.size() > 0) { // if they share at least one movie
						
						displayArea.setText("The two actors have been in these movie(s) together: \n"); // displays the movies they have been in together
						
						for (int i = 0; i < sharedEdges.size(); i++) {
				
							displayArea.setText(displayArea.getText()+sharedEdges.get(i)+"\n");
					
						}
					
					} else { // if they have not been in a single movie together
					
						// displays this
						displayArea.setText("These two actors have never been in the same movie before");
					
					}
				
				
				}
				
			}
			
			
		});
		
		mostMovies.addActionListener(new ActionListener () { // finds the actor who has been in the most movies
			
			public void actionPerformed (ActionEvent e) {
				
				HashMap<Integer, String> edgeLength = new HashMap<Integer, String>(); // creates a hashmap that links the how many edges they have to the name of the actor
				ArrayList<Integer> arr = new ArrayList<Integer>(); // array list with all the sizes of all the edges the actors have

				int largestSize = 0; // largest size
				
				for (int i = 0; i < actorList.size(); i++) { // runs through all the actors
					
					edgeLength.put(g.vertices.get(actorList.get(i)).edges.size(), actorList.get(i)); // adds each corresponding size and actor 
					arr.add(g.vertices.get(actorList.get(i)).edges.size()); // adds each size 
					
				}
				
				largestSize = Collections.max(arr); // finds the largest size
				 
				mostEdges = edgeLength.get(largestSize); // sets this string to the actor name who has the largest size of edges
				
				displayArea.setText("The Actor That Has Starred in the Most Movies is: "+mostEdges); // displays that actor's name
				
			}
			
			
		});
		
		sameEdges2.addActionListener(new ActionListener () { // method that finds the shared movies between actor one and the actor with the most edges

			public void actionPerformed(ActionEvent e) {
			
				String actorOne = textContent.getText().trim(); // gets the input actor
				String actorTwo = mostEdges; // sets the second actor as the one who has the most edges
				
				ArrayList<String> sharedEdges = new ArrayList<String>(); // array list with their shared edges
				
				String firstActor = reverseActors.get(actorOne); // gets the code of the two actors
				String secondActor = reverseActors.get(actorTwo);
				
				if (actorOne.isEmpty() || !actors.containsValue(actorOne)) { // checks if there are errors
					
					displayArea.setText("Sorry there was an error, please re-enter your actors");
					
				} else {
				
					if (mostEdges != "") { // if most edges is not empty
				
						// runs through the edges of both actors
						for (int i = 0; i < g.vertices.get(actors.get(firstActor)).edges.size(); i++) {
					
							for (int j = 0; j < g.vertices.get(actors.get(secondActor)).edges.size(); j++) {
							
								// if they are the same
								if (g.vertices.get(actors.get(firstActor)).edges.toArray()[i].equals(
										g.vertices.get(actors.get(secondActor)).edges.toArray()[j])) {
							
									// add the edge to the shared edges array list
									sharedEdges.add(g.vertices.get(actors.get(firstActor)).edges.toArray()[i].toString());
							
								}
						
							
							}
						
						}
				
						// if they share at least one movie together
						if (sharedEdges.size() > 0) {
				
							displayArea.setText("The two actors have been in these movie(s) together: \n");
					
							for (int i = 0; i < sharedEdges.size(); i++) {
				
								// display the movie(s)
								displayArea.setText(displayArea.getText()+sharedEdges.get(i)+"\n");
					
							}
					
						} else { // if they don't share any movies with one-another
					
							displayArea.setText("These two actors have never been in the same movie before");
					
						}
					
					} else { // if mostEdges is empty, it tells you to click on find the actor with the most movies first
					
						displayArea.setText("Please find the actor who has starred in the most movies first");
					
					}
					
				}
				
				
			}
				
		
		});
		
		
		JPanel innerPanel = new JPanel(); // panel to add the textAreas
		innerPanel.setPreferredSize(new Dimension(HEIGHT, PANEL_HEIGHT)); // size of the inner panel
		innerPanel.setBackground(c);
		// adding all of the textareas, and display areas
		innerPanel.add(displayAreaText);
		innerPanel.add(textContent);
		innerPanel.add(displayAreaTSize);
		innerPanel.add(textContent2);
		innerPanel.add(displayArea);
		
		// a different panel to add the buttons because they are in a different place
		JPanel innerPanel2 = new JPanel();
		innerPanel2.setPreferredSize(new Dimension(0, PANEL_HEIGHT-375));
		innerPanel2.setBackground(c);
		innerPanel2.add(search);
		innerPanel2.add(sameEdges);
		innerPanel2.add(mostMovies);
		innerPanel2.add(sameEdges2);
		
		// panels adds both inner panels
		panel.add(innerPanel);
		panel.add(innerPanel2);
		
		// runs create movie map, vertices, and edges
		g.createMovieMap(movies);
		g.createVertices(g, actors, reverseActors, actorList);
		g.generateEdges(g, actors, movies);
		
		// sets up the frame
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);

		
	}
		
}
	
