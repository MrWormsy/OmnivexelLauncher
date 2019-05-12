package fr.mrwormsy.omnivexel.launcher;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class CustomButton extends JButton implements MouseListener {
	private static final long serialVersionUID = 759803634522406155L;
	
	private boolean hovered;
	
	private Image texture;
	private Image textureHovered;
	
	public CustomButton(Image texture, Image TextureHovered) {
		this.hovered = false;
		
		setTexture(texture);
		setTextureHovered(TextureHovered);
		
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
			g.drawImage(textureHovered, 0, 0, this);
		} else {
			g.drawImage(texture, 0, 0, this);
		}
	}

	public Image getTexture() {
		return texture;
	}

	public void setTexture(Image texture) {
		this.texture = texture;
	}

	public Image getTextureHovered() {
		return textureHovered;
	}

	public void setTextureHovered(Image textureHovered) {
		this.textureHovered = textureHovered;
	}

}
