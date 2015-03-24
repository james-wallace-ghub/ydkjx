package org.kjtw.structures;

import java.io.File;
import java.io.UnsupportedEncodingException;




import com.kreative.ksfl.KSFLUtilities;

public class QHeader {
	protected String qname;
	private int diff;
	protected int type;
	protected int subtype;
	protected byte[] titleraw;
	private String title;
	private String titlea;
	private String titleb;
	protected String path;
	protected String forced;
	protected String forcing;
	protected int answer;
	protected boolean hidetext;
	public QHeader(int id, byte[] stuff, File file) {
		String qheadnm = KSFLUtilities.fccs(id).trim();
		this.setName(qheadnm);
		
		int tempval = (int)stuff[8];
		this.setValue(tempval);
		this.setType((int)stuff[9]);

		int subtype = stuff[11];
		this.setSubType(subtype);

		if (subtype == 8)
		{
			this.hidetext = true;
		}
		byte[] titleconst = KSFLUtilities.copy(stuff, 16, 63);

		byte[] pathconst = KSFLUtilities.copy(stuff, 81, 63);

		String path="";
		try {
			path = new String(pathconst, "MACROMAN").trim().replace(':', File.separatorChar);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String fpath = file.getPath().replaceFirst(file.getName(), "");
		this.setTitleraw(titleconst);
		try {
			this.setTitle(new String(getTitleraw(),"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator() );
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.setPath(fpath+File.separatorChar+path);

		if (stuff.length > 146)
		{
			this.setAnswer((int)stuff[146]);
		}
		else
		{
			this.setAnswer(0);
		}

		if (stuff.length >149)
		{
			if (this.hidetext != true)
			{
				this.hidetext = (stuff[149] == 0x4c);
			}
		}
		if (stuff.length >152)
		{
			if (this.getType() == 2)
			{
				if (stuff[146] != 0)
				{
					//forcing a question after this one.
					byte[] forceconst = KSFLUtilities.copy(stuff, 146, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForcing(forcestr);
				}
				if (stuff[150] != 0)
				{
					//This was a forced question, don't display it.
					byte[] forceconst = KSFLUtilities.copy(stuff, 150, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForced(forcestr);
				}
			}
			else if (this.getType() == 3)
			{
				if (stuff[148] != 0)
				{
					//forcing a question after this one.
					byte[] forceconst = KSFLUtilities.copy(stuff, 148, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForcing(forcestr);
				}
				if (stuff[152] != 0)
				{
					//This was a forced question, don't display it.
					byte[] forceconst = KSFLUtilities.copy(stuff, 152, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForced(forcestr);
				}
			}
			else
			{
				if (stuff[150] != 0)
				{
					//forcing a question after this one.
					byte[] forceconst = KSFLUtilities.copy(stuff, 150, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForcing(forcestr);
				}
				if (stuff[154] != 0)
				{
					//This was a forced question, don't display it.
					byte[] forceconst = KSFLUtilities.copy(stuff, 154, 4);
					String forcestr="";
					try {
						forcestr = new String(forceconst, "MACROMAN").trim();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					this.setForced(forcestr);
				}
			}
		}        						
	}
	public void setName(String qheadnm) {
		this.qname = qheadnm;
	}
	public void setValue(int i) {
		this.setDiff(i);
	}
	public void setType(int i) {
		this.type =i;
	}
	public int getType() {
		return this.type;
	}
	public void setSubType(int i) {
		this.subtype =i;
		
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setPath(String path) {
		this.path=path;
	}
	public void setAnswer(int i) {
		this.answer=i;
	}
	public void setForcing(String forcestr) {
		this.forcing=forcestr;
	}
	public void setForced(String forcestr) {
		this.forced=forcestr;
	}
	public int getSubType() {
		return this.subtype;
	}
	public String getPath() {
		return this.path;
	}
	public int getValue() {
		return this.getDiff();
	}
	public void setTitleraw(byte[] titleconst) {
		this.titleraw=titleconst;
	}
	public String toString(boolean jmode){
		
		String output = new String("Name: "+getName()+System.lineSeparator());
		output += "Question title: ";
		if (jmode)
		{
			try {
				output += new String(getTitleraw(),"MS932").trim()+System.lineSeparator();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				output += new String(getTitleraw(),"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String qtypedef="";
		switch (type)
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
				qtypedef = "Fiber Optic Field Trip/ Pub Quiz";
				break;
			case 10:
				qtypedef = "Celebrity Collect Call";
				break;
			case 12:
				qtypedef = "3Way";
				break;
			default:
				qtypedef = ""+type;
				break;
		}

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

			case 8:
				subtypedef = "Normal 4 answer question (spellings)";
				break;
			case 9:
				subtypedef = "Normal 4 answer question (description)";
				break;
			case 10:
				subtypedef = "Normal 4 answer question (trash)";
				break;

			default:
				subtypedef = ""+subtype;
				break;

		}

		output+="Location : "+path+System.lineSeparator()+
		"Difficulty ="+getDiff()+System.lineSeparator()+
		"Question type ="+qtypedef+System.lineSeparator()+
		"Subtype ="+subtypedef+System.lineSeparator()+
		"Correct answer number ="+answer+System.lineSeparator();

		if (this.hidetext)
		{
			output +="This question will autohide text as soon as the answers are read out"+System.lineSeparator();			
		}
		String forcing=this.forcing;
		String forced=this.forced;
		if (forcing != null)
		{
			output +="This question forces "+this.forcing+" to appear next"+System.lineSeparator();
		}
		if (forced != null)
		{
			output +="This question is forced by "+this.forced+System.lineSeparator();
		}
		return output;
		
	}
	public byte[] getTitleraw() {
		// TODO Auto-generated method stub
		return this.titleraw;
	}
	public String getName() {
		// TODO Auto-generated method stub
		return this.qname;
	}
	public String getForced() {
		// TODO Auto-generated method stub
		return forced;
	}
	public String getForcing() {
		// TODO Auto-generated method stub
		return forcing;
	}
	public int getAnswer() {
		// TODO Auto-generated method stub
		return answer;
	}
	public String getTitlea() {
		return titlea;
	}
	public void setTitlea(String titlea) {
		this.titlea = titlea;
	}
	public String getTitleb() {
		return titleb;
	}
	public void setTitleb(String titleb) {
		this.titleb = titleb;
	}
	public String getTitle() {
		return title;
	}
	public int getDiff() {
		return diff;
	}
	public void setDiff(int diff) {
		this.diff = diff;
	}

}
