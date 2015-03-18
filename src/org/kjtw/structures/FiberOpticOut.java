package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;


public class FiberOpticOut extends YDKJQ {

	String title;
	String actualtitle;
	String text;
		
	String a1;
	String a2;
	String a3;
	String a4;
	
	String region;
	String subregion;
	String name;
	
	int answer;
	public FiberOpticOut(String wout, QHeader qh) throws IOException {
		Hashtable<String, byte[]> supplements = new Hashtable<String, byte[]>();
		SRFLoad QData = new SRFLoad(qh.getPath());
		supplements = QData.getData();
    	File typedir = new File (wout+File.separator+"snd");
		typedir.mkdirs();
		answer = qh.getAnswer();
		text = new String (supplements.get("STR_2"));
		a1 = new String (supplements.get("STR_3"));
		a2 = new String (supplements.get("STR_4"));
		a3 = new String (supplements.get("STR_5"));
		a4 = new String (supplements.get("STR_6"));

		region = new String (supplements.get("STR_7"));
		subregion = new String (supplements.get("STR_8"));
		name = new String (supplements.get("STR_9"));
		
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

	}

}
