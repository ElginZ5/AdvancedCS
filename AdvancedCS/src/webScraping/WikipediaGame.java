package webScraping;

import org.jsoup.Jsoup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import djikstras.LocationGraph.Edge;
import djikstras.LocationGraph.Vertex;

public class WikipediaGame {
	
	JTextArea displayArea;
		
	HashMap<String, String> neighbors = new HashMap<String, String>(); // hashmap neighbors to get the neighbor of a link
 
	public WikipediaGame () {
		
		final int WIDTH = 500;
		final int HEIGHT = 410;
		
		Color myColor = new Color(245, 245, 245);
		
		JFrame frame = new JFrame("Wikipedia Game"); //sets up frame and panels
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(myColor);
		innerPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4-5)));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(myColor);
		bottomPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4)));
		//bottomPanel.setBorder(BorderFactory.createLineBorder(Color.white, 20));
		
		displayArea = new JTextArea(); //display area that displays the path
		displayArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (displayArea,  // scroll bar
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scroll.setPreferredSize(new Dimension(WIDTH-40, ((int)(HEIGHT/1.7)-10)));
		
		JTextArea textArea = new JTextArea(); // text area for entering the two you want to find the path of
		textArea.setEditable(true);
		textArea.setPreferredSize(new Dimension (WIDTH/2-15, (HEIGHT/4)-15));
		textArea.setText("Enter the first entry");
		textArea.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				textArea.setText("");
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JTextArea textArea2 = new JTextArea();
		textArea2.setEditable(true);
		textArea2.setPreferredSize(new Dimension (WIDTH/2-15, (HEIGHT/4)-15));
		textArea2.setText("Enter the second entry");
		textArea2.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				textArea2.setText("");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		JButton search = new JButton("Find Path"); // button that finds the path
		search.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String info = textArea.getText().trim();
				String info2 = textArea2.getText().trim();
				//System.out.println(info2);
				try {
					getWikipediaInformation(info, info2);
				} catch (URISyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//String name = "";
				

				//displayArea.setText(name);	
				
			}
			
		});
		
		innerPanel.add(textArea);
		innerPanel.add(textArea2);
		bottomPanel.add(search);
	
		panel.add(innerPanel);
		panel.add(scroll);
		panel.add(bottomPanel);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		
	}
	
	private void getWikipediaInformation(String info, String info2) throws URISyntaxException { // gets the path
		
		try {

			String displayAreaText = "";
			
			String name = "";
			String name2 = "";

			for (int i = 0; i < info.length(); i++) { // changes the spaces in the inputs to underscores to match the wikipedia urls
				
				if (info.charAt(i) == ' ') {
					
					name += "_";
					
				} else {
					
					name += info.charAt(i);
					
				}
				
			}
			
			for (int j = 0; j < info2.length(); j++ ) {
				
				if (info2.charAt(j) == ' ') {
					
					name2 += "_";
					
				} else {
					
					name2 += info2.charAt(j);
					
				}
				
			}
			
			//System.out.println(name);
			
			//String[] targets = info2.split(" ");
			
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/"+name).get();
			String startLink = "https://en.wikipedia.org/wiki/"+name;
			
			Document doc2 = Jsoup.connect("https://en.wikipedia.org/wiki/"+name2).get();
			String targetLink = "https://en.wikipedia.org/wiki/"+name2;
			
			//System.out.println(startLink);
			//System.out.println(targetLink);
			//links = doc.select("a");
			//System.out.println(links);
			//Elements e = links.select("div#tocright");
			
			//System.out.println(BFS(startLink, targetLink));
			ArrayList<String> wikiPath = BFS(startLink, targetLink); // calls BFS with the two links
			
			//System.out.println(wikiPath.get(0).substring(30));
			
			for (int i = 0; i < wikiPath.size(); i++) { // displays the path after calling BFS
				
				//System.out.println(i);
				String wikiText = wikiPath.get(i).substring(30);
				displayAreaText += wikiText + " leads to ";
				
			}
			
			displayAreaText = displayAreaText.substring(0, displayAreaText.lastIndexOf(" leads to "));
			
			displayArea.setText(displayAreaText);
			
			
		} catch (IOException e) {
			
			displayArea.setText("Couldn't Connect");
			
		}
		
	}
	
	public ArrayList<String> BFS (String start, String target) throws URISyntaxException { // BFS
		
		try {
			
			String targetT = target.substring(30); // the target
			String[] targets = targetT.split("_");
			
			ArrayList<String> toVisit = new ArrayList<String>(); // to visit
			toVisit.add(start);
			
			HashSet<String> visited = new HashSet<String>(); // visited
			visited.add(start);
			
			HashMap<String, Element> leadsTo = new HashMap<String, Element>(); // leads to
			
			while (!toVisit.isEmpty()) { // if we havent visited everything
				
				String startLink = toVisit.remove(0); // startlink is the first element in toVisit
				//System.out.println(startLink);
				
				Document doc = Jsoup.connect(startLink).get();
				Elements links = doc.select("a"); // gets the links of startlink
				
				for (Element l : links) {
					
					String neighbor = getNeighbor(startLink, l); // gets the neighbor of each link in links
					
					//String[] neighborInfo = neighbor.;
					
					if (visited.contains(neighbor)) continue; // if it contains the neighbor already
					
					leadsTo.put(neighbor, l); // puts the neighbor and link in leads to
					
					//System.out.println(neighbor);
					
					if (neighbor == null) {
						
						continue;
						
					} 
					
					for (String s : targets) { 
					
						if (neighbor.equals(target) || neighbor.contains(targetT) || neighbor.contains(s)) { // if the neighbor contains even one word in the target
						
							return backtrace(start, neighbor, leadsTo); //returns the backtrace
						
						}
						
					}
						
						toVisit.add(neighbor); // adds neighbor to toVisit and neighbor to visited if the neighbor was not our target
						visited.add(neighbor);
					
					
				}
				
				
				
			}
			
			
		} catch (IOException e) {
			
			displayArea.setText("Couldn't Connect");
			
		}
		return null;
		
	}
	
	public ArrayList<String> backtrace(String start, String target, HashMap<String, Element> leadsTo) throws URISyntaxException { // backtrace method
		
		//back trace
		//System.out.println(leadsTo);
		String curr = target; // string curr is initialized as target
		ArrayList<String> path = new ArrayList<String>(); // new arraylist path
		
		while (leadsTo.get(curr) != null) { // while the link is not null
			path.add(0, curr); // adds the string to the path
			curr = neighbors.get(curr); // sets curr as its neighbor (gotten from the hashmap
			//System.out.println(path);
		}
		path.add(0, start); // adds the first vertex at the start of the path
		//System.out.println("PATH"+path);
		return path;
		
	}
	
	public String getNeighbor(String start, Element l) throws URISyntaxException { // get neighbor
		
		try {
			
			String link = l.absUrl("href"); // gets the link/neighbor
			//System.out.println(link);
			//System.out.println("L: "+l);
			URL u = new URL(link); // checks if the link is valid
			u.toURI();
			Document doc = Jsoup.connect(link).get();
			neighbors.put(link, start); // puts in the link and the start in the hashmap
			neighbors.put(start, link); // puts in the start and the link in the hashmap
			//System.out.println(neighbors);
			return link; //returns the neighbor
			
			
		} catch (IOException e) {
			
			return null;
			//displayArea.setText("Couldn't Connect");
			
		}
		
		//return null;
		
	}
	
	public static void main (String[] args) {
		
		new WikipediaGame();
		
	}
	
}


