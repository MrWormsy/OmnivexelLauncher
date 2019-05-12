package fr.mrwormsy.omnivexel.launcher;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;

public class CustomCheckbox extends JCheckBox implements MouseListener {

	private static final long serialVersionUID = 5176759408412464897L;

	private boolean hovered;
	
	private Image textureNotSelected;
	private Image textureHoveredNotSelected;
	
	private Image textureSelected;
	private Image textureHoveredSelected;
	
	public CustomCheckbox(Image texture, Image textureHovered, Image textureSelected, Image textureHoveredSelected) {
		this.hovered = false;
		
		this.textureNotSelected = texture;
		this.textureHoveredNotSelected = textureHovered;
		this.textureSelected = textureSelected;
		this.textureHoveredSelected = textureHoveredSelected;
		
		addMouseListener(this);
		
		repaint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		hovered = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		hovered = false;
		repaint();
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
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if (hovered) {
			if (isSelected()) {
				g.drawImage(textureHoveredSelected, 0, 0, this);
			} else {
				g.drawImage(textureHoveredNotSelected, 0, 0, this);
			}
			
			
		} else {
			
			if (isSelected()) {
				g.drawImage(textureSelected, 0, 0, this);
			} else {
				g.drawImage(textureNotSelected, 0, 0, this);
			}
		}
	}

}
