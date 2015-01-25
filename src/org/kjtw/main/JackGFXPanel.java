package org.kjtw.main;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class JackGFXPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2291934827966122735L;
	private Image image;
	private boolean scaleflag = false;
	public JackGFXPanel(BufferedImage imgout) {
		image = imgout;
		if ( (imgout.getWidth() >639)|| (imgout.getHeight() >479))
		{
			scaleflag=true;
		}
		setBounds(0, 0, 640, 480);
	}
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (scaleflag)
        {
        	g.drawImage(image,0,0,640,480,null);
        }
        else
        {
        	g.drawImage(image, 0, 0, null);            
        }
    }
	public void setImage(BufferedImage imgout) {
		image = imgout;
		repaint();
	}
}
