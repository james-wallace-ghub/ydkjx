package org.kjtw.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

import javax.swing.JTree;

import org.kjtw.structures.JackGraphic;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.SoundResource;
import com.kreative.rsrc.StringListResource;

public class SRFLoad {

Hashtable<String,String> parents = new Hashtable<String,String>();
Hashtable<String, JackGraphic> recallsave = new Hashtable<String, JackGraphic>();
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
	    						recallsave.put(ftype+'_'+id, new JackGraphic(r.data));
       						}
    						if ( ftype.equals("Dcoy") || ( ftype.equals("Mtch") || ( ftype.equals("Root") || ( ftype.equals("STR")))))
    						{
    							String str = new String(stuff,"MACROMAN").replaceAll("\\{", "").replaceAll("\u2211" , "ß");
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
	    								String databuff="";
	    								stuff = KSFLUtilities.copy(r.data, 1, r.data.length-1);
	    								databuff +=r.data[0]+",";
	    								String[] strs = new String(stuff,"MACROMAN").split("\0");
	    								for (int i=0; i < strs.length; i++)
	    								{
	    									byte[] totals = (strs[i].substring(0, 1)).getBytes();
	    									
	    									if (totals[0] <20)
	    									{
	    										databuff+=totals[0]+",";
	    										strs[i] = strs[i].substring(1,strs[i].length()).trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
	    									}
	    									else
	    									{
	    										strs[i] = strs[i].trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
	    									}
	    								}
	    								recalldata.put(ftype+'_'+id, databuff.getBytes());
	    								recallstr.put(ftype+'_'+id, strs);
	    							}
	    							if (isstring(stuff))
	    							{
	    								stuff = new String(stuff,"MACROMAN").replaceAll("\\{", "").replaceAll("\u2211" , "ß").getBytes();
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

	public Hashtable<String, JackGraphic> getGfx() {
		return recallsave;
	}
	public Hashtable<String, String[]> getStrs() {
		// TODO Auto-generated method stub
		return recallstr;
	}
	}
