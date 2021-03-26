package djikstras;

import java.util.ArrayList;

public class PriorityQueue<E> {
	
	private ArrayList<Node<E>> queue = new ArrayList<Node<E>>();
	
	public int size() {
		return queue.size();
	}
	
	public String toString() {
		return queue.toString();
	}
	
	public Node<E> pop() {
		return queue.remove(queue.size()-1);
	}
	
	public void put(E info, double priority) {
		Node<E> newNode = new Node(priority, info);
		
		if (queue.size() == 0) {
			queue.add(newNode);
		}
		
		else if (queue.get(0).priority < newNode.priority) {
			queue.add(0, newNode);
		}
		
		else if (queue.get(queue.size()-1).priority > newNode.priority) {
			queue.add(newNode);
		}
	
		else {
			
			int start = 0, end = queue.size()-1;
			
			while (start < end) {
				
				Node<E> midpoint = queue.get((start+end)/2);
				
				if (midpoint.priority > newNode.priority) {
					start = (start+end)/2 + 1;
				}
				
				else {
					end = (start+end)/2;
				}
			}
			
			queue.add(start, newNode);
			
		}
	}

	public static void main(String[] args) {
		PriorityQueue<Character> myQ = new PriorityQueue<Character>();
		
		for (int i = 0; i< 100; i++) {
			myQ.put('g', (int)(Math.random()*100));
		}
		
		System.out.println(myQ);

	}

}