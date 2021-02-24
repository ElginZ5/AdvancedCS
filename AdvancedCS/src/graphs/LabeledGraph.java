package graphs;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class LabeledGraph<E, T> {
	
	HashMap<E, Vertex> vertices;
	
	public LabeledGraph() {
		
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
			
			if (v.info.equals(v1.info)) {
				return v2;
			}
			
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
	
	public static void main (String[] args) {
		
		LabeledGraph<String, String> g = new LabeledGraph<String, String>();
		
		g.addVertex("Bob");
		g.addVertex("Elgin");
		g.addVertex("Jack");
		/*g.addVertex("Alex");
		g.addVertex("Austin");
		g.addVertex("Jimmy");
		g.addVertex("Adam");*/
		
		/*g.connect("Bob", "Elgin", null);
		g.connect("Bob", "Jack", null);
		g.connect("Jimmy", "Adam", null);
		g.connect("Elgin", "Jimmy", null);
		g.connect("Jack", "Elgin", null);
		g.connect("Elgin", "Austin", null);*/
		
		g.connect("Bob", "Elgin", "Dogs");
		g.connect("Elgin", "Jack", "Dogs");
		
		System.out.println(g.BFS("Bob", "Jack"));
		System.out.println(g.vertices.get("Bob").edges.toString());
		
	}
	
}