package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

import com.kreative.ksfl.KSFLUtilities;

public class ThreeWayOut extends YDKJQ {
	String title;
	String[] options;
	String[] questions;
	
	byte[] answers;
	
	public ThreeWayOut(String wout, QHeader qh) throws IOException {
		SRFLoad QData = new SRFLoad(qh.getPath());
		Hashtable<String, byte[]> supplements = QData.getData();
		
		byte[] ans = supplements.get("ANS#_3");
		answers = KSFLUtilities.copy(ans,4,ans.length-4);

		String[] q = QData.getStrs().get("STR#_2");

		options = new String[3];
		
		for (int i=0; i <3; i++)
		{
			options[i] = q[i];
		}

		for (int i=3; i <q.length; i++)
		{
			questions[i-3] = q[i];
		}

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
