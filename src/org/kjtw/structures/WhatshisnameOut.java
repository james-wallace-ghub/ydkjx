package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

public class WhatshisnameOut extends YDKJQ {

	String title;
	String titlea;
	String titleb;

	String h1;
	String h2;
	String h3;
	String h4;
	
	String a1;
	String a2;
	String a3;
	String a4;

	String[] spellings;

	int answer;
	int value;
	public WhatshisnameOut(String wout, QHeader qh) throws IOException {
		Hashtable<String, byte[]> supplements = new Hashtable<String, byte[]>();
		SRFLoad QData = new SRFLoad(qh.getPath());
		supplements = QData.getData();
		spellings = QData.getStrs().get("Wrds_128");
    	File typedir = new File (wout+File.separator+"snd");
		typedir.mkdirs();
		answer = qh.getAnswer();
		value= qh.getDiff();
		a1 = new String (supplements.get("STR_3"));
		a2 = new String (supplements.get("STR_4"));
		a3 = new String (supplements.get("STR_5"));
		a4 = new String (supplements.get("STR_6"));

		h1 = new String (supplements.get("STR_7"));
		h2 = new String (supplements.get("STR_8"));
		h3 = new String (supplements.get("STR_9"));
		h4 = new String (supplements.get("STR_10"));

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

		title = qh.getTitle();
        if (qh.getForced() != null)
		{
			titlea =(new String(supplements.get("STR_18")));
			titleb=(new String(supplements.get("STR_19")));
		}

	}

}
