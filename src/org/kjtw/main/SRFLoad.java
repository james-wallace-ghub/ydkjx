package org.kjtw.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JTree;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.SoundResource;
import com.kreative.rsrc.StringListResource;

public class SRFLoad {

Hashtable<String,String> parents = new Hashtable<String,String>();
Hashtable<String, List<JackRawImage>> recallsave = new Hashtable<String, List<JackRawImage>>();
Hashtable<String, String[]> recallstr = new Hashtable<String, String[]>();
Hashtable<String, byte[]> recalldata = new Hashtable<String, byte[]>();

	JTree tree = null;

	public SRFLoad(String loc) throws IOException
	{
		File path = new File(loc);
		RandomAccessFile raf = null;
		//Force read only
		try {
			raf = new RandomAccessFile(path, "r");
		} catch (FileNotFoundException e) {
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

    					String ftype = KSFLUtilities.fccs(type).trim();
    					for (short id : rp.getIDs(type)) 
    					{
							MacResource r = rp.get(type, id);
							stuff = r.data;

    						if (ftype.equals("off4"))
       						{
	    						recallsave.put(ftype+'_'+id, new JackGraphic(r.data).getJri());
       						}
    						if ( ftype.equals("Dcoy") || ( ftype.equals("Mtch") || ( ftype.equals("Root") || ( ftype.equals("STR")))))
    						{
    							String str = new String(stuff,"MACROMAN").replaceAll("\\{", "");
								recallstr.put(ftype+'_'+id, str.split("\0"));
    						}
    							if (  ( ftype.equals("3SEx") || (ftype.contains("#") && !ftype.equals("ANS#"))))
    							{
	    								StringListResource rstr = r.shallowRecast(StringListResource.class);
	    								String[] strs = rstr.getStrings("MACROMAN");
	    								recallstr.put(ftype+'_'+id, strs);
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "stringlist");
	    								}
	    
	    							}
    							if ( ftype.equals("ANS#"))
    							{
    								if (!parents.containsKey(ftype))
    								{
    									parents.put(ftype, "ansstr");
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
	    							
	    						recalldata.put(ftype+'_'+id, stuff);
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
	
	public byte[] convert(SoundResource r) {
		return r.toWav();
	}
	public Hashtable<String,String> getParents() {
		return parents;
	}
	public Hashtable<String, byte[]> getData() {
		return recalldata;
	}

	public Hashtable<String, List<JackRawImage>> getGfx() {
		return recallsave;
	}
	public Hashtable<String, String[]> getStrs() {
		// TODO Auto-generated method stub
		return recallstr;
	}
	}
