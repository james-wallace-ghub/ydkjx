package org.kjtw.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Hashtable;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

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

	Tree tree = null;

	public SRFProcess(Shell uishell, File file) throws IOException
	{
        tree = new Tree(uishell, SWT.BORDER);
		RandomAccessFile raf = null;
		//Force read only
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
					String ftype = KSFLUtilities.fccs(type).trim();
					TreeItem ti = new TreeItem(tree,0);
					ti.setText(ftype);
					for (short id : rp.getIDs(type)) {
						byte[] stuff = new byte[0];
						byte[] save = new byte[0];
						TreeItem ti2 = new TreeItem(ti,0);
						ti2.setText(""+id);

						if (ftype.equals("qhdr"))
						{
							//special case for question header strings
							int qtotal = rp.readInt(16);
							for (int qhd =0; qhd < qtotal; qhd++)
							{
								if (!parents.containsKey(ftype))
								{
									parents.put(ftype, "qheader");
								}
								String apology ="We don't handle this yet, because the format is frankly painful.";
								stuff = apology.getBytes();
								int startoff = (20+(qhd*12));
								String shortfname = KSFLUtilities.fccs(rp.readInt(startoff)).trim();
								int headoffset = rp.readInt(startoff+4)+1;//skip checksum bits
								int version = rp.readInt(startoff+8);
								
/*								in header pos 0 first int is -'shortfname'
								pos 4,5 first short is unknown (category a has 0041 - has preamble opening byte?, others are blank)
								pos 6,7 second short is cash value in round 1 as thousands of dollars 
								pos 8,9 10, 11 unknown
								pos 12,13,14,15 is 0
								Can't see string length code
								Seek 2a, next string is location (2A = root, 3a = /)
								last but two short = correct answer value
								last but one short unknown
								last int 0000*/
								
/*								For AAA
								in header pos 0 first int is 97414141
								pos 4,5 first short is 0041
								pos 6,7 second short is 0002 
								pos 8,9 10, 11 00010100
								pos 12,13,14,15 is 0
								Can't see string length code
								Seek 2a, next string is location (2A = root, 3a = /)
								last but two short = 02
								last but one short = 3f
								last int 0000*/
							}
						}
						else
						{
							MacResource r = rp.get(type, id);
//							n = r.name;
							stuff = r.data;
							if (( ftype.equals("3SEx") || ftype.contains("#")) && !ftype.equals("ANS#"))
							{
								StringListResource rstr = r.shallowRecast(StringListResource.class);
								String[] strs = rstr.getStrings();
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
								stuff = new String(stuff).getBytes("ASCII");
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
			//return r.toAiff();
		return r.toWav();
	}
	public Tree getTree() {
		// TODO Auto-generated method stub
		return tree;
	}
	public Hashtable<String,String> getParents() {
		// TODO Auto-generated method stub
		return parents;
	}
	public Hashtable<String, Byte[]> getData() {
		// TODO Auto-generated method stub
		return recalldata;
	}
	public Hashtable<String, Byte[]> getSaves() {
		// TODO Auto-generated method stub
		return recallsave;
	}
	}
