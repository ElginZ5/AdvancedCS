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
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeatherInformation {
	
	String time;
	String temperature;
	String precipitation;
	String humidity;
	String wind;
	String weather;
	
	JTextArea displayArea;

	public WeatherInformation () {
		
		final int WIDTH = 600;
		final int HEIGHT = 600;
		
		Color myColor = new Color(245, 245, 245);
		
		JFrame frame = new JFrame("Weather Fetcher");
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		JPanel innerPanel = new JPanel();
		innerPanel.setBackground(myColor);
		innerPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4)));
		
		JPanel bottomPanel = new JPanel();
		bottomPanel.setBackground(myColor);
		bottomPanel.setPreferredSize(new Dimension(WIDTH, (HEIGHT/4)));
		bottomPanel.setBorder(BorderFactory.createLineBorder(Color.white, 20));
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		displayArea.setPreferredSize(new Dimension(WIDTH-40, ((int)(HEIGHT/1.7)-10)));
		displayArea.setBackground(Color.white);
		displayArea.setBorder(BorderFactory.createLineBorder(myColor, 10));
		displayArea.setText("");
		
		JTextArea textArea = new JTextArea();
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
		
		JButton search = new JButton("Get Weather Information");
		search.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				String cityName = textArea.getText().trim();
				getWeatherInformation(cityName);
				
				if (!(time.equals("")&&
						temperature.equals("")&&
						precipitation.equals("")&&
						humidity.equals("")&&
						wind.equals("")&&
						weather.equals(""))) {
					
					displayArea.setText("The time in "+cityName+" is: "+time+"\n\n"+
						"The temperature in "+cityName+" is: "+temperature+" degrees celcius \n\n"+
						"In "+cityName+", "+precipitation+"\n\n"+
						"In "+cityName+", "+humidity+"\n\n"+
						"In "+cityName+", "+wind+"\n\n"+
						"The weather in "+cityName+" is: "+weather);
					
				} else {
					
					displayArea.setText("Error. Please check if your place is spelt correctly and that it exists");
					
				}
				
			}
			
		});
		
		innerPanel.add(textArea);
		bottomPanel.add(search);
		
		panel.add(innerPanel);
		panel.add(displayArea);
		panel.add(bottomPanel);
		
		frame.add(panel);
		frame.setSize(WIDTH, HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setFocusable(true);
		frame.setVisible(true);
		
	}
	
	private void getWeatherInformation(String cityName) {
		
		try {

			String name = "";
			
			for (int i = 0; i < cityName.length(); i++) {
				
				if (cityName.charAt(i) == ' ') {
					
					name += "+";
					
				} else {
					
					name += cityName.charAt(i);
					
				}
				
			}
			
			//System.out.println(name);
			
			Document doc = Jsoup.connect("https://www.bing.com/search?q=weather+in+"+name).get();
			
			time = doc.getElementsByClass("wtr_dayTime").text();
			temperature = doc.getElementsByClass("wtr_currTemp b_focusTextLarge").text();
			precipitation = doc.getElementsByClass("wtr_currPerci").text();
			humidity = doc.getElementsByClass("wtr_currHumi").text();
			wind = doc.getElementsByClass("wtr_currWind").text();
			weather = doc.getElementsByClass("wtr_caption").text();
			
		} catch (IOException e) {
			
			displayArea.setText("Couldn't Connect");
			
		}
		
	}
	
	public static void main (String[] args) {
		
		new WeatherInformation();
		
	}
	
}


