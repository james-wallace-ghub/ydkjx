package org.kjtw.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PaletteMaker {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();
        String indir = file.getAbsolutePath();
		BufferedImage out = null; 
        try (BufferedReader br = new BufferedReader(new FileReader(file)))
		{
        	out = new BufferedImage(256,1, BufferedImage.TYPE_INT_RGB);
			String sCurrentLine;
			int pos=0;
			while ((sCurrentLine = br.readLine()) != null) {
				String[] values = sCurrentLine.split(" ");
				Color ocol = new Color(Integer.valueOf(values[0]),Integer.valueOf(values[1]),Integer.valueOf(values[2]));
				out.setRGB(pos, 0, ocol.getRGB()); 
				pos++;
			} 
    		  File outputimage = new File("C:\\ydkj\\palette", "GENPALETTE.bmp");
					ImageIO.write(out, "bmp", outputimage);

		} catch (IOException e) {
			e.printStackTrace();
		} 
	}

}
