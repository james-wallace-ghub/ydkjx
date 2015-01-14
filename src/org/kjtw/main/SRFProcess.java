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

public class SRFProcess {

Hashtable<String,String> parents = new Hashtable<String,String>();
Hashtable<String, Byte[]> recallsave = new Hashtable<String, Byte[]>();
Hashtable<String, Byte[]> recalldata = new Hashtable<String, Byte[]>();
Hashtable<String, JackGraphic> gfx = new Hashtable<String, JackGraphic>();
	JTree tree = null;

	public SRFProcess(File file) throws IOException
	{
		boolean showanswer = true;
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
    			BerkeleyResourceFile rp = null;
    			try {
    				rp = new BerkeleyResourceFile(file, "r", MacResourceFile.CREATE_NEVER);
    			} catch (IOException e) {
    				System.err.println("Error: Invalid file ("+e.getClass().getSimpleName()+": "+e.getMessage()+")");
    				return;
    			}
    			
    				for (int type : rp.getTypes()) {
   						byte[] stuff = new byte[0];
   						byte[] save = new byte[0];

    					String ftype = KSFLUtilities.fccs(type).trim();
    					DefaultMutableTreeNode ti = new DefaultMutableTreeNode(ftype);
    					top.add(ti);

    					if (ftype.equals("qhdr"))
   						{
	    					for (int id : rp.getfullIDs(type)) 
	    					{
    							MacResource r = rp.getFromFullID(type, id);
    							stuff = r.data;
    							
    							String qheadnm = KSFLUtilities.fccs(id).trim();
    							
        						DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+qheadnm);
        						ti.add(ti2);

    							//special case for question header strings
        						int value = stuff[8]; // equals value in 1000's of question in Round 1
        						int qtype = stuff[9];// 0 = Standard Question, 1 is skipped?, 2 = Gib, 3 = DisOrDat/3Way, 4 = JackAttack/HeadRush, 5 = Fiber/CCC/PubQuiz
        						String qtypedef="";
        						switch (qtype)
        						{
        							case 0:
        								qtypedef = "standard";
        								break;
        							case 1:
        								qtypedef = "unknown";
        								break;
        							case 2:
        								qtypedef = "Gibberish";
        								break;
        							case 3:
        								qtypedef = "Dis or Dat / 3Way";
        								break;
        							case 4:
        								qtypedef = "Jack Attack / HeadRush";
        								break;
        							case 5:
        								qtypedef = "Fiber Optic Field Trip/ Pub Quiz / Celebrity Collect Call";
        								break;
        						}
        						int subtype = stuff[11];
        						String subtypedef="";
        						switch (subtype)
        						{
        							case 0:
        								subtypedef = "Special type";
        								break;
        							case 1:
        								subtypedef = "Normal 4 answer question";
        								break;
        							case 2:
        								subtypedef = "Fill in the blank";
        								break;
        							case 3:
        								subtypedef = "Whatshisname?";
        								break;
        							case 4:
        								subtypedef = "Picture question";
        								break;
        						}
        						
        						byte[] titleconst = KSFLUtilities.copy(stuff, 16, 64);
        						        						
        						String title = new String(titleconst, "MACROMAN").trim();
        						
        						
        						byte[] pathconst = KSFLUtilities.copy(stuff, 81, 64);
        						
        						String path = new String(pathconst, "MACROMAN").trim().replace(':', File.separatorChar);
        						int rightanswer = stuff[146];//relates to correct choice, or position of right answer for FITB
        						String qdata = "Question title: "+title+System.lineSeparator()+
        								"location : "+path+System.lineSeparator()+
        								"Round 1 Value ="+value+",000"+System.lineSeparator()+
        								"Question type ="+qtypedef+System.lineSeparator()+
        								"Subtype ="+subtypedef+System.lineSeparator();
        						if (showanswer)
        						{
        							qdata +="Correct answer number ="+rightanswer+System.lineSeparator();
        						}

        						if (stuff.length >150)
        						{
	        						if (stuff[150] != 0)
	        						{
	        							//forcing a question after this one.
	            						byte[] forceconst = KSFLUtilities.copy(stuff, 150, 4);
	            						String forcestr = new String(forceconst, "MACROMAN").trim();
	            						
	        							qdata +="This question forces "+forcestr+" to appear next"+System.lineSeparator();
	        						}
	        						if (stuff[154] != 0)
	        						{
	        							//This was a forced question, don't display it.
	            						byte[] forceconst = KSFLUtilities.copy(stuff, 154, 4);
	            						String forcestr = new String(forceconst, "MACROMAN").trim();
	            						
	        							qdata +="This question is forced by "+forcestr+System.lineSeparator();
	        						}
        						}        						
        						stuff = qdata.getBytes();
	    						Byte[] recall = new Byte[stuff.length];
	    						for (int i=0; i < stuff.length; i++)
	    						{
	    							recall[i] = stuff[i];
	    						}
	    						recalldata.put(ftype+'_'+qheadnm, recall);
    						}
							if (!parents.containsKey(ftype))
							{
								parents.put(ftype, "string");
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
	    								JackGraphic jgfx = new JackGraphic(r.data);
	    								String warn =  "this is gfx";
	    								gfx.put(ftype+'_'+id, jgfx);
	    								stuff = warn.getBytes();
	    								if (!parents.containsKey(ftype))
	    								{
	    									parents.put(ftype, "gfx");
	    								}
	    								
	    							}
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
	    							if (isstring(stuff))
	    							{
	    								stuff = new String(stuff,"MACROMAN").getBytes();
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
    		        tree = new JTree(top);
    		        tree.setShowsRootHandles(true);
    		        tree.setBounds(10, 24, 116, 193);
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
	public JTree getTree() {
		return tree;
	}
	public Hashtable<String,String> getParents() {
		return parents;
	}
	public Hashtable<String, Byte[]> getData() {
		return recalldata;
	}
	public Hashtable<String, JackGraphic> getGfx() {
		return gfx;
	}

	public Hashtable<String, Byte[]> getSaves() {
		return recallsave;
	}
	}
