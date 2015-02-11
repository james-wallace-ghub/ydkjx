package org.kjtw.main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.kreative.ksfl.KSFLUtilities;

public class JackGraphic {
	private int imagestart;
	private int fpsnum;
	private int fpsden;
	private double fps;
	private int frames;
	private List<JackFrame> visframes;
	private List<JackRawImage> jri;
	private Color[] palette;
	private String tilestring;
	private String framestring;
	public Color[] GetPalette()
	{
		return this.palette;
	}

	public String getJS()
	{
		StringBuilder out = new StringBuilder();
		if (tilestring == null)
		{
			out.append("");
		}
		else
		{
			out.append(tilestring);
		}
		out.append(System.lineSeparator());
		out.append(framestring);
		return out.toString();
	}
	public void SetPalette(String in)
	{
		BufferedImage img = null;
		try {
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream input = classLoader.getResourceAsStream(in);
		    img = ImageIO.read(input);
		} catch (IOException e) {
		}

		palette = new Color[256];
		for (int i =0; i< 256;i++)
		{
			palette[i] = new Color(img.getRGB(i, 0));
		}
	}

	public JackGraphic() {
		SetPalette("org/kjtw/resources/YDKJ2PAL.bmp");
	}
	public JackGraphic(byte[] data) {
		visframes = new ArrayList<JackFrame>();
		imagestart = (KSFLUtilities.getInt(data, 0));
		fpsnum = KSFLUtilities.getShort(data, 4);
		fpsden = KSFLUtilities.getShort(data, 6);
		jri = (new ArrayList <JackRawImage>());
		int max = data.length -12;
		fps = fpsnum/fpsden;
		frames =KSFLUtilities.getShort(data, 8);
		int seekpos =10;
		int oldseek =10;
		//default to YDKJ2, since that's all YDKJS.eu looks at right now;
		
		SetPalette("org/kjtw/resources/YDKJ2PAL.bmp");
		if (frames >0)
		{
			for (int i =0; i < frames;i++)
			{
				List<JackSubFrame> jsflist = new ArrayList<JackSubFrame>();
				int frameoffset = KSFLUtilities.getShort(data, seekpos);
				seekpos+=2;
				if (frameoffset ==0)
				{
				}
				else
				{
					oldseek = seekpos;
					seekpos = (10+frames*2+(frameoffset-1)*12);
					int sameoffset,frameimgs;
					try
					{
						sameoffset = KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						int xoffset = KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						int yoffset = KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						int xsize = KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						int ysize = KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						frameimgs =  KSFLUtilities.getShort(data, seekpos);
						seekpos+=2;
						jsflist.add(new JackSubFrame(0,xoffset,yoffset,xsize,ysize,frameimgs,0));
					}
					catch(Exception e)
					{
						break;
					}
					if (sameoffset !=0)
					{
						seekpos = (10+frames*2+(sameoffset)*12);
						if ( (seekpos < 0 ) || (seekpos > (max) ))
						{
							break;
						}
					}
					if (frameimgs > 0)
					{
						for (int j =1; (j < frameimgs+1); j++)
						{
							try
							{
								int subimgnum = KSFLUtilities.getByte(data, seekpos);
								seekpos++;
								int valflag= KSFLUtilities.getByte(data, seekpos);//what is this?
								seekpos++;
								int xoffs = KSFLUtilities.getShort(data, seekpos);
								seekpos+=2;
								int yoffs = KSFLUtilities.getShort(data, seekpos);
								seekpos+=2;
								int xs = KSFLUtilities.getShort(data, seekpos);
								seekpos+=2;
								int ys = KSFLUtilities.getShort(data, seekpos);
								seekpos+=2;
								int idx =  KSFLUtilities.getShort(data, seekpos);
								seekpos+=2;
								jsflist.add(new JackSubFrame(valflag,xoffs,yoffs,xs,ys,0,idx));
							}
							catch(Exception e)
							{
								break;
							}
						seekpos =oldseek;
					}
				}
				}
				visframes.add(new JackFrame(jsflist));
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
		StringBuilder fs = new StringBuilder("res['frames']=[");
		for (JackFrame jf : visframes)
		{
			List<JackSubFrame> jsf = jf.GetSubFrameList();
			int nbimages = jsf.size();
			if (nbimages >0)
			{
				fs.append("{nbimg:"+(nbimages-1));
			}
			else
			{
				fs.append("{nbimg:"+0);
			}
			if (nbimages >0)
			{
				fs.append(",fps1:"+fpsnum+",fps2:"+fpsden+",img:[");
				for (int j=0; j < nbimages; j++)
				{
					fs.append("{val:"+jsf.get(j).valflag+",ox:"+jsf.get(j).xoffset+",oy:"+jsf.get(j).yoffset+",sx:"+jsf.get(j).xsize+",sy:"+jsf.get(j).ysize+",idx:"+jsf.get(j).idx+"},");
				}
				fs.append("]");
			}
			fs.append("},");
		}
			fs.append("];");
			framestring = fs.toString();

	}

	public List<JackRawImage> getJri() {
		return jri;
	}

	public void setJri(List<JackRawImage> jri) {
		this.jri = jri;
	}
	
	
	public BufferedImage toGif(Color[] pal)
	{
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
			
			if ((maxwidth > 0) && (maxheight >0))
			{
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
					out.createGraphics().drawImage(j.getImgout(pal),null,0,realy);
					realy+= h+1;
					off4pos++;
				}
				tilestring = js.toString();
				return out;
			}
			else
			{
				return new BufferedImage (1,1, BufferedImage.TYPE_INT_ARGB);
			}
		}
	}
}
