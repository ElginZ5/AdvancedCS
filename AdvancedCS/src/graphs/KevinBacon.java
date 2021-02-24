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
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class KevinBacon<E, T> {
	
	HashMap<E, Vertex> vertices;
	
	public KevinBacon() throws IOException {
		
		vertices = new HashMap<E, Vertex>();
		
				
	}
	
	public void addVertex(E info) {
		
		vertices.put(info, new Vertex(info));
		
	}
	
	public void connect(E info1, E info2, T label) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		Edge e = new Edge (label, v1, v2);
		
		v1.edges.add(e);
		v2.edges.add(e);
		
	}
	
	private class Edge {
		
		T label;
		Vertex v1, v2;
		
		public Edge (T label, Vertex v1, Vertex v2) {
			
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
			
		}
		
		public Vertex getNeighbor (Vertex v) {
			
			if (v.info.equals(v1.info))
				return v2;
			else
				return v1;
			
		}
		
		public String toString () {
			
			return label.toString();
			
		}
		
	}
	
	private class Vertex {
		
		E info;
		HashSet<Edge> edges;
		
		public Vertex(E info) {
			
			this.info = info;
			edges = new HashSet<Edge>();
			
		}	
		
		public boolean equals (Vertex other) {
			
			return info.equals(other.info);
			
		}
		
		public String toString () {
			
			return info.toString();
			
		}
		
	}
	
	public ArrayList<Object> BFS(E start, E target) {
	
		ArrayList<Vertex> toVisit = new ArrayList<Vertex>();
		toVisit.add(vertices.get(start));
		
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>();
		
		while (!toVisit.isEmpty()) {
			
			Vertex curr = toVisit.remove(0);
			
			for (Edge e : curr.edges) {
				
				Vertex neighbor = e.getNeighbor(curr);
				
				if (visited.contains(neighbor)) continue;
				

				leadsTo.put(neighbor, e);
				
				if (neighbor.info.equals(target)) {
					
					return backtrace(neighbor, leadsTo);
				}
				
				else {
					toVisit.add(neighbor);
					visited.add(neighbor);
				}
			}
		}
		return null;
	}
	
	public ArrayList<Object> backtrace(Vertex target, HashMap<Vertex, Edge> leadsTo) {
		
		Vertex curr = target;
		ArrayList<Object> path = new ArrayList<Object>();
		
		while (leadsTo.get(curr) != null) {
			path.add(0, curr.info);
			path.add(0, leadsTo.get(curr).label);
			curr = leadsTo.get(curr).getNeighbor(curr);
		}
		path.add(0, curr.info);
		return path;
		
	}
	
	public void createVertices (KevinBacon g, HashMap actors, HashMap reverseActors, ArrayList<String> actoro) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("actors.txt"));
		
		String i;
		while ((i = s.readLine()) != null) {
			
			String[] actor;
			
			actor = i.split("~");
			actors.put(actor[0], actor[1]);
			reverseActors.put(actor[1], actor[0]);
			actoro.add((String) actors.get(actor[0]));
			g.addVertex(actor[1]);
			
		}
		
		s.close();
		
	}
	
	public void createMovieMap (HashMap movies) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("movies.txt"));
		
		String i;
		while ((i = s.readLine()) != null) {
			
			String[] movie;
			
			movie = i.split("~");
			movies.put(movie[0], movie[1]);
			
		}	
		
		s.close();
		
	}
	
	public void generateEdges (KevinBacon g, HashMap actors, HashMap movies) throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("movie-actors.txt"));
		
		String i;
		String curr = "";
		String prev = "";
		int count = 0;
		
		ArrayList<String> act = new ArrayList<String>();
		
		while ((i = s.readLine()) != null) {
			
			String[] movieActors = i.split("~");
			
			curr = movieActors[0];
			//System.out.println(curr);
			
			if (curr.equals(prev)) {
				
				act.add(movieActors[1]);
				
			} else {
			
				for (int w = 0; w < act.size(); w++) {
				
					for (int p = w+1; p < act.size(); p++) {
					
						g.connect(actors.get(act.get(w)), actors.get(act.get(p)), movies.get(prev));
						//System.out.println(movies.get(curr));
						count++;
					
					}
				
				}
				
				prev = movieActors[0];
				act.clear();
				act.add(movieActors[1]);
				
			}
			
	
		}
		
		for (int w = 0; w < act.size(); w++) {
			
			for (int p = w+1; p < act.size(); p++) {
			
				g.connect(actors.get(act.get(w)), actors.get(act.get(p)), movies.get(prev));
			
			}
		
		}
		
			
		s.close();
		System.out.println(count);
	
	}
	
	
	public static void main (String[] args) throws IOException {
		
		HashMap<String, String> actors = new HashMap<String, String>();
		HashMap<String, String> reverseActors = new HashMap<String, String>();
		HashMap<String, String> movies = new HashMap<String, String>();
		ArrayList<String> actoro = new ArrayList<String>();
		
		KevinBacon<String, String> g = new KevinBacon<String, String>();
		
		final int WIDTH = 700, HEIGHT = 600, BUTTON_HEIGHT = 90;
		JTextArea displayAreaText;
		
		JTextArea displayAreaTSize, displayArea, textContent, textContent2;
		
		Color c = new Color(237, 236, 235);
		
		Font myFont = new Font("Arial", Font.BOLD, 16);
		
		JFrame frame = new JFrame();
		
		JPanel panel = new JPanel();
		
		BoxLayout boxlayout = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(boxlayout);
		
		textContent = new JTextArea(); // text area for inputing the text
		textContent.setEditable(true);
		
		textContent2 = new JTextArea(); // text area for inputing the size
		textContent2.setEditable(true);
		
		displayAreaText = new JTextArea();
		displayAreaText.setEditable(false);
		displayAreaText.setBackground(c);
		
		displayAreaText.setFont(myFont);
		displayAreaText.setText("Actor 1: ");
		
		displayAreaTSize = new JTextArea();
		displayAreaTSize.setEditable(false);
		displayAreaTSize.setBackground(c);
		
		displayAreaTSize.setFont(myFont);
		displayAreaTSize.setText("Actor 2: ");
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setPreferredSize(new Dimension(600, 450));
		displayArea.setBackground(Color.WHITE);
		
		textContent.setPreferredSize(new Dimension(100, 20)); // size of the textboxes
		textContent2.setPreferredSize(new Dimension(100, 20));
		
		panel.setBorder(BorderFactory.createTitledBorder("Kevin Bacon Game"));
		
		JButton search = new JButton ("Search");
		JButton allEdges = new JButton ("Find all of the common movies");
		JButton allConnections = new JButton("Find all the shared actors connected to actor one and two");
		
		//System.out.println(actors);
		
		search.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String actorOne = textContent.getText().trim();
				String actorTwo = textContent2.getText().trim();
				
				if (actorTwo.isEmpty() || actorOne.isEmpty() || 
						!actors.containsValue(actorOne) || !actors.containsValue(actorTwo)) {
					
					displayArea.setText("Sorry there was an error, please re-enter your actors");
					
				} else {
					
					ArrayList<Object> arr = new ArrayList<Object>();
					arr = g.BFS(actorOne, actorTwo);
					System.out.println(arr);
					
					if (g.BFS(actorOne, actorTwo) != null) {
					
						if (arr.size() > 3) {
						
							displayArea.setText(arr.get(0)+" was in a movie with "+arr.get(2)+
								" who was in a movie with "+arr.get(arr.size()-1));
						
						} else {
						
							displayArea.setText(arr.get(0)+" was in a movie with "+arr.get(2));
						
						}
						
					} else {
						
						displayArea.setText(arr.get(0)+" was never in a movie with "+arr.get(arr.size()-1));
						
					}
					
				}
				
			}
			
		});
		
		allEdges.addActionListener(new ActionListener () {
			
			
			public void actionPerformed (ActionEvent e) {
				
				
				String actorOne = textContent.getText().trim();
				String actorTwo = textContent2.getText().trim();
				
				ArrayList<String> shareddEdges = new ArrayList<String>();
				
				String firstActor = reverseActors.get(actorOne);
				String secondActor = reverseActors.get(actorTwo);
				
				for (int i = 0; i < g.vertices.get(actors.get(firstActor)).edges.size(); i++) {
					
					for (int j = 0; j < g.vertices.get(actors.get(secondActor)).edges.size(); j++) {
						
						if (g.vertices.get(actors.get(firstActor)).edges.toArray()[i].equals(
								g.vertices.get(actors.get(secondActor)).edges.toArray()[j])) {
							
							shareddEdges.add(g.vertices.get(actors.get(firstActor)).edges.toArray()[i].toString());
							
						}
						
						
					}
					
				}
				
				
				System.out.println(shareddEdges);
				
			}
			
			
		});
		
		allConnections.addActionListener(new ActionListener () {
			
			
			public void actionPerformed (ActionEvent e) {
				
				int boogie = 0;
				
				ArrayList<String> actorOneC = new ArrayList<String>();
				ArrayList<String> actorTwoC =  new ArrayList<String>();
				ArrayList<String> sharedActors = new ArrayList<String>();
				
				String actorOne = textContent.getText().trim();
				String actorTwo = textContent2.getText().trim();
				
				String firstActor = reverseActors.get(actorOne);
				String secondActor = reverseActors.get(actorTwo);
				
				//System.out.println(actors.get(reverseActors.get(actoro.get(0))));
				
				for (int i = 0; i < g.vertices.get(actors.get(firstActor)).edges.size(); i++) {
					
					for (int j = 0; j < actoro.size(); j++) {
						
						for (int z = 0; z < g.vertices.get(actors.get(reverseActors.get(actoro.get(j)))).edges.size(); z++) {
						
						if (g.vertices.get(actors.get(firstActor)).edges.toArray()[i].equals(
								g.vertices.get(actors.get(reverseActors.get(actoro.get(j)))).edges.toArray()[z])) {
							
							//System.out.println(actoro.get(j));
							//actorOneC.add(actoro.get(j));
							boogie++;
							
							
						}
						
						//System.out.println(g.vertices.get(actors.get(firstActor)).edges.toArray()[i]+" SCRAWT \n");
						
						//System.out.println(g.vertices.get(actors.get(reverseActors.get(actoro.get(j))))+" DEJA "+g.vertices.get(actors.get(reverseActors.get(actoro.get(j)))).edges.toArray()[z]);
							
						//System.out.println(g.vertices.get(actors.get(firstActor)).edges.toArray()[i]);
						
						}
						
					}
					
				}
				
				//System.out.println(actorOneC);
				
			}
			
			
		});
		
		JPanel innerPanel = new JPanel(); // panel to add the buttons
		innerPanel.setPreferredSize(new Dimension(HEIGHT, BUTTON_HEIGHT)); // size of the inner panel
		innerPanel.setBackground(c);
		// adding all of the buttons, textareas, and display areas
		innerPanel.add(displayAreaText);
		innerPanel.add(textContent);
		innerPanel.add(displayAreaTSize);
		innerPanel.add(textContent2);
		innerPanel.add(displayArea);
		
		JPanel innerInnerPanel = new JPanel();
		innerInnerPanel.setPreferredSize(new Dimension(0, BUTTON_HEIGHT-375));
		innerInnerPanel.setBackground(c);
		innerInnerPanel.add(search);
		innerInnerPanel.add(allEdges);
		innerInnerPanel.add(allConnections);
		
		panel.add(innerPanel);
		panel.add(innerInnerPanel);
		
		g.createMovieMap(movies);
		g.createVertices(g, actors, reverseActors, actoro);
		g.generateEdges(g, actors, movies);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);

		
	}
		
}
	
