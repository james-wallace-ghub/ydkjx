package org.kjtw.main;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.kreative.ksfl.KSFLUtilities;

public class JackRawImage {

	private int width;
	private int height;
	private BufferedImage imgout;
	Color[] JACKPALETTE = {
	new Color(0, 0, 0)      , new Color(128, 0, 0)    ,new Color(0, 128, 0)    ,new Color(128, 128, 0), 
	new Color(0, 0, 128)    , new Color(128, 0, 128)  ,new Color(0, 128, 128)  ,new Color(192, 192, 192),
	new Color(192, 220, 192), new Color(166, 202, 240),new Color(255, 255, 204),new Color(255, 255, 153),
	new Color(255, 255, 102), new Color(255, 255, 51) ,new Color(255, 204, 255),new Color(255, 204, 204),
	new Color(255, 204, 153), new Color(255, 204, 102),new Color(255, 204, 51) ,new Color(255, 204, 0),
	new Color(255, 153, 255), new Color(255, 153, 204),new Color(255, 153, 153),new Color(255, 153, 102),
	new Color(255, 153, 51) , new Color(255, 153, 0)  ,new Color(255, 102, 255),new Color(255, 102, 204),
	new Color(255, 102, 153), new Color(255, 102, 102),new Color(255, 102, 51) ,new Color(255, 102, 0),
	new Color(255, 51, 255) , new Color(255, 51, 204) ,new Color(255, 51, 153) ,new Color(255, 51, 102),
	new Color(255, 51, 51)  , new Color(255, 51, 0)   ,new Color(255, 0,   204),new Color(255, 0, 153),
	new Color(255, 0, 102)  , new Color(255, 0, 51)   ,new Color(204, 255, 255),new Color(204, 255, 204),
	new Color(204, 255, 153), new Color(204, 255, 102),new Color(204, 255, 51) ,new Color(204, 255, 0),
	new Color(204, 204, 255), new Color(204, 204, 204),new Color(204, 204, 153),new Color(204, 204, 102),
	new Color(204, 204, 51) , new Color(204, 204, 0)  ,new Color(204, 153, 255),new Color(204, 153, 204),
	new Color(204, 153, 153), new Color(204, 153, 102),new Color(204, 153, 51) ,new Color(204, 153, 0),
	new Color(204, 102, 255), new Color(204, 102, 204),new Color(204, 102, 153),new Color(204, 102, 102),
	new Color(204, 102, 51) , new Color(204, 102, 0)  ,new Color(204, 51, 255) ,new Color(204, 51, 204),
	new Color(204, 51, 153) , new Color(204, 51, 102) ,new Color(204, 51, 51)  ,new Color(204, 51, 0),
	new Color(204, 0, 255)  , new Color(204, 0, 204)  ,new Color(204, 0, 153)  ,new Color(204, 0, 102),
	new Color(204, 0, 51)   , new Color(204, 0, 0)    ,new Color(153, 255, 255),new Color(153, 255, 204),
	new Color(153, 255, 153), new Color(153, 255, 102),new Color(153, 255, 51) ,new Color(153, 255, 0),
	new Color(153, 204, 255), new Color(153, 204, 204),new Color(153, 204, 153),new Color(153, 204, 102),
	new Color(153, 204, 51) , new Color(153, 204, 0)  ,new Color(153, 153, 255),new Color(153, 153, 204),
	new Color(153, 153, 153), new Color(153, 153, 102),new Color(153, 153, 51) ,new Color(153, 153, 0),
	new Color(153, 102, 255), new Color(153, 102, 204),new Color(153, 102, 153),new Color(153, 102, 102),
	new Color(153, 102, 51) , new Color(153, 102, 0)  ,new Color(153, 51, 255) ,new Color(153, 51, 204),
	new Color(153, 51, 153 ), new Color(153, 51, 102) ,new Color(153, 51, 51)  ,new Color(153, 51, 0),
	new Color(153, 0, 255)  , new Color(153, 0, 204)  ,new Color(153, 0, 153)  ,new Color(153, 0, 102),
	new Color(153, 0, 51)   , new Color(153, 0, 0)    ,new Color(102, 255, 255),new Color(102, 255, 204),
	new Color(102, 255, 153), new Color(102, 255, 102),new Color(102, 255, 51) ,new Color(102, 255, 0),
	new Color(102, 204, 255), new Color(102, 204, 204),new Color(102, 204, 153),new Color(102, 204, 102),
	new Color(102, 204, 51) , new Color(102, 204, 0)  ,new Color(102, 153, 255),new Color(102, 153, 204),
	new Color(102, 153, 153), new Color(102, 153, 102),new Color(102, 153, 51) ,new Color(102, 153, 0),
	new Color(102, 102, 255), new Color(102, 102, 204),new Color(102, 102, 153),new Color(102, 102, 102),
	new Color(102, 102, 51) , new Color(102, 102, 0)  ,new Color(102, 51, 255) ,new Color(102, 51, 204),
	new Color(102, 51, 153) , new Color(102, 51, 102) ,new Color(102, 51, 51)  ,new Color(102, 51, 0),
	new Color(102, 0, 255)  , new Color(102, 0, 204)  ,new Color(102, 0, 153)  ,new Color(102, 0, 102),
	new Color(102, 0, 51)   , new Color(102, 0, 0)    ,new Color(51, 51, 255)  ,new Color(51, 51, 204),
	new Color(51, 255, 153) , new Color(51, 255, 102) ,new Color(51, 255, 51)  ,new Color(51, 255, 0),
	new Color(51, 204, 255) , new Color(51, 204, 204) ,new Color(51, 204, 153) ,new Color(51, 204, 102),
	new Color(51, 204, 51)  , new Color(51, 204, 0)   ,new Color(51, 153, 255) ,new Color(51, 153, 204),
	new Color(51, 153, 153) , new Color(51, 153, 102) ,new Color(51, 153, 51)  ,new Color(51, 153, 0),
	new Color(51, 102, 255) , new Color(51, 102, 204) ,new Color(51, 102, 153) ,new Color(51, 102, 102),
	new Color(51, 102, 51)  , new Color(51, 102, 0)   ,new Color(51, 51, 255)  ,new Color(51, 51, 204),
	new Color(51, 51, 153)  , new Color(51, 51, 102)  ,new Color(51, 51, 51)   ,new Color(51, 51, 0),
	new Color(51, 0, 255)   , new Color(51, 0, 204)   ,new Color(51, 0, 153)   ,new Color(51, 0, 102),
	new Color(51, 0, 51)    , new Color(51, 0, 0)     ,new Color(0, 255, 204)  ,new Color(0, 255, 153),
	new Color(0, 255, 102)  , new Color(0, 255, 51)   ,new Color(0, 204, 255)  ,new Color(0, 204, 204),
	new Color(0, 204, 153)  , new Color(0, 204, 102)  ,new Color(0, 204, 51)   ,new Color(0, 204, 0),
	new Color(0, 153, 255)  , new Color(0, 153, 204)  ,new Color(0, 153, 153)  ,new Color(0, 153, 102),
	new Color(0, 153, 51)   , new Color(0, 153, 0)    ,new Color(0, 102, 255)  ,new Color(0, 102, 204),
	new Color(0, 102, 153)  , new Color(0, 102, 102)  ,new Color(0, 102, 51)   ,new Color(0, 102, 0),
	new Color(0, 51, 255)   , new Color(0, 51, 204)   ,new Color(0, 51, 153)   ,new Color(0, 51, 102),
	new Color(0, 51, 51)    , new Color(0, 51, 0)     ,new Color(0, 0, 204)    ,new Color(0, 0, 153),
	new Color(0, 0, 102)    , new Color(0, 0, 51)     ,new Color(238,0, 0)     ,new Color(221,0, 0),
	new Color(187,0, 0)     , new Color(170,0, 0)     ,new Color(119,0, 0)     ,new Color(68,0, 0),	
	new Color(0,238, 0)     , new Color(0,221, 0)     ,new Color(0,187, 0)     ,new Color(0,170, 0),
	new Color(0,119, 0)     , new Color(0,68, 0)      ,new Color(0,0, 238)     ,new Color(0,0, 221),
	new Color(0,0, 187)     , new Color(0,0,170)      ,new Color(0,0,119)      ,new Color(0,0,68),
	new Color(238,238,238)  , new Color(221,221,221)  ,new Color(187,187,187)  ,new Color(170,170,170),
	new Color(136,136,136)  , new Color(119,119,119)  ,new Color(85,85, 85)    ,new Color(68,68,68),
	new Color(34,34,34)     ,new Color(0,0,1)         ,new Color(255,251,240)  ,new Color(160,160,164),
	new Color(128,128,128)  ,new Color(255,0,0)       ,new Color(0,255,0)      ,new Color(255,255,0),
	new Color(0,0,255)      ,new Color(255,0,255)     ,new Color(0,255,255)    ,new Color(255,255,255)
	};

	public JackRawImage(byte[] data, int offset, int width, int height) {
		this.setWidth(width);
		this.setHeight(height);
		
		byte[] buf = KSFLUtilities.copy(data, offset, 307200);
		setImgout(decodegfx(buf,width*height,width,height));
		
	}

	//Straight port of code from YDKJ.fr, just with sign hacking
	private BufferedImage decodegfx(byte[] buf,int buflen,int width, int height) {
		int[] bitmap = new int[307201];
		int[] infos = new int[3];
		int l=0;
		int pos =0;
		int extrapixels = (buf[0] &0xff)-2;
		int bglength = -1;
		int nextbg = (buf[0] &0xff)+(buf[1] &0xff);
		int widebf;
		int status;
		int nbrepeat;
		int offset;
		BufferedImage imgout = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i <3; i++)
		{
			bitmap[l]=(buf[pos]&0xff);
			pos++;
			l++;
		}
		  while ((l+extrapixels < buflen) && (l < 307200) )
		{
			widebf=(buf[pos]&0xff);
			pos++;
			widebf = ((widebf <<1) | 1);
			while ( (widebf & 0xff) !=0)
			{
				status = ((widebf & 0x100) >>8);
				widebf = (widebf << 1);
				if (status ==0)
				{
					//copy pixel
					if ( (l == (nextbg - extrapixels)) || (bglength != -1))
					{
						if (bglength ==-1)
						{
							bglength = (buf[pos] & 0xff);
						}
						else
						{
							extrapixels += (bglength -2);
							nextbg +=(bglength + (buf[pos]&0xff));
							bglength =-1;
						}
					}
						bitmap[l] = (buf[pos]&0xff);
						pos++;
						l++;
				}
			else
				{ // Répétition
			        infos[0]=(buf[pos]&0xff);
			        pos++;
			        infos[1]=(buf[pos]&0xff);
			        pos++;
			        if ((infos[1] & 0xF0) == 0)
			        { // Répétition à 3 octets
			          infos[2]=(buf[pos]&0xff);
			          pos++;
			          nbrepeat=infos[2]+0x10;
			        }else
			        	{ // Répétition à 2 octets
			          nbrepeat=infos[1] & 0xF0;
			          nbrepeat=nbrepeat >> 4;
			        	}
			        	offset=l-(infos[0]+0x100*(infos[1] & 0x0F));  // Répétition, offset : line[0]+0x100*LO(line[1]), qté = HI(line[1])+2
			        	
						for (int i = 1; i <(nbrepeat+3); i++)
						{
							if ( (l == (nextbg-extrapixels) ) || (bglength !=1))
							{
								if (bglength == -1)
								{
									bglength = (bitmap[offset]);
								}
								else
								{
									extrapixels += (bglength -2);
									nextbg += (bglength +(bitmap[offset]));
									bglength =-1;
								}
							}
							bitmap[l]=(bitmap[offset]);
							offset++;
							l++;
						}
					}
				}
			}
			
		nextbg=0;
		bglength =-1;
		//bitmap extraction
		pos =0;
		
		for (int i=0; i < l; i++)
		{
			if (bglength != -1)
			{
				nextbg += (bglength + (bitmap[i]));
				while (bglength >0)
				{
					int row = (pos / width);
					int column = pos - (row * width); 
					imgout.setRGB(column, row,0xff80ff);

					pos++;
					bglength--;
					if ( pos > 307199)
					{
						break;//Some of the non Berkeley segues don't set this correctly, (the last French segue, for example. Break before we overrun.
					}
				}
				bglength =-1;
			}
			else if (pos == nextbg)
			{
				bglength = (bitmap[i]);
			}
			else
			{
				int row = (pos / width);
				int column = pos - (row * width); 
				imgout.setRGB(column, row, getJackColour(JACKPALETTE[bitmap[i]]));
				pos++;
			}
			if ( pos > 307199)
			{
				break;
			}
		}
		return imgout;
	}

	private int getJackColour(Color color) {
		return color.getRGB();
	}

	public BufferedImage getImgout() {
		return imgout;
	}

	public void setImgout(BufferedImage imgout) {
		this.imgout = imgout;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}


}
