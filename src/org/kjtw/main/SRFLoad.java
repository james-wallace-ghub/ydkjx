package org.kjtw.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.SoundResource;
import com.kreative.rsrc.StringListResource;

public class SRFLoad {

Hashtable<String,String> parents = new Hashtable<String,String>();
Hashtable<String, Byte[]> recallsave = new Hashtable<String, Byte[]>();
Hashtable<String, Byte[]> recalldata = new Hashtable<String, Byte[]>();

	JTree tree = null;

	public SRFLoad(String loc) throws IOException
	{
		File path = new File(loc);
		RandomAccessFile raf = null;
		//Force read only
		try {
			raf = new RandomAccessFile(path, "r");
		} catch (FileNotFoundException e) {
		    JOptionPane.showMessageDialog(null, "Resource tree can't be made, that file doesn't exist.");
			e.printStackTrace();
		}
		if (raf != null)
		{
    		byte[]packet = new byte[4];
    		raf.read(packet, 0, 4);
    		if (issrf(packet))
    		{
    			BerkeleyResourceFile rp = null;
    			try {
    				rp = new BerkeleyResourceFile(path, "r", MacResourceFile.CREATE_NEVER);
    			} catch (IOException e) {
    				System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    				return;
    			}
    			
    				for (int type : rp.getTypes()) {
   						byte[] stuff = new byte[0];
   						byte[] save = new byte[0];

    					String ftype = KSFLUtilities.fccs(type).trim();
    					if (ftype.equals("off4"))
   						{

   						}
    					else	for (short id : rp.getIDs(type)) 
    	    					{
	
	    							MacResource r = rp.get(type, id);
	    							stuff = r.data;
	    							if (( ftype.equals("3SEx") || ftype.contains("#")) && !ftype.equals("ANS#"))
	    							{
	    								StringListResource rstr = r.shallowRecast(StringListResource.class);
	    								String[] strs = rstr.getStrings("MACROMAN");
	    								String master="";
	    								for (String result : strs)
	    								{
	    									master+=result+System.lineSeparator();
	    								}
	    								stuff=master.getBytes();
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "string");
	    								}
	    
	    							}
	    							if ( ftype.equals("Wrds"))
	    							{
	    								stuff = new String(stuff,"MACROMAN").replaceAll("\\{", "").getBytes();
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "string");
	    								}									    								
	    							}
	    							if (isstring(stuff))
	    							{
	    								stuff = new String(stuff,"MACROMAN").replaceAll("\\{", "").getBytes();
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "string");
	    								}								
	    							}
	    							else if (isaudio(stuff))
	    							{
	    								SoundResource rsnd = r.shallowRecast(SoundResource.class);
	    								stuff = convert(rsnd);
	    								save = toAIFF(rsnd);
	    								
	    								Byte[] recall = new Byte[save.length];
	    								for (int i=0; i < save.length; i++)
	    								{
	    									recall[i] = save[i];
	    								}
	    
	    								recallsave.put(ftype+'_'+id, recall);
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "audio");
	    								}
	    							}
	    							else
	    							{
	                                    if (!parents.containsKey(ftype))
	                                    {
	                                        stuff = new String(stuff,"MACROMAN").getBytes();
	                                        parents.put(ftype, "string");
	                                    }
	    							}
	    							
	    						Byte[] recall = new Byte[stuff.length];
	    						for (int i=0; i < stuff.length; i++)
	    						{
	    							recall[i] = stuff[i];
	    						}
	    						recalldata.put(ftype+'_'+id, recall);
	    					}
	    				}
    				}
    			}
		}

	private static boolean isstring(byte[] stuff) 
	{
		if (( stuff.length > 5) && isaudio(stuff))
		{
			return false;
		}
		if ((stuff[stuff.length-1] == 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	private static boolean isaudio(byte[] stuff) 
	{
		if (stuff.length < 5)
		{
			return false;
		}
		if ((stuff[0] == 0) && (stuff[1] == 1) && (stuff[2] == 0) && (stuff[3] == 1) && (stuff[5] == 5))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	private static boolean issrf(byte[] packet) 
	{
		if ((packet[0] == 115) && (packet[1] == 114) && (packet[2] == 102) && (packet[3] == 49)) //srf1 in byte form
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public byte[] toAIFF(SoundResource r) {
		return r.toAiff();
}
	
	public byte[] convert(SoundResource r) {
		return r.toWav();
	}
	public Hashtable<String,String> getParents() {
		return parents;
	}
	public Hashtable<String, Byte[]> getData() {
		return recalldata;
	}
	public Hashtable<String, Byte[]> getSaves() {
		return recallsave;
	}
	}
