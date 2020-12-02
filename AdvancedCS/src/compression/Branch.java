package compression;

public class Branch<T> {

	public boolean isLeaf;
	public T left, right;
	public T info;
	
	public Branch (T child1, T child2) {
		
		this.left = child1;
		this.right = child2;
		isLeaf = false;
		
	}
	
	public Branch (T info) {
		
		this.info = info;
		isLeaf = true;
		
	}
	
}
