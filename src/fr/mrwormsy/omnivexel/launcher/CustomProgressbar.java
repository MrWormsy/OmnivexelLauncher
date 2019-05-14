package fr.mrwormsy.omnivexel.launcher;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JProgressBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class CustomProgressbar extends JProgressBar implements ChangeListener {

	private static final long serialVersionUID = -8725945615747348084L;

	private Image emptyTexture;
	private Image fullTexture;
	
	public CustomProgressbar(Image emptyTexture, Image fullTexture) {
		
		this.emptyTexture = emptyTexture;
		this.fullTexture = fullTexture;
		
		addChangeListener(this);
		
		repaint();
	}
	
	
	@Override
	public void stateChanged(ChangeEvent e) {

		repaint();
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(emptyTexture, 0, 0, this);
		g.drawImage(fullTexture, 0, 0, (int) (this.getWidth() * ((double) this.getValue() / this.getMaximum())), this.getHeight(), this);
	}
	
	
	
}
