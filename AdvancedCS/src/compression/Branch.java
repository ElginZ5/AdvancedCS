package compression;

public class Branch<T> {

	public boolean isLeaf;
	public Branch<T> left, right;
	public T info;
	
	public Branch (Branch<T> child1, Branch<T> child2, boolean isLeaf) {
		
		this.left = child1;
		this.right = child2;
		isLeaf = false;
		
	}
	
	public Branch (T info, boolean isLeaf) {
		
		this.info = info;
		isLeaf = true;
		
	}
	
}
