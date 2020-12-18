package compression;

public class Branch<T> {

	public boolean isLeaf;
	public Branch left, right;
	public T info;
	
	public Branch (Branch child1, Branch child2, boolean isLeaf) {
		
		this.left = child1;
		this.right = child2;
		this.isLeaf = false;
		
	}
	
	public Branch (T info, boolean isLeaf) {
		
		this.info = info;
		this.isLeaf = true;
		
	}
	
	public String toString () {
		
		return info+"";
		
	}
	
}
