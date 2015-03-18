package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

import com.kreative.ksfl.KSFLUtilities;

public class JackAttackOut extends YDKJQ {
	String title;
	String[] questions;
	String[] answers;
	String[] decoy1;
	String[] decoy2;
	String[] decoy3;

	public JackAttackOut(String wout, QHeader qh) throws IOException {
		SRFLoad QData = new SRFLoad(qh.getPath());
		Hashtable<String, byte[]> supplements = QData.getData();
		
		Hashtable<String, String[]> strings = QData.getStrs();
		String[] strsa = strings.get("STR_130");
		final String[] roots = strings.get("Root_128");
		final String[] match = strings.get("Mtch_128");
		final String[] decoy = strings.get("Dcoy_128");
		
		for (int i=0; i < strsa.length; i++)
		{
			int position = Integer.valueOf(strsa[0].substring(i, i+1));
			questions[i] = roots[position-1]; 
			answers[i] = match[position-1]; 
			decoy1[i] = decoy[position-1]; 
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
