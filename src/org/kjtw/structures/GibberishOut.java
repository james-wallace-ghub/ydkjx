package org.kjtw.structures;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

public class GibberishOut extends YDKJQ {

	String title;
	String titlea;
	String titleb;

	String text;
	
	String h1;
	String h2;
	String h3;
	
	String ans;

	String[] altanswers;
	String[] spellings;
	int totalwords;

	public GibberishOut(String wout, QHeader qh) throws IOException {
		SRFLoad QData = new SRFLoad(qh.getPath());
		Hashtable<String, byte[]> supplements = QData.getData();
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
		
		text = new String (supplements.get("Gibr_128"));

		ans = new String (supplements.get("Ansr_128"));

		h1 = new String (supplements.get("Hnt1_128"));
		h2 = new String (supplements.get("Hnt2_128"));
		h3 = new String (supplements.get("Hnt3_128"));

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
			titlea =(new String(supplements.get("STR_128")));
			titleb=(new String(supplements.get("STR_129")));
		}

	}

}
