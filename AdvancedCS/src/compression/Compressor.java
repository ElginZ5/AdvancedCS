package compression;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

public class Compressor {
	
	public void createCode (Branch branch, String code) {
		
		Branch left = branch.left;
		Branch right = branch.right;
		
		if (branch.isLeaf) {
			
			codeMap.put((char) branch.info, code);
			
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
		BufferedReader s = new BufferedReader(new FileReader("textFile"));
		
		int i;
		while ((i = s.read()) != -1) {
			
			Character ch = (char)(i);
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
	
	public void decompressFile () throws IOException {
		
		BufferedBitReader reader = new BufferedBitReader("compressedText");
		FileWriter decompressed = new FileWriter("decompressedText");
		BufferedReader s = new BufferedReader(new FileReader("codeFile"));
		
		HashMap<String, String> rebuiltCodeMap = new HashMap<String, String>();
		
		String code1;
		while ((code1 = s.readLine()) != null) {
			
			rebuiltCodeMap.put(code1, s.readLine());
			
		}
		
		s.close();
		//System.out.println(rebuiltCodeMap);
		
		Set<String> codeKeys = rebuiltCodeMap.keySet();
		
		String code2 = "";
		
		while (reader.hasNext()) {
			
			boolean bit = reader.readBit();
			
			if (bit) {
				
				code2 += "1";
				
			} else {
				
				code2 += "0";
				
			}
			
			for (String c : codeKeys) {
				
				if (code2.equals(c)) {
					
					decompressed.write(rebuiltCodeMap.get(c));
					code2 = "";
					break;
					
				}
				
			}
			
		}
		
		reader.close();
		decompressed.close();
		
	}
	
	public void createCodeFile () throws IOException {
		
		Set<Character> keys = codeMap.keySet();
		for (char ch : keys) {
			
			newFile.write(codeMap.get(ch)+"\n"+ch+"\n");
			
		}
		
		newFile.close();
		
	}
	
	HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	HashMap<Character, String> codeMap = new HashMap<Character, String>();
	PriorityQueue<Branch> queue = new PriorityQueue<Branch>();
	FileWriter newFile = new FileWriter("codeFile");
	
	public Compressor () throws IOException {
		
		BufferedReader s = new BufferedReader (new FileReader("textFile"));
		
		int i;
		while ((i = s.read()) != -1) {
			
			Character ch = (char)(i);
			
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
		
		while (queue.size() != 1) {
		
			Node<Branch> branch1 = queue.pop();
			Node<Branch> branch2 = queue.pop();
			
			int priority = branch1.priority+branch2.priority;
		
			Branch<Character> b = new Branch<Character>(branch1.info, branch2.info, false);
			queue.add(b, priority);
			
		}
		
		//System.out.println(queue.size());
		
		createCode(queue.pop().info, "");
		printCode();
		compressFile();
		createCodeFile();
		decompressFile();
		//System.out.println(map);
		
	}
	
	public static void main(String[] args) throws IOException {

		new Compressor();

	}

}
