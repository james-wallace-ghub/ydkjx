package org.kjtw.resources;

import java.util.Hashtable;


public class YDKJPalettes {

	static Hashtable<String,String> palettelist = new Hashtable<String,String>();
	

	public YDKJPalettes(){
		palettelist = new Hashtable<String,String>();
		palettelist.put("YDKJ 1","org/kjtw/resources/YDKJ1PAL.bmp");
		palettelist.put("YDKJ 2","org/kjtw/resources/YDKJ2PAL.bmp");
		palettelist.put("YDKJ 3","org/kjtw/resources/YDKJ3PAL.bmp");
		palettelist.put("YDKJ 4 (The Ride)","org/kjtw/resources/YDKJ4PAL.bmp");
		palettelist.put("HeadRush","org/kjtw/resources/HRUSHP.bmp");
		palettelist.put("Offline","org/kjtw/resources/OFFLINEP.bmp");
		palettelist.put("Louder! Faster! Funnier!","org/kjtw/resources/LFFP.bmp");
		
		
	}


	public static Hashtable<String,String> getPalettes() {
		// TODO Auto-generated method stub
		return palettelist;
	};
	
}
