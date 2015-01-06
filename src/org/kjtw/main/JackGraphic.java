package org.kjtw.main;

import java.awt.Color;

public class JackGraphic {

	Color[] JACKPALETTE = {
	new Color(0, 0, 0)      , new Color(128, 0, 0)    ,new Color(0, 128, 0)    ,new Color(128, 128, 0), 
	new Color(0, 0, 128)    , new Color(128, 0, 128)  ,new Color(0, 128, 128)  ,new Color(192, 192, 192),
	new Color(192, 220, 192), new Color(166, 202, 240),new Color(255, 255, 204),new Color(255, 255, 153),
	new Color(255, 255, 102), new Color(255, 255, 51),new Color(255, 204, 255),new Color(255, 204, 204),
	new Color(255, 204, 153), new Color(255, 204, 102),new Color(255, 204, 51),new Color(255, 204, 0),
	new Color(255, 153, 255), new Color(255, 153, 204),new Color(255, 153, 153),new Color(255, 153, 102),
	new Color(255, 153, 51), new Color(255, 153, 0)  ,new Color(255, 102, 255),new Color(255, 102, 204),
	new Color(255, 102, 153), new Color(255, 102, 102),new Color(255, 102, 51),new Color(255, 102, 0),
	new Color(255, 51, 255), new Color(255, 51, 204),new Color(255, 51, 153),new Color(255, 51, 102),
	new Color(255, 51, 51), new Color(255, 51, 0)  ,new Color(255, 0,   204),new Color(255, 0, 153),
	new Color(255, 0, 102)  , new Color(255, 0, 51)  ,new Color(204, 255, 255),new Color(204, 255, 204),
	new Color(204, 255, 153), new Color(204, 255, 102),new Color(204, 255, 51),new Color(204, 255, 0),
	new Color(204, 204, 255), new Color(204, 204, 204),new Color(204, 204, 153),new Color(204, 204, 102),
	new Color(204, 204, 51), new Color(204, 204, 0)  ,new Color(204, 153, 255),new Color(204, 153, 204),
	new Color(204, 153, 153), new Color(204, 153, 102),new Color(204, 153, 51),new Color(204, 153, 0),
	new Color(204, 102, 255), new Color(204, 102, 204),new Color(204, 102, 153),new Color(204, 102, 102),
	new Color(204, 102, 51), new Color(204, 102, 0)  ,new Color(204, 51, 255),new Color(204, 51, 204),
	new Color(204, 51, 153), new Color(204, 51, 102),new Color(204, 51, 51),new Color(204, 51, 0),
	new Color(204, 0, 255)  , new Color(204, 0, 204)  ,new Color(204, 0, 153)  ,new Color(204, 0, 102),
	new Color(204, 0, 51)  , new Color(204, 0, 0)    ,new Color(153, 255, 255),new Color(153, 255, 204),
	new Color(153, 255, 153), new Color(153, 255, 102),new Color(153, 255, 51),new Color(153, 255, 0),
	new Color(153, 204, 255), new Color(153, 204, 204),new Color(153, 204, 153),new Color(153, 204, 102),
	new Color(153, 204, 51), new Color(153, 204, 0)  ,new Color(153, 153, 255),new Color(153, 153, 204),
	new Color(153, 153, 153), new Color(153, 153, 102),new Color(153, 153, 51),new Color(153, 153, 0),
	new Color(153, 102, 255), new Color(153, 102, 204),new Color(153, 102, 153),new Color(153, 102, 102),
	new Color(153, 102, 51), new Color(153, 102, 0)  ,new Color(153, 51, 255),new Color(153, 51, 204),
	new Color(153, 51, 153), new Color(153, 51, 102),new Color(153, 51, 51),new Color(153, 51, 0),
	new Color(153, 0, 255)  , new Color(153, 0, 204)  ,new Color(153, 0, 153)  ,new Color(153, 0, 102),
	new Color(153, 0, 51)  , new Color(153, 0, 0)    ,new Color(102, 255, 255),new Color(102, 255, 204),
	new Color(102, 255, 153), new Color(102, 255, 102),new Color(102, 255, 51),new Color(102, 255, 0),
	new Color(102, 204, 255), new Color(102, 204, 204),new Color(102, 204, 153),new Color(102, 204, 102),
	new Color(102, 204, 51), new Color(102, 204, 0)  ,new Color(102, 153, 255),new Color(102, 153, 204),
	new Color(102, 153, 153), new Color(102, 153, 102),new Color(102, 153, 51),new Color(102, 153, 0),
	new Color(102, 102, 255), new Color(102, 102, 204),new Color(102, 102, 153),new Color(102, 102, 102),
	new Color(102, 102, 51), new Color(102, 102, 0)  ,new Color(102, 51, 255),new Color(102, 51, 204),
	new Color(102, 51, 153), new Color(102, 51, 102),new Color(102, 51, 51),new Color(102, 51, 0),
	new Color(102, 0, 255)  , new Color(102, 0, 204)  ,new Color(102, 0, 153)  ,new Color(102, 0, 102),
	new Color(102, 0, 51)  , new Color(102, 0, 0)    ,new Color(51, 51, 255),new Color(51, 51, 204),
	new Color(51, 255, 153), new Color(51, 255, 102),new Color(51, 255, 51),new Color(51, 255, 0),
	new Color(51, 204, 255), new Color(51, 204, 204),new Color(51, 204, 153),new Color(51, 204, 102),
	new Color(51, 204, 51), new Color(51, 204, 0)  ,new Color(51, 153, 255),new Color(51, 153, 204),

	new Color(51, 153, 153), new Color(51, 153, 102),new Color(51, 153, 51),new Color(51, 153, 0),
	new Color(51, 102, 255), new Color(51, 102, 204),new Color(51, 102, 153),new Color(51, 102, 102),
	new Color(51, 102, 51), new Color(51, 102, 0)  ,new Color(51, 51, 255),new Color(51, 51, 204),

	new Color(51, 51, 153), new Color(51, 51, 102),new Color(51, 51, 51),new Color(51, 51, 0),
	new Color(51, 0, 255)  , new Color(51, 0, 204)  ,new Color(51, 0, 153)  ,new Color(51, 0, 102),
	new Color(51, 0, 51)  , new Color(51, 0, 0)    ,new Color(0, 255, 204)  ,new Color(0, 255, 153),

	new Color(0, 255, 102)  , new Color(0, 255, 51)  ,new Color(0, 204, 255)  ,new Color(0, 204, 204),
	new Color(0, 204, 153)  , new Color(0, 204, 102)  ,new Color(0, 204, 51)  ,new Color(0, 204, 0),
	new Color(0, 153, 255)  , new Color(0, 153, 204)  ,new Color(0, 153, 153)  ,new Color(0, 153, 102),
	new Color(0, 153, 51)  , new Color(0, 153, 0)    ,new Color(0, 102, 255)  ,new Color(0, 102, 204),
	new Color(0, 102, 153)  , new Color(0, 102, 102)  ,new Color(0, 102, 51)  ,new Color(0, 102, 0),
	
	new Color(0, 51, 255)  , new Color(0, 51, 204)  ,new Color(0, 51, 153)  ,new Color(0, 51, 102),
	new Color(0, 51, 51)  , new Color(0, 51, 0)    ,new Color(0, 0, 204)    ,new Color(0, 0, 153),
	new Color(0, 0, 102)    , new Color(0, 0, 51)    ,new Color(238,0, 0)     ,new Color(221,0, 0),
	new Color(187,0, 0)     , new Color(170,0, 0)     ,new Color(119,0, 0)     ,new Color(68,0, 0),
	
	new Color(0,238, 0)     , new Color(0,221, 0)     ,new Color(0,187, 0)     ,new Color(0,170, 0),
	new Color(0,119, 0)     , new Color(0,68, 0)     ,new Color(0,0, 238)     ,new Color(0,0, 221),
	new Color(0,0, 187)     , new Color(0,0,170)     ,new Color(0,0,119)     ,new Color(0,0,68),
	
	new Color(238,238,238)  , new Color(221,221,221)     ,new Color(187,187,187)     ,new Color(170,170,170),
	new Color(136,136,136)  ,new Color(119,119,119)     , new Color(85,85, 85)     , new Color(68,68,68)     ,
	new Color(34,34,34)  ,new Color(0,0,1)     , new Color(255,251,240)  ,new Color(160,160,164)     ,
	new Color(128,128,128)  ,new Color(255,0,0)     , new Color(0,255,0)  ,new Color(255,255,0)     ,
	new Color(0,0,255)  ,new Color(255,0,255)     , new Color(0,255,255)  ,new Color(255,255,255)
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
