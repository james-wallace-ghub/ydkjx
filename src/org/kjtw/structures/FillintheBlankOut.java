package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;


public class FillintheBlankOut extends YDKJQ{

	String title;
	String titlea;
	String titleb;

	String text;
	String a1;
	String a2;
	String a3;
	String a4;

	String[] spellings;
	
	String[] altanswers;
	int totalwords;
	int answer;
	int value;
	public FillintheBlankOut(String wout, QHeader qh) throws IOException {
		Hashtable<String, byte[]> supplements = new Hashtable<String, byte[]>();
		SRFLoad QData = new SRFLoad(qh.getPath());
		supplements = QData.getData();
		spellings = QData.getStrs().get("Wrds_128");

		String[] wordcnts = new String (supplements.get("Wrds_128")).split(",");

		totalwords = Integer.valueOf(wordcnts[0]);
		
		int[] altwordcounts = new int[totalwords];
		
		for (int i=1; i <wordcnts.length;i++)
		{
			altwordcounts[i-1] = Integer.valueOf(wordcnts[i]);
		}

		int pos=0;
		for (int i=0; i <totalwords; i++)
		{
			String tmp ="";
			for (int j=0; j < altwordcounts[i]; j++)
			{
				tmp += spellings[pos]+"|";
				pos++;
			}
			altanswers[i]=tmp;
		}
		File typedir = new File (wout+File.separator+"snd");
		typedir.mkdirs();
		answer = qh.getAnswer();
		text = new String (supplements.get("STR_2"));
		value= qh.getDiff();
		a1 = new String (supplements.get("STR_3"));
		a2 = new String (supplements.get("STR_4"));
		a3 = new String (supplements.get("STR_5"));
		a4 = new String (supplements.get("STR_6"));

		title = qh.getTitle();
        try {
            File output = new File(typedir.toString(),"1.wav");
            output.createNewFile();
            FileOutputStream fos = new FileOutputStream(output);
            fos.write(supplements.get("snd_1"));
            fos.close();
        } catch (IOException e) {
            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
        }

        
        if (supplements.get("snd_2") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"2.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_2"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }

        if (supplements.get("snd_7") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"7.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_7"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }

        if (supplements.get("snd_8") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"8.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_8"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }
        
        
        if (supplements.get("snd_9") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"9.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_9"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }
        
        if (supplements.get("snd_10") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"10.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_10"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }

        if (supplements.get("snd_11") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"11.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_11"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }

        if (supplements.get("snd_6") != null)
        {
	        try {
	            File output = new File(typedir.toString(),"6.wav");
	            output.createNewFile();
	            FileOutputStream fos = new FileOutputStream(output);
	            fos.write(supplements.get("snd_6"));
	            fos.close();
	        } catch (IOException e) {
	            System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
	        }
        }

        if (qh.getForced() != null)
		{
			titlea =(new String(supplements.get("STR_18")));
            try {
                File output = new File(typedir.toString(),"18.wav");
                output.createNewFile();
                FileOutputStream fos = new FileOutputStream(output);
                fos.write(supplements.get("snd_18"));
                fos.close();
            } catch (IOException e) {
                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
            }
			titleb=(new String(supplements.get("STR_19")));
            try {
                File output = new File(typedir.toString(),"19.wav");
                output.createNewFile();
                FileOutputStream fos = new FileOutputStream(output);
                fos.write(supplements.get("snd_19"));
                fos.close();
            } catch (IOException e) {
                System.err.println("Error: Cannot write file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
            }
		}

	}

}
