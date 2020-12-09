package compression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Compressor {
	
	public void createCode (Branch branch, String code) {
		
		Branch left = branch.left;
		Branch right = branch.right;
		
		if (branch.isLeaf) {
			
			codeMap.put((Character) branch.info, code);
			
		}
		
		createCode(left, code+"0");
		createCode(right, code+"1");
		
	}
	
	HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	HashMap<Character, String> codeMap = new HashMap<Character, String>();
	PriorityQueue<Branch> queue = new PriorityQueue<Branch>();
	
	public Compressor () throws IOException {
		
		BufferedReader s = new BufferedReader(new FileReader("test.txt"));
		
		while(s.read() != -1) {
			
			Character ch = (char) s.read();
			
			int prev = 0;
			
			if (map.get(ch) != null) {
				
				prev = map.get(ch);
				
			}
			
			map.put(ch, prev+1);
			
		}
		
		s.close();

		for (Character key : map.keySet()) {
			
			Branch<Character> branch = new Branch<Character>(key, true);
			queue.add(branch, map.get(key));
			
		}
		
		while (queue.size() > 1) {
		
			Node<Branch> branch1 = queue.pop();
			Node<Branch> branch2 = queue.pop();
			
			int priority = branch1.priority+branch2.priority;
		
			Branch<Character> b = new Branch<Character>(branch1.info, branch2.info, false);
			queue.add(b, priority);
			
		}
		
		//createCode(queue.pop().info, "");
		
		System.out.println(map);
		
	}
	
	public static void main(String[] args) throws IOException {

		new Compressor();

	}

}
