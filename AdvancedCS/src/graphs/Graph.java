package graphs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Graph<E> {
	
	HashMap<E, Vertex> vertices;
	
	public Graph() {
		
		vertices = new HashMap<E, Vertex>();
		
	}
	
	public void addVertex(E info) {
		
		vertices.put(info, new Vertex(info));
		
	}
	
	public void connect(E info1, E info2) {
		
		Vertex v1 = vertices.get(info1);
		Vertex v2 = vertices.get(info2);
		
		v1.neighbors.add(v2);
		v2.neighbors.add(v1);
		
	}
	
	private class Vertex {
		
		E info;
		HashSet<Vertex> neighbors;
		
		public Vertex(E info) {
			
			this.info = info;
			neighbors = new HashSet<Vertex>();
			
		}	
		
		public boolean equals (Vertex other) {
			
			return info.equals(other.info);
			
		}
		
	}
	
	public ArrayList<E> BFS (E start, E target) {
		
		ArrayList<Vertex> points = new ArrayList<Vertex>();
		points.add(vertices.get(start));
		HashSet<Vertex> visited = new HashSet<Vertex>();
		visited.add(vertices.get(start));
		
		HashMap<Vertex, Vertex> leadsTo = new HashMap<Vertex, Vertex>();
		
		while (!points.isEmpty()) {
			
			Vertex curr = points.remove(0);
			
			for (Vertex neighbor : curr.neighbors) {
				
				if (visited.contains(neighbor)) {
					
					continue;
					
				}
				
				leadsTo.put(neighbor, curr);
				
				if (neighbor.info.equals(target)) {
					
					return backTrace(neighbor, leadsTo);
					
				} 
				
				else {
					
					points.add(neighbor);
					visited.add(neighbor);
					
				}
				
				
			}
			
		}
		
		return null;
		
	}
	
	public ArrayList<E> backTrace (Vertex target, HashMap<Vertex, Vertex> leadsTo) {
		
		Vertex curr = target;
		ArrayList<E> path = new ArrayList<E>();
		
		while (curr != null) {
			
			path.add(0, curr.info);
			curr = leadsTo.get(curr);
			
		}
		
		return path;
		
	}
	
	public static void main (String[] args) {
		
		Graph<String> g = new Graph<String>();
		
		g.addVertex("Bob");
		g.addVertex("Elgin");
		g.addVertex("Jack");
		g.addVertex("Alex");
		g.addVertex("Austin");
		g.addVertex("Jimmy");
		g.addVertex("Adam");
		
		g.connect("Bob", "Elgin");
		g.connect("Bob", "Jack");
		g.connect("Jimmy", "Adam");
		g.connect("Elgin", "Jimmy");
		g.connect("Jack", "Elgin");
		g.connect("Elgin", "Austin");
		
		System.out.println(g.BFS("Bob", "Austin"));
		
	}
	
}