package org.kjtw.categories;


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

public class QHeadProcess {

Hashtable<String,QHeader> headtree = new Hashtable<String,QHeader>();

	JTree tree = null;

	public QHeadProcess(File file) throws IOException
	{
	    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Questions");

	    DefaultMutableTreeNode std = new DefaultMutableTreeNode("Standard Questions");
	    top.add(std);
	    DefaultMutableTreeNode norm = new DefaultMutableTreeNode("Normal 4 answer");
	    std.add(norm);
	    DefaultMutableTreeNode fitb = new DefaultMutableTreeNode("Fill in the blank");
	    std.add(fitb);
	    DefaultMutableTreeNode whn = new DefaultMutableTreeNode("Whatshisname?");
	    std.add(whn);
	    DefaultMutableTreeNode pic = new DefaultMutableTreeNode("Picture question");
	    std.add(pic);

	    DefaultMutableTreeNode dod = new DefaultMutableTreeNode("Dis or Dat (3 Way)");
	    DefaultMutableTreeNode fof = new DefaultMutableTreeNode("Fiber Optic Field Trip");
	    DefaultMutableTreeNode gib = new DefaultMutableTreeNode("Gibberish Questions");
	    DefaultMutableTreeNode jat = new DefaultMutableTreeNode("Jack Attack (HeadRush)");
	    top.add(dod);
	    top.add(fof);
	    top.add(gib);
	    top.add(jat);
	    
        
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

    			String fpath = file.getPath().replaceFirst(file.getName(), "");
    				for (int type : rp.getTypes()) {
   						byte[] stuff = new byte[0];

    					String ftype = KSFLUtilities.fccs(type).trim();
    					if (ftype.equals("qhdr"))
   						{
	    					for (int id : rp.getfullIDs(type)) 
	    					{
    							MacResource r = rp.getFromFullID(type, id);
    							stuff = r.data;
    							QHeader qh = new QHeader();
    							
    							String qheadnm = KSFLUtilities.fccs(id).trim();
    							qh.setName(qheadnm);
    							
    							qh.setValue((int)stuff[8]);
    							qh.setType((int)stuff[9]);

    							DefaultMutableTreeNode q = new DefaultMutableTreeNode(qheadnm);

    							int qtype = qh.getType();
    							// if 0 keep looking, else we can put this in a tree already
    							boolean qfound=false;
    							if (qtype !=0)
    							{
            						switch (qtype)
            						{
		    							case 2:
		    								gib.add(q);
		    								break;
		    							case 3:
		    								dod.add(q);
		    								break;
		    							case 4:
		    								jat.add(q);
		    								break;
		    							case 5:
		    								fof.add(q);
		    								break;
            						}
            						qfound=true;
    							}
    							qh.setSubType((int)stuff[11]);
    							int subtype = stuff[11];
    							if (!qfound)
    							{
    								switch (subtype)
	        						{
	        							case 1:
	        								norm.add(q);
	        								break;
	        							case 2:
	        								fitb.add(q);
	        								break;
	        							case 3:
	        								whn.add(q);
	        								break;
	        							case 4:
	        								pic.add(q);
	        								break;
	        						}
    							}
    							List<Byte> construct = new ArrayList<Byte>();
        						
        						byte[] titleconst = KSFLUtilities.copy(stuff, 16, 64);
        						
        						String title = new String(titleconst, "MACROMAN").trim();
        						
        						byte[] pathconst = KSFLUtilities.copy(stuff, 81, 64);
        						
        						String path = new String(pathconst, "MACROMAN").trim().replace(':', File.separatorChar);

        						qh.setTitle(title);
        						qh.setPath(fpath+path);

        						qh.setAnswer((int)stuff[146]);

        						if (stuff.length >150)
        						{
	
	        						if (stuff[150] != 0)
	        						{
	        							//forcing a question after this one.
	            						byte[] forceconst = KSFLUtilities.copy(stuff, 150, 4);
	            						String forcestr = new String(forceconst, "MACROMAN").trim();
	            						qh.setForcing(forcestr);
	        						}
	        						construct.clear();
	        						if (stuff[154] != 0)
	        						{
	        							//This was a forced question, don't display it.
	            						byte[] forceconst = KSFLUtilities.copy(stuff, 154, 4);
	            						String forcestr = new String(forceconst, "MACROMAN").trim();
	            						qh.setForced(forcestr);
	        						}
        						}        						
    					
	    					headtree.put(qheadnm, qh);
    				}
    		        tree = new JTree(top);
    		        tree.setShowsRootHandles(true);
    		        tree.setBounds(10, 24, 116, 193);
    			}
    		}
		}
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
	
	public Hashtable<String,QHeader> getTable()
	{
		return headtree;
	}
	public JTree getTree() {
		return tree;
	}
	}
