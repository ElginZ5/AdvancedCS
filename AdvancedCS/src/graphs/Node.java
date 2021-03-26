package graphs;

public class Node<E> {

	public double priority;
	public E info;
	
	public Node(double priority2, E i) {
		priority = priority2;
		info=i;
	}
	
	public String toString() {
		return "(" + info + ", " + priority + ")";
	}
}