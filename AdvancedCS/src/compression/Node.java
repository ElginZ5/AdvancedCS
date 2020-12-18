package compression;

public class Node<E> {

	public int priority;
	public E info;
	
	public Node(int p, E i) {
		priority = p;
		info=i;
	}
	
	public String toString() {
		return "(" + info + ", " + priority + ")";
	}
}