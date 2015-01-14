package org.kjtw.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JackGFXPanel extends JPanel {

	private Image image;
	public JackGFXPanel(BufferedImage imgout) {
		image = imgout;
		setBounds(0, 0, 640, 480);
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 640, 480, null);

//        g.drawImage(image, 0, 0, null); // see javadoc for more info on the parameters            
    }
	public void setImage(BufferedImage imgout) {
		image = imgout;
		repaint();
	}
}
