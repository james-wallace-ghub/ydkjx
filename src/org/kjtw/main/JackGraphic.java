package org.kjtw.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.kreative.ksfl.KSFLUtilities;

public class JackGraphic {
	private int imagestart;
	private int fpsnum;
	private int fpsden;
	private double fps;
	private int frames;
	private List<JackFrame> visframes;
	private List<JackRawImage> jri;

	public JackGraphic(byte[] data) {
		visframes = new ArrayList<JackFrame>();
		imagestart = (KSFLUtilities.getInt(data, 0));
		fpsnum = KSFLUtilities.getShort(data, 4);
		fpsden = KSFLUtilities.getShort(data, 6);
		jri = (new ArrayList <JackRawImage>());

		fps = fpsnum/fpsden;
		frames =KSFLUtilities.getShort(data, 8);
		int seekpos =10;
		int oldseek =10;
		if (frames >0)
		{
			for (int i =0; i < frames;i++)
			{
				int frameoffset = KSFLUtilities.getShort(data, seekpos);
				if (frameoffset ==0)
				{
					visframes.add(new JackFrame());					
				}
				else
				{
					List<JackSubFrame> jsflist = new ArrayList<JackSubFrame>();
					seekpos = (10+frames*2+(frameoffset-1)*12);
					int sameoffset = KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					int xoffset = KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					int yoffset = KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					int xsize = KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					int ysize = KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					int frameimgs =  KSFLUtilities.getShort(data, seekpos);
					seekpos+=2;
					jsflist.add(new JackSubFrame(sameoffset,xoffset,yoffset,xsize,ysize));
					if (sameoffset !=0)
					{
						seekpos = (10+frames*2+(sameoffset)*12);
					}
					if (frameimgs > 0)
					{
						for (int j =1; (j < frameimgs+1); j++)
						{
							int subimgnum = KSFLUtilities.getByte(data, seekpos);
							seekpos++;
							int valflag= KSFLUtilities.getByte(data, seekpos);//what is this?
							seekpos++;
							xoffset = KSFLUtilities.getShort(data, seekpos);
							seekpos+=2;
							yoffset = KSFLUtilities.getShort(data, seekpos);
							seekpos+=2;
							xsize = KSFLUtilities.getShort(data, seekpos);
							seekpos+=2;
							ysize = KSFLUtilities.getShort(data, seekpos);
							seekpos+=2;
							frameimgs =  KSFLUtilities.getShort(data, seekpos);
							seekpos+=2;
							jsflist.add(new JackSubFrame(valflag,xoffset,yoffset,xsize,ysize));							
						}
					}
				}
				seekpos =oldseek;
			}
		}
		//image list
		seekpos = imagestart;
		int numimages = KSFLUtilities.getShort(data,seekpos);
		seekpos+=2;
		int altnumimages = KSFLUtilities.getShort(data,seekpos);
		seekpos+=2;
		if (numimages >0)
		{
			for (int i=0; (i < numimages); i++)
			{
				int offset = KSFLUtilities.getInt(data, seekpos);
				seekpos+=4;
				int width = KSFLUtilities.getShort(data,seekpos);
				seekpos +=2;
				int height = KSFLUtilities.getShort(data,seekpos);
				seekpos+=2;
				jri.add(new JackRawImage(data,offset,width,height));
			}
		}
	}

	public List<JackRawImage> getJri() {
		return jri;
	}

	public void setJri(List<JackRawImage> jri) {
		this.jri = jri;
	}

	public BufferedImage toGif()
	{
		int maxwidth = 0, maxheight = 0;
		for (JackRawImage j : getJri())
		{
			if (j.getWidth() > maxwidth)
			{
				maxwidth = j.getWidth();
			}
			maxheight += j.getHeight();
		}
		maxheight += getJri().size();
		
		BufferedImage out = new BufferedImage(maxwidth,maxheight, BufferedImage.TYPE_INT_ARGB);
		StringBuilder js = new StringBuilder();
		js.append("res['tiles']=new Array();");
		int realx=0, realy=0;
		int off4pos =0;
		for (JackRawImage j : getJri())
		{
			int w = j.getWidth();
			int h = j.getHeight();
			
			js.append("res['tiles']["+off4pos+"]={x:"+realx+",y:"+realy+",w:"+w+",h:"+h+"};");
			out.createGraphics().drawImage(j.getImgout(),null,0,realy);
			realy+= h+1;
			off4pos++;
		}
		return out;
		
	}
}
