package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import org.kjtw.process.SRFLoad;


public class PictureQuestionOut extends YDKJQ {

	String title;
	String titlea;
	String titleb;

	String text;
	String a1;
	String a2;
	String a3;
	String a4;
	
	int answer;
	int value;
	public PictureQuestionOut(String wout, QHeader qh, String pal) throws IOException {
		Hashtable<String, byte[]> supplements = new Hashtable<String, byte[]>();
		SRFLoad QData = new SRFLoad(qh.getPath());
		supplements = QData.getData();
    	File typedir = new File (wout+File.separator+"snd");
    	File typegfx = new File (wout+File.separator+"off4");
		typedir.mkdirs();
		typegfx.mkdirs();
		
		JackGraphic jgfx = QData.getGfx().get("off4_21500");

        jgfx.SetPalette(pal);
    	  File outputimage = new File(typegfx, 21500+".gif");

		  try {
			ImageIO.write(jgfx.toGif(jgfx.GetPalette()), "gif", outputimage);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	    			  		
  		  
          try {
	                File output = new File(typegfx, 21500+".json");
	                output.createNewFile();
	                PrintWriter out = new PrintWriter(output);
	                out.print(jgfx.getTileJSON());
	                out.print(jgfx.getFrameJSON());
	                out.close();
	            } catch (IOException e) {
	                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	            }

		
		answer = qh.getAnswer();
		text = new String (supplements.get("STR_2"));
		value= qh.getDiff();
		a1 = new String (supplements.get("STR_3"));
		a2 = new String (supplements.get("STR_4"));
		a3 = new String (supplements.get("STR_5"));
		a4 = new String (supplements.get("STR_6"));

		title = qh.getTitle();
		for (String key : supplements.keySet())
		{
			if (key.startsWith("snd"))
			{
		        try {
		            File output = new File(typedir.toString(),key.substring(4)+".wav");
		            output.createNewFile();
		            FileOutputStream fos = new FileOutputStream(output);
		            fos.write(supplements.get(key));
		            fos.close();
		        } catch (IOException e) {
		            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
		        }				
			}
		}

        if (qh.getForced() != null)
		{
			titlea =(new String(supplements.get("STR_18")));
			titleb=(new String(supplements.get("STR_19")));
		}

	}

}
