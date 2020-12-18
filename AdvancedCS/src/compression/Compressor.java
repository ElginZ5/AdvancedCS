package compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Compressor {
	
	public void createCode (Branch branch, String code) {
		
		Branch left = branch.left;
		Branch right = branch.right;
		
		if (branch.isLeaf) {
			
			codeMap.put((Character) branch.info, code);
			
		} else {
		
			createCode(left, code+"0");
			createCode(right, code+"1");
			
		}
		
	}
	
	public void printCode () {
		
		Set<Character> keys = codeMap.keySet();
		for (char ch : keys) {
			
			System.out.println(ch+" = "+codeMap.get(ch));
			
		}
		
	}
	
	public void compressFile () throws IOException {
		
		BufferedBitWriter writer = new BufferedBitWriter("compressedText");
		BufferedReader s = new BufferedReader(new FileReader("test.txt"));
		
		for (int i = s.read(); i != -1; i = s.read()) {
			
			Character ch = (char) s.read();
			String code = codeMap.get(ch);
			
			for (int j = 0; j < code.length(); j++) {
				
				if (code.charAt(j) == '0') {
					
					writer.writeBit(false);
					
				} else {
					
					writer.writeBit(true);
					
				}
				
			}
			
		}
		
		writer.close();
		s.close();
		
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

		Set<Character> keys = map.keySet();
		for (char ch : keys) {
			
			Branch<Character> branch = new Branch<Character>(ch, true);
			queue.add(branch, map.get(ch));
			
		}
		
		//System.out.println(queue);
		
		while (queue.size() != 1) {
		
			Node<Branch> branch1 = queue.pop();
			Node<Branch> branch2 = queue.pop();
			
			int priority = branch1.priority+branch2.priority;
		
			Branch<Character> b = new Branch<Character>(branch1.info, branch2.info, false);
			queue.add(b, priority);
			
		}
		
		//System.out.println(queue.size());
		
		createCode(queue.pop().info, "");
		//printCode();
		compressFile();
		//System.out.println(map);
		
	}
	
	public static void main(String[] args) throws IOException {

		new Compressor();

	}

}
