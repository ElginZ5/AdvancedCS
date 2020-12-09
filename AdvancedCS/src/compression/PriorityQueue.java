package compression;

import java.util.ArrayList;

public class PriorityQueue<T> {

	private ArrayList<Node<T>> queue = new ArrayList<Node<T>>();
	
	public void add (T info, int priority) {
		
		Node<T> node = new Node (priority, info);
		
		if (queue.size() == 0) {
			
			queue.add(node);
			
		} else if (queue.get(0).priority < node.priority) {
			
			queue.add(0, node);
			
		} else if (queue.get(queue.size()-1).priority > node.priority) {
			
			queue.add(node);
			
		} else {
			
			int start = 0, end = queue.size()-1;
			
			while (start < end) {
				
				Node<T> midpoint = queue.get((start+end)/2);
				
				if (midpoint.priority > node.priority) {
					
					start = (start+end)/2 + 1;
					
				} else {
					
					end = (start+end)/2;
					
				}
				
			}
			
			queue.add(start, node);
			
		}
		
	}
	
	public int size () {
		
		return queue.size();
		
	}
	
	public String toString () {
		
		return queue.toString();
		
	}
	
	public Node<T> pop () {
		
		return queue.remove(queue.size()-1);
		
	}
	
	public static void main (String[] args) {
		
		PriorityQueue<Character> myPQ = new PriorityQueue<Character>();
		
		/*myPQ.add('y', 6);
		
		System.out.println(myPQ.toString());*/
		
	}
	
}


