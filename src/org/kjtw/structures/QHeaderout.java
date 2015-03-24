package org.kjtw.structures;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

import org.kjtw.process.SRFLoad;

public class QHeaderout {
	protected String qname;
	protected int diff;
	protected int type;
	protected int subtype;
	protected String title;
	protected String titlea;
	protected String titleb;
	protected String path;
	protected String forced;
	protected String forcing;
	protected int answer;
	protected boolean autohide;
	
	public QHeaderout(QHeader qh1, boolean jmode) {
		qname = qh1.getName();
		diff = qh1.getValue();
		type = qh1.getType();
		subtype = qh1.getSubType();
		autohide = qh1.hidetext;
		path = qh1.getPath();
		forced = qh1.getForced();
		if (jmode==true)
		{
			try {
				title = new String (qh1.getTitleraw(),"MS932").trim();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				title = new String (qh1.getTitleraw(),"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (forced != null)
		{
			SRFLoad QData = null;
			try {
				QData = new SRFLoad(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Hashtable<String, byte[]> supplements = QData.getData(); 
			if (jmode=true)
			{
				try {
					titlea= new String(supplements.get("STR_18"),"MS932").trim();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					titleb= new String(supplements.get("STR_19"),"MS932").trim();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				try {
					titlea= new String(supplements.get("STR_18"),"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					titleb= new String(supplements.get("STR_19"),"MACROMAN").trim().replaceAll("\\{", "").replaceAll("\u2211" , "ß")+System.lineSeparator();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		forcing = qh1.getForcing();
		answer = qh1.getAnswer();
		
	}

}
