package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

import com.kreative.ksfl.KSFLUtilities;

public class DisorDatOut extends YDKJQ {
	String title;
	String text;
	boolean opt3needed=false;
	String[] questions;
	
	byte[] answers;
	
	public DisorDatOut(String wout, QHeader qh) throws IOException {
		SRFLoad QData = new SRFLoad(qh.getPath());
		Hashtable<String, byte[]> supplements = QData.getData();
		
		byte[] ans = supplements.get("ANS#_4");
		answers = KSFLUtilities.copy(ans,4,ans.length-4);

		for (byte b : answers)
		{
			if (b ==3)
			{
				opt3needed=true;
			}
		}
		questions = QData.getStrs().get("STR#_3");

		File typedir = new File (wout+File.separator+"snd");
		typedir.mkdirs();
		

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
