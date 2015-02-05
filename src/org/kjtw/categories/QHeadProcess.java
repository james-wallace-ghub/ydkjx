package org.kjtw.categories;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.kreative.ksfl.KSFLUtilities;
import com.kreative.rsrc.BerkeleyResourceFile;
import com.kreative.rsrc.MacResource;

public class QHeadProcess {

Hashtable<String,QHeader> headtree = new Hashtable<String,QHeader>();

	JTree tree = null;

	public QHeadProcess(BerkeleyResourceFile rp, String fpath) throws IOException
	{
	    DefaultMutableTreeNode top = new DefaultMutableTreeNode("Questions");

	    DefaultMutableTreeNode std = new DefaultMutableTreeNode("Standard Questions");
	    top.add(std);
	    DefaultMutableTreeNode norm = new DefaultMutableTreeNode("Normal 4 answer");
	    std.add(norm);
	    DefaultMutableTreeNode fitb = new DefaultMutableTreeNode("Fill in the blank");
	    std.add(fitb);
	    DefaultMutableTreeNode whn = new DefaultMutableTreeNode("Whatshisname? / Moldy Memories");
	    std.add(whn);
	    DefaultMutableTreeNode pic = new DefaultMutableTreeNode("Picture question");
	    std.add(pic);
	    DefaultMutableTreeNode saq = new DefaultMutableTreeNode("Super Audio Question");
	    std.add(saq);
	    DefaultMutableTreeNode imp = new DefaultMutableTreeNode("Impossible Question");
	    std.add(imp);
	    DefaultMutableTreeNode ghq = new DefaultMutableTreeNode("Guest Host / Pissed about a Question");
	    std.add(ghq);

	    DefaultMutableTreeNode dod = new DefaultMutableTreeNode("Dis or Dat");
	    DefaultMutableTreeNode ccc = new DefaultMutableTreeNode("Celebrity Collect Call");
	    DefaultMutableTreeNode fof = new DefaultMutableTreeNode("Fiber Optic Field Trip");
	    DefaultMutableTreeNode gib = new DefaultMutableTreeNode("Gibberish Questions");
	    DefaultMutableTreeNode jat = new DefaultMutableTreeNode("Jack Attack (HeadRush)");
	    DefaultMutableTreeNode twy = new DefaultMutableTreeNode("3Way");
	    DefaultMutableTreeNode unk = new DefaultMutableTreeNode("Unknown Questions");
	    top.add(dod);
	    top.add(ccc);
	    top.add(fof);
	    top.add(gib);
	    top.add(jat);
	    top.add(twy);
	    top.add(unk);
	    

    				for (int type : rp.getTypes()) {
   						byte[] stuff = new byte[0];

    					String ftype = KSFLUtilities.fccs(type).trim();
    					if (ftype.equals("Nuke"))
   						{
   						}
    					if (ftype.equals("qhdr"))
   						{
	    					for (int id : rp.getfullIDs(type)) 
	    					{
    							MacResource r = rp.getFromFullID(type, id);
    							stuff = r.data;
    							QHeader qh = new QHeader();
    							
    							String qheadnm = KSFLUtilities.fccs(id).trim();
    							qh.setName(qheadnm);
    							
    							int tempval = (int)stuff[8];
   								qh.setValue(tempval);
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
		    							case 10:
		    								ccc.add(q);
		    								break;
		    							case 12:
		    								twy.add(q);
		    								break;
		    							default:
		    								unk.add(q);
		    								break;
            						}
            						qfound=true;
    							}
       							int subtype = stuff[11];
    							qh.setSubType(subtype);
       							if (qh.value ==4)
    							{
       								imp.add(q);
       								qfound=true;
    							}
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
	        							case 5:
	        								saq.add(q);
	        								break;
	        							case 6:
	        								ghq.add(q);
	        								break;
	        							default:
	        								unk.add(q);
	        								break;
	        						}
    							}
    							List<Byte> construct = new ArrayList<Byte>();
        						
        						byte[] titleconst = KSFLUtilities.copy(stuff, 16, 63);
        						
        						String title = new String(titleconst, "MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß");
        						
        						byte[] pathconst = KSFLUtilities.copy(stuff, 81, 63);
        						
        						String path = new String(pathconst, "MACROMAN").trim().replace(':', File.separatorChar);

        						qh.setTitle(title);
        						qh.setPath(fpath+path);

        						if (stuff.length > 146)
        						{
            						qh.setAnswer((int)stuff[146]);
        						}
        						else
        						{
        							qh.setAnswer(0);
        						}

        						if (stuff.length >152)
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
	
	public Hashtable<String,QHeader> getTable()
	{
		return headtree;
	}
	public JTree getTree() {
		return tree;
	}
	}
