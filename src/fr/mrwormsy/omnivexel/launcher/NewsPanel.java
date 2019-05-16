package fr.mrwormsy.omnivexel.launcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class NewsPanel extends JPanel {

	private static final long serialVersionUID = -3363906031496156336L;
	private Image newsBg = (new ImageIcon(LauncherFrame.class.getResource("/fr/mrwormsy/omnivexel/launcher/resources/news.png")).getImage());
	
	public NewsPanel() {
		
		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);   

		try {
		  jep.setPage("http://51.75.254.98/update.html");
		}catch (IOException e) {
		  jep.setContentType("text/html");
		  jep.setText("<html>Could not load</html>");
		} 

		JScrollPane scrollPane = new JScrollPane(jep);
		
		this.setSize(420, 430);
		this.setLayout(null);
		this.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		jep.setBorder(null);
		//jep.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		
		scrollPane.setBounds(15, 15, 390, 400);
		scrollPane.setBorder(null);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		JScrollBar scrollBar = new JScrollBar(JScrollBar.VERTICAL);
		scrollBar.setBackground(new Color(205, 214, 156));
		scrollBar.setUI(new CustomScrollBar());
		scrollBar.setBorder(null);
		scrollBar.setUnitIncrement(25);
		scrollPane.setVerticalScrollBar(scrollBar);
		
		this.add(scrollPane);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.drawImage(newsBg, 0, 0, this.getWidth(), this.getHeight(), this);
		
		
	}
	
}
