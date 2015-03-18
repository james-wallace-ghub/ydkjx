package org.kjtw.process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.kjtw.structures.GameTemplate;
import org.kjtw.structures.QHeader;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;
import com.kreative.rsrc.MacResourceFile;
import com.kreative.rsrc.StringListResource;

public class SRFProcess {

Hashtable<String,String> parents = new Hashtable<String,String>();
Hashtable<String, byte[]> recallsave = new Hashtable<String, byte[]>();
Hashtable<String, String> recallstr = new Hashtable<String, String>();

Hashtable<String, byte[]> recalldata = new Hashtable<String, byte[]>();
	JTree tree = null;
	BerkeleyResourceFile rp;
	public SRFProcess(File file) throws IOException
	{
	    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Resources");
        
		RandomAccessFile raf = null;
		//Force read only
		try {
			raf = new RandomAccessFile(file, "r");
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
    			rp = null;
    			try {
    				rp = new BerkeleyResourceFile(file, "r", MacResourceFile.CREATE_NEVER);
    			} catch (IOException e) {
    				System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    				return;
    			}
    			
    				for (int type : rp.getTypes()) {
   						byte[] stuff = new byte[0];

    					String ftype = KSFLUtilities.fccs(type).trim();
    					DefaultMutableTreeNode ti = new DefaultMutableTreeNode(ftype);
    					top.add(ti);
    					if ( (ftype.contains("GT")) || ( (ftype.contains("t07") && !ftype.equals("Mt07"))))
   						{
	    					for (short id : rp.getIDs(type)) 
	    					{
    							MacResource r = rp.get(type, id);
        						DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+id);
        						ti.add(ti2);

        						GameTemplate gt = new GameTemplate(r.data);
		    					stuff = gt.toString().getBytes();
			    				recalldata.put(ftype+'_'+id, stuff);    								
			    				recallsave.put(ftype+'_'+id, r.data);    								
								if (!parents.containsKey(ftype))
								{
									parents.put(ftype, "template");
								}
	    				}
   					}	
    					else if (ftype.startsWith("TC"))
   						{
	    					for (short id : rp.getIDs(type)) 
	    					{
        						DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+id);
        						ti.add(ti2);

        						MacResource r = rp.get(type, id);
    							
    							String content = new String();
    							for (int i=0; i <r.data.length; i++)
    							{
    								content += r.data[i];
    							}
    							stuff = content.getBytes();
			    				recalldata.put(ftype+'_'+id, stuff);    								
								if (!parents.containsKey(ftype))
								{
									parents.put(ftype, "templ");
								}
	    					}
   						}
    					else if (ftype.equals("Nuke"))
   						{
    						MacResource r = rp.get(type, "128");
    						stuff = KSFLUtilities.copy(r.data, 4, r.data.length-4 );
			    			recalldata.put(ftype+'_'+128, stuff);    								

	    					if (!parents.containsKey(ftype))
							{
								parents.put(ftype, "qheadused");
							}								
   						}
    					else if (ftype.equals("Used"))
   						{
    						MacResource r = rp.get(type, "1");
    						stuff = r.data;
			    			recalldata.put(ftype+'_'+1, stuff);    								

	    					if (!parents.containsKey(ftype))
							{
								parents.put(ftype, "qheadused");
							}								
   						}
    					else if (ftype.equals("qhdr"))
   						{

	    					for (int id : rp.getfullIDs(type)) 
	    					{
    							MacResource r = rp.getFromFullID(type, id);
    							stuff = r.data;
    							QHeader qh = new QHeader(id,stuff,file);
    							
    							DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+qh.getName());
    							ti.add(ti2);
        						
	    						recallstr.put(ftype+'_'+qh.getName(), qh.toString(false));
	    						recallstr.put(ftype+'_'+qh.getName()+"j", qh.toString(true));
	    						recalldata.put(ftype+'_'+qh.getName(), qh.toString(false).getBytes());
	    					}

	    					if (!parents.containsKey(ftype))
							{
								parents.put(ftype, "qheader");
							}								
   						}
    						else
    						{
    	    					for (short id : rp.getIDs(type)) 
    	    					{
	        						DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+id);
	        						ti.add(ti2);
	
	    							MacResource r = rp.get(type, id);
	    							stuff = r.data;
	    							if (ftype.equals("off4"))
	    							{
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "gfx");
	    								}
	    								
	    							}
	    							if (( ftype.equals("3SEx") || ftype.contains("#")) && !ftype.equals("ANS#"))
	    							{
	    								StringListResource rstr = r.shallowRecast(StringListResource.class);
	    								if (rstr.getStringCount() ==0)
	    								{
		    								int numstrings = KSFLUtilities.getInt(r.data, 0);
		    								int seekpos = 4;
		    								String master="";
		    								for (int i=0; i < numstrings; i++)
		    								{
		    									int numchars = KSFLUtilities.getByte(r.data, seekpos);
		    									seekpos++;
		    									master+=new String(KSFLUtilities.copy(r.data, seekpos, numchars),"MACROMAN");
		    									master+=System.lineSeparator();
		    									seekpos+=numchars;
		    								}
		    								master = master.replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
		    								stuff=master.getBytes();
		    							}
	    								else
	    								{
		    								String[] strs = rstr.getStrings("MACROMAN");
		    								String master="";
		    								for (String result : strs)
		    								{
		    									master+=result.trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
		    								}
		    								stuff=master.getBytes();
	    								}
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "string");
	    								}
	    							}
	    							if (ftype.equals("ANS#"))
	    							{
	    								String ans = "";
	    								for (int i=0; i < r.data.length;i++)
	    								{
	    									ans+=r.data[i];
	    								}
	    								stuff = ans.getBytes();
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "string");
	    								}	
	    							}
	    							if (isstring(stuff))
	    							{
	    								if (!recalldata.containsKey(ftype+'_'+id))
   										{
	    									if (!parents.containsKey(ftype))
		    								{
		    									parents.put(ftype, "string");
		    								}	
   										}
	    							}
	    							else if (isaudio(stuff))
	    							{
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "audio");
	    								}
	    							}
	    							else
	    							{
	                                    if (!parents.containsKey(ftype))
	                                    {
	                                        parents.put(ftype, "string");
	                                    }
	    							}
	    							
	    						recalldata.put(ftype+'_'+id, stuff);
	    					}
	    				}
    				}
    		        tree = new JTree(top);
    		        tree.setShowsRootHandles(true);
    		        tree.setBounds(10, 24, 116, 193);
    			}
		}
	}		
	private static boolean isstring(byte[] stuff) 
	{
		if (stuff.length ==0)
		{
			return false;
		}
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
	
	public JTree getTree() {
		return tree;
	}
	public Hashtable<String,String> getParents() {
		return parents;
	}
	public Hashtable<String, byte[]> getData() {
		return recalldata;
	}
	public BerkeleyResourceFile getBRF() {
		return rp;
	}

	public Hashtable<String, byte[]> getSaves() {
		return recallsave;
	}
	public Hashtable<String, String> getStr() {
		// TODO Auto-generated method stub
		return recallstr;
	}
	}
