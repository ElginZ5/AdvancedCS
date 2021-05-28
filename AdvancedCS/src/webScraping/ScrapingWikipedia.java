package webScraping;

import org.jsoup.Jsoup;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

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

public class ScrapingWikipedia {
	
	String bodyContent; // the main text
	
	JTextArea displayArea;

	public ScrapingWikipedia () {
		
		final int WIDTH = 600;
		final int HEIGHT = 600;
		
		Color myColor = new Color(245, 245, 245);
		
		JFrame frame = new JFrame("Scraping Wikipedia"); // setting up the frame and jpanels
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(myColor);
		innerPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4)));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(myColor);
		bottomPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4)));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.white, 20));
		
		displayArea = new JTextArea(); // new display area
		displayArea.setEditable(false);
		
		JScrollPane scroll = new JScrollPane (displayArea,  // scroll so that the reader can read every bit of information acquired
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		scroll.setPreferredSize(new Dimension(WIDTH-40, ((int)(HEIGHT/1.7)-10)));
		
		JTextArea textArea = new JTextArea(); // text area to input the subject
		textArea.setEditable(true);
		textArea.setPreferredSize(new Dimension (WIDTH-40, (HEIGHT/4)-15));
		textArea.setText("Enter the name of a city or place");
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
		
		JButton search = new JButton("Get Wikipedia Information"); // the search button
		search.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String info = textArea.getText().trim();
				getWikipediaInformation(info); // calls getWikipedia information to get the information
				
				String name = "";
				
				for (int i = 0; i < bodyContent.length(); i++) { // loops through the text from wikipedia and cuts it to the next line once it reaches the end of the window
					
					if (i % 85 == 0 && i != 0) {
						
						if (bodyContent.charAt(i) == ' ') {
							
							name += "\n";
							
						} else {
							
							while ((bodyContent.charAt(i) != ' ') && (i < bodyContent.length()-1)) {
								
								name += bodyContent.charAt(i);
								i++;
								
							}
							
							name += "\n";	
							
						}
						
					} else {
						
						name += bodyContent.charAt(i);
						
					}
					
				}
				
				displayArea.setText(name);
				//displayArea.
					
					//displayArea.setText("Error. Please check if your place is spelt correctly and that it exists");
					
			
				
			}
			
		});
		
		innerPanel.add(textArea);
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
	
	private void getWikipediaInformation(String info) {
		
		try { // sets the spaces in the input to underscores so that wikipedia can accept this input

			String name = "";
			
			for (int i = 0; i < info.length(); i++) {
				
				if (info.charAt(i) == ' ') {
					
					name += "_";
					
				} else {
					
					name += info.charAt(i);
					
				}
				
			}
			
			//System.out.println(name);
			
			Document doc = Jsoup.connect("https://en.wikipedia.org/wiki/"+name).get();
			
			bodyContent = doc.getElementById("mw-content-text").select("p").text(); // bodycontent gets all of the text labeled as "p" or paragraph
			// this gets all the needed information on the wikipedia page and sets it to body content.
			
			
		} catch (IOException e) {
			
			displayArea.setText("Couldn't Connect");
			
		}
		
	}
	
	public static void main (String[] args) {
		
		new ScrapingWikipedia();
		
	}
	
}


