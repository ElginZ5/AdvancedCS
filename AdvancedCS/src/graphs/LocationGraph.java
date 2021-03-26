package graphs;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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


public class LocationGraph <E, T> {
	
	HashMap<E, Vertex> vertices; // vertices hashmap
	
	private final double INFINITE = Double.MAX_VALUE; // infinite value
	
	public LocationGraph () { // constructor 
		 
		vertices = new HashMap<E, Vertex>(); // initializing vertices in the constructor 
		
	}
	
	public void addVertex(E name, int x, int y) { // addVertex method
		
		vertices.put(name, new Vertex(name, x, y)); // adds the parameter (info) to vertices
		
	}
	
	public void connect(E info1, E info2) { // connect method
		
		Vertex v1 = vertices.get(info1); // sets v1 to the vertex given by the user (info1)
		Vertex v2 = vertices.get(info2); // sets v1 to the vertex given by the user (info2)
		double label = Math.sqrt(Math.pow(v1.x-v2.x, 2)+Math.pow(v1.y-v2.y, 2)); // gets the distance between the two vertices and sets it as the label of the edge
		
		Edge e = new Edge (label, v1, v2); // creates a new edge
		
		v1.edges.add(e); // adds the edge to v1
		v2.edges.add(e); // adds the edge to v2
		
	}
	
	public class Edge { // edge class
	
		double label; // the label (distance)
		Vertex v1, v2; // two vertices connected by the edge
		
		public Edge (double label, Vertex v1, Vertex v2) { // constructor
			
			this.label = label;
			this.v1 = v1;
			this.v2 = v2;
			
		}
		
		public Vertex getNeighbor (Vertex v) { // getNeighbor method
			
			if (v.info.equals(v1.info)) { // gets the neighbor of the edge (either v1 or v2)
				return v2;
			}
			
			return v1;
			
		}
		
		public String toString () { // toString method to print the neighbor out 
			
			return v1.info.toString();
			
		}
		
	}
	
	public class Vertex { // vertex class
		
		E info; // holds info
		int x, y; // location of the vertex
		HashSet<Edge> edges; // holds the edges it has
		
		public Vertex(E name, int x, int y) { // constructor 
			
			this.info = name;
			this.x = x-14;
			this.y = y-14;
			edges = new HashSet<Edge>();
			
		}	
		
		public boolean equals (Vertex other) { // equals method
			
			return info.equals(other.info);
			
		}
		
		public String toString () { // toString method to print the info of the vertex
			
			return info.toString(); // info (name)
			
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
	
	public ArrayList<Object> Djikstra (E start, E target) { // Djikstra algorithm
		
		PriorityQueue<Vertex> toVisit = new PriorityQueue<Vertex>(); // to visit priority queue
		toVisit.put(vertices.get(start), 0.0); // puts the initial vertex in the pq
		
		HashSet<Vertex> visited = new HashSet<Vertex>(); // hashset for visited vertices
		
		HashMap<Vertex, Double> distance = new HashMap<Vertex, Double>(); // total distance hashmap
		
		HashMap<Vertex, Edge> leadsTo = new HashMap<Vertex, Edge>(); // HashMap leadsTo its a hashmap that shows the edge that the vertex leadsTo
		
		for (E vertex : vertices.keySet()) { // for each vertex
			
			if (vertex.equals(start)) { // if its the first vertex
				
				distance.put(vertices.get(vertex), 0.0); // add it into distance with a distance of 0.0
				
			} else {
				
				distance.put(vertices.get(vertex), INFINITE); // if its not add it into distance with infinte value 
				
			}
			
		}
		
		while (toVisit.size() > 0) { // while there are still vertices to visit
			
			Vertex curr = toVisit.pop().info; // pops the last vertex in tovisit
			
			if (curr.equals(vertices.get(target))) { // if the current vertex is our target
				
				return backtrace(vertices.get(target), leadsTo); // return the path 
				
			} else {
				
				for (Edge e : curr.edges) { // for each edge in a vertex 
					
					Vertex neighbor = e.getNeighbor(curr); // gets the neighbor of the edge
					
					if (visited.contains(neighbor)) continue; // if we have already visited the neighbor continue
					
					double dis = distance.get(curr)+e.label; // else calculate total distance to the neighbor
					
					if (dis < distance.get(neighbor)) { // total distance is less than the map's distance
						
						toVisit.put(neighbor, dis); // add the neighbor to the priority queue
						leadsTo.put(neighbor, e); //add the neighbor to leadsTo
						distance.put(neighbor, dis); // update the maps distance
						
					}
					
				}
				
				visited.add(curr); // mark current vertex as visited 
				
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
	
	public static void main (String[] args) throws IOException {
		
		LocationGraph<String, String> g = new LocationGraph<String, String>();
		
		g.addVertex("A", 100, 100);
		g.addVertex("B", 1000, 100);
		g.addVertex("C", 600, 300);
		g.addVertex("D", 100, 300);
		g.addVertex("E", 300, 300);
		
		g.connect("A", "B");
		g.connect("A", "D");
		g.connect("B", "C");
		g.connect("C", "E");
		g.connect("E", "D");
		
		System.out.println(g.Djikstra("A", "C"));
		
		
	}
	
}