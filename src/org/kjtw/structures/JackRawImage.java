package org.kjtw.structures;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.kreative.ksfl.KSFLUtilities;

public class JackRawImage {
	private int width;
	private int height;
	private BufferedImage imgout;
	int[][] bitmap;
	//This code taken from the YDKJS project, just converted from Pascal to Java. Credit to ydkjs.eu
	public JackRawImage(byte[] data, int offset, int width, int height) {
		this.setWidth(width);
		this.setHeight(height);

		if ((data.length - offset) > 307199)
		{
			bitmap = decodegfx(KSFLUtilities.copy(data, offset, 307200),width*height,width,height);
		}
		else
		{
			bitmap = decodegfx(KSFLUtilities.copy(data, offset, data.length),width*height,width,height);
		}
	}

	public JackRawImage() {
		this.setWidth(0);
		this.setHeight(0);
		bitmap = new int[1][1];
	}

	//Straight port of code from YDKJ.fr, just with sign hacking
	private int[][] decodegfx(byte[] indata,int buflen,int width, int height) {
		int[] infos = new int[3];
		int[] bitmap = new int[309001];
		long widebf;
		long status;
		long nbrepeat;
		long offset;
		int l=0;
		int pos =0;
		long bglength = -1;
		long nextbg;
		int extrapixels;

		int [][] output = new int[width][height];
		l=0;
		pos=0;
		extrapixels=(indata[0]&0xff)-2;
		bglength=-1;
		nextbg=(indata[0]&0xff)+(indata[1]&0xff);
		for (int i=0; i <3; i++)
		{
			    bitmap[l]=(indata[pos]&0xff);
			    pos++;
			    l++;
		  }
		  
		  while ((l+extrapixels < buflen) && (l < 307200))
		  {
			  widebf=(indata[pos]&0xff);
			  pos++;
			  widebf=(widebf << 1) | 1;
		    while ((widebf & 0xFF) != 0)
		    {
		      status = (widebf & 0x100) >> 8;
		      widebf = widebf << 1;
		      if (status == 0)  // Pixel copied as is
		      {
		        if ((l == nextbg-extrapixels) || (bglength != -1))
		        {
		        	if (bglength == -1) bglength=(indata[pos]&0xff); else
		        	{
		        		extrapixels+=bglength-2;
		        		nextbg+=bglength+(indata[pos]&0xff);
		            	bglength=-1;
		        	}
		        }
		        bitmap[l]=(indata[pos]&0xff);
		        pos++;
		        l++;
		      } else { // Répétition
		        infos[0]=(indata[pos]&0xff);
		        pos++;
		        infos[1]=(indata[pos]&0xff);
		        pos++;
		        if ((infos[1] & 0xF0) == 0 ){ // repeat 3 bytes
		          infos[2]=(indata[pos]&0xff);
		          pos++;
		          nbrepeat=infos[2]+0x10;
		        } else { // Repeat 2 bytes
		          nbrepeat=infos[1] & 0xF0;
		          nbrepeat=nbrepeat >> 4;
		        }
		        offset=l-(infos[0]+0x100*(infos[1] & 0x0F));  // Repeat, offset : line[0]+0x100*LO(line[1]), val = HI(line[1])+2
		        if (offset < 0) offset=0;
		        for (int i=1 ; i < nbrepeat+3 ; i++) {
		          if ((l == nextbg-extrapixels) || (bglength != -1)) {
		            if (bglength == -1)  bglength=bitmap[(int) offset] ; else {
		              extrapixels+=bglength-2;
		              nextbg+=bglength+bitmap[(int) offset];
		              bglength=-1;
		            }
		          }
		          bitmap[l]=bitmap[(int) offset];
		          offset++;
		          l++;
		        }
		      }
		    }
		  }

		  nextbg=0;
		  bglength=-1;

		  // Extraction from bitmap
		  pos=0;
		  for ( int i=0; i < l; i++) {
		    if (bglength != -1) {
		      nextbg+= bglength+bitmap[i];
		      while (bglength > 0) {
		    	  if (pos >= buflen) break;
		    	int row = (pos / width);
				int column = pos - (row * width); 
				output[column][row] =500;//transparency set to impossible colour
		        pos++;
		        bglength--;
		        if (pos >= 307200) break; // TODO: QNUMBERS.SRF, image n°48 (last image) causes value of positon to be exceeded
		      }
		      bglength=-1;
		    } else if (pos==nextbg) {
		      bglength=bitmap[i];
		    } else {
		   	  	if (pos >= buflen) break;
		    	  
		    	int row = (pos / width);
				int column = pos - (row * width); 
				output[column][row] =bitmap[i];

		      pos++;
		    }
		    if (pos >= 307200) break;
		  }
		return output;
	}


	private int getJackColour(Color color) {
		return color.getRGB();
	}

	public int[][] getBitmap(){
		return this.bitmap;		
	}
	public BufferedImage getImgout(Color[] palette) {
		BufferedImage img = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);

		for (int x=0; x<width; x++) {
			for (int y=0; y<height; y++){
				if (bitmap[x][y] ==500)
				{
					img.setRGB(x, y, 0);
				}
				else
				{
					img.setRGB(x, y, getJackColour(palette[bitmap[x][y]]));
				}
			}
		}
		this.imgout = img;
		return imgout;
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
