package compression;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class LetterFrequency {
	
	HashMap<Character, Integer> map = new HashMap<Character, Integer>();
	
	public LetterFrequency () throws IOException {
		
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

		System.out.println(map);
		
	}
	
	public static void main(String[] args) throws IOException {

		new LetterFrequency();

	}

}
