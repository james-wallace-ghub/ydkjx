package org.kjtw.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

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
Hashtable<String, byte[]> recallsave = new Hashtable<String, byte[]>();
Hashtable<String, byte[]> recalldata = new Hashtable<String, byte[]>();
	JTree tree = null;
	BerkeleyResourceFile rp;
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
    							stuff = r.data;
    							
        						DefaultMutableTreeNode ti2 = new DefaultMutableTreeNode(""+id);
        						ti.add(ti2);

    							int questnum = KSFLUtilities.getInt(stuff, 0);
    							StringBuilder sb = new StringBuilder();
    							for (int i=0; i <questnum; i++)
    							{
    								sb.append("Question "+(i+1)+":"+System.lineSeparator());
    								int seekval =4 + (i*20);
    								sb.append("Set to use :");
    								seekval+=12;
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ALL"+System.lineSeparator());
    								}
    								else
    								{
    									sb.append(stuff[seekval]+System.lineSeparator());
    								}
    								seekval++;
    								sb.append("Intro to use :");
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ALL"+System.lineSeparator());
    								}
    								else
    								{
    									sb.append(stuff[seekval]+System.lineSeparator());
    								}
    								sb.append("Question Type :");
    								seekval++;
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ANY"+System.lineSeparator());
    								}
    								else
    								{
    									switch (stuff[seekval])
    									{
    										case 0:
    										{
    	    									sb.append("Standard(Shorty)"+System.lineSeparator());
    	    									break;
    										}
    										case 2:
    										{
    	    									sb.append("Gibberish"+System.lineSeparator());    											
    	    									break;
    										}
    										case 3:
    										{
    	    									sb.append("Dis or Dat"+System.lineSeparator());    											
    	    									break;
    										}
    										case 4:
    										{
    	    									sb.append("Jack Attack"+System.lineSeparator());    											
    	    									break;
    										}
    										case 5:
    										{
    	    									sb.append("Fiber Optic Setup"+System.lineSeparator());    											
    	    									break;
    										}
    										case 6:
    										{
    	    									sb.append("Fiber Optic"+System.lineSeparator());    											
    	    									break;
    										}
    										case 7:
    										{
    	    									sb.append("Round 1 Wrapup"+System.lineSeparator());    											
    	    									break;
    										}
    										case 10:
    										{
    	    									sb.append("Celebrity Collect Call Setup"+System.lineSeparator());    											
    	    									break;
    										}
    										case 11:
    										{
    	    									sb.append("Celebrity Collect Call"+System.lineSeparator());    											
    	    									break;
    										}
    										case 12:
    										{
    	    									sb.append("ThreeWay"+System.lineSeparator());    											
    	    									break;
    										}
    								}   								
    							}
    								seekval++;
    								sb.append("Difficulty :");
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ANY"+System.lineSeparator());
    								}
    								else
    								{
    									switch (stuff[seekval])
    									{
    										case 1:
    										{
    	    									sb.append("Easy"+System.lineSeparator());
    	    									break;
    										}
    										case 2:
    										{
    	    									sb.append("Medium"+System.lineSeparator());    											
    	    									break;
    										}
    										case 3:
    										{
    	    									sb.append("Hard"+System.lineSeparator());    											
    	    									break;
    										}
    										case 4:
    										{
    	    									sb.append("Impossible"+System.lineSeparator());    											
    	    									break;
    										}
    									}    								
    								}
    								seekval++;
    								sb.append("Topic :");
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ANY"+System.lineSeparator());
    								}
    								else
    								{
    									sb.append("Code "+stuff[seekval]+" (Contact Support)"+System.lineSeparator());
    								}
    								seekval++;
    								sb.append("Format :");
    								if (stuff[seekval] == 0x2a)
    								{
    									sb.append("ANY"+System.lineSeparator());
    								}
    								else
    								{
    									switch (stuff[seekval])
    									{
    										case 1:
    										{
    	    									sb.append("Plain (4 option)"+System.lineSeparator());
    	    									break;
    										}
    										case 2:
    										{
    	    									sb.append("Fill in the blank/Interruptible Celebrity Collect Call"+System.lineSeparator());    											
    	    									break;
    										}
    										case 3:
    										{
    	    									sb.append("Whatshisname"+System.lineSeparator());    											
    	    									break;
    										}
    										case 4:
    										{
    	    									sb.append("Picture Question"+System.lineSeparator());    											
    	    									break;
    										}
    										case 5:
    										{
    	    									sb.append("Audio Question"+System.lineSeparator());    											
    	    									break;
    										}
    										case 6:
    										{
    	    									sb.append("Guest Host Question"+System.lineSeparator());    											
    	    									break;
    										}

    									}    								
    								}
    								
    						}
		    					stuff = sb.toString().getBytes();
			    				recalldata.put(ftype+'_'+id, stuff);    								
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
									parents.put(ftype, "template");
								}
	    					}
	    					
	    					
   						}
    					else if (ftype.equals("qhdr"))
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
        						
        						//Impossible questions in YDKJ3 are a special case, a value of 4 is actually 20.
        						if (value ==4)
        						{
        							value =20;
        						}
        						int qtype = stuff[9];// 0 = Standard Question, 1 is skipped?, 2 = Gib, 3 = DisOrDat/3Way, 4 = JackAttack/HeadRush, 5 = Fiber/CCC/PubQuiz, 12 = 3Way
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
        								qtypedef = "Dis or Dat";
        								break;
        							case 4:
        								qtypedef = "Jack Attack / HeadRush";
        								break;
        							case 5:
        								qtypedef = "Fiber Optic Field Trip/ Pub Quiz / Celebrity Collect Call";
        								break;
        							case 12:
        								qtypedef = "3Way";
        								break;
       								default:
           								qtypedef = ""+qtype;
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
        							case 5:
        								subtypedef = "Super Audio question";
        								break;
        							case 6:
        								subtypedef = "Guest host question";
        								break;
       								default:
           								subtypedef = ""+subtype;
           								break;

        						}
        						
        						byte[] titleconst = KSFLUtilities.copy(stuff, 16, 63);
        						        						
        						String title = new String(titleconst, "MACROMAN").trim().replaceAll("\\{", "");;
        						
        						
        						byte[] pathconst = KSFLUtilities.copy(stuff, 81, 63);
        						
        						String path = new String(pathconst, "MACROMAN").trim().replace(':', File.separatorChar);

        						int rightanswer=0;
        						if (stuff.length > 146)
        						{
        							rightanswer = stuff[146];//relates to correct choice, or position of right answer for FITB
        						}
        						String qdata = "Question title: "+title+System.lineSeparator()+
        								"location : "+path+System.lineSeparator()+
        								"Round 1 Value ="+value+",000"+System.lineSeparator()+
        								"Question type ="+qtypedef+System.lineSeparator()+
        								"Subtype ="+subtypedef+System.lineSeparator();
        						if (showanswer)
        						{
        							qdata +="Correct answer number ="+rightanswer+System.lineSeparator();
        						}
        						
        						if (stuff.length >152)
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
	    						recalldata.put(ftype+'_'+qheadnm, stuff);
    						}
							if (!parents.containsKey(ftype))
							{
								parents.put(ftype, "qhdr");
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
	    								if (!recalldata.containsKey(ftype+'_'+id))
   										{
		    								stuff = new String(stuff,"MACROMAN").getBytes();
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
	public Hashtable<String, byte[]> getData() {
		return recalldata;
	}
	public BerkeleyResourceFile getBRF() {
		return rp;
	}

	public Hashtable<String, byte[]> getSaves() {
		return recallsave;
	}
	}
