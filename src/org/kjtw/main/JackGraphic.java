package org.kjtw.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
		out.append(tilestring);
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
		jri.add(new JackRawImage());
		int max = data.length -12;
		fps = fpsnum/fpsden;
		frames =KSFLUtilities.getShort(data, 8);
		int seekpos =10;
		int oldseek =10;
		//default to YDKJ2, since that's all YDKJS.eu looks at right now;
		
		SetPalette("org/kjtw/resources/YDKJ2PAL.bmp");
		if (frames >0)
		{
			StringBuilder frametxt = new StringBuilder();
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
						frametxt.append("VAL:"+0+",XOFFS"+xoffset+",YOFFS"+yoffset+",XSIZE"+xsize+",YSIZE"+ysize+",NUMIMAGES"+frameimgs+",IDX:-1"+System.lineSeparator());

						jsflist.add(new JackSubFrame(0,xoffset,yoffset,xsize,ysize,frameimgs,-1));
					}
					catch(Exception e)
					{
						break;
					}
					if (sameoffset !=0)
					{
						seekpos = (10+frames*2+(sameoffset*12));
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
//								int subimgnum = KSFLUtilities.getByte(data, seekpos);
								seekpos++;
								int flag= KSFLUtilities.getByte(data, seekpos);
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
								
								if ((flag & 0x01) == 0x01)
								{
									frametxt.append("MIRROR TEXT"+System.lineSeparator());
								}
								if ((flag & 0x08) == 0x08)
								{
									frametxt.append("STATE TRIGGER"+System.lineSeparator());
								}
								if ((flag & 0x10) == 0x10)
								{
									frametxt.append("ANIM END"+System.lineSeparator());
								}
								if ((flag & 0x20) == 0x20)
								{
									frametxt.append("SUBFRAME IS TXT"+System.lineSeparator());
								}
									
								frametxt.append("VAL:"+flag+",XOFFS"+xoffs+",YOFFS"+yoffs+",XSIZE"+xs+",YSIZE"+ys+",NUMIMAGES"+0+",IDX:"+idx+System.lineSeparator());
								jsflist.add(new JackSubFrame(flag,xoffs,yoffs,xs,ys,0,idx));
							}
							catch(Exception e)
							{
								break;
							}
					}
						seekpos =oldseek;
					}
				}
				visframes.add(new JackFrame(jsflist,frametxt.toString()));
			}
		}
		
		//image list
		seekpos = imagestart;
		int numimages = KSFLUtilities.getShort(data,seekpos);
		seekpos+=2;
//		int altnumimages = KSFLUtilities.getShort(data,seekpos);
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
			StringBuilder js = new StringBuilder();
			js.append("res['tiles']=new Array();");
			int realy=0;
			int off4pos =0;
			int ignorefirst=0;
			for (JackRawImage j : getJri())
			{
				if (ignorefirst !=0)
				{
					int w = j.getWidth();
					int h = j.getHeight();
					js.append("res['tiles']["+off4pos+"]={x:"+0+",y:"+realy+",w:"+w+",h:"+h+"};");
					realy+= h+1;
					off4pos++;
				}
				else
				{
					ignorefirst++;
				}
				
			}
			tilestring = js.toString();
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
	

	public  BufferedImage resizeImage(BufferedImage image, int width, int height) {
        int type=0;
       type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
       BufferedImage resizedImage = new BufferedImage(width, height,type);
       Graphics2D g = resizedImage.createGraphics();
       g.drawImage(image, 0, 0, width, height, null);
       g.dispose();
       return resizedImage;
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
				int realy=0;
				for (JackRawImage j : getJri())
				{
					int h = j.getHeight();
					
					out.createGraphics().drawImage(j.getImgout(pal),null,0,realy);
					realy+= h+1;
				}
				return out;
			}
			else
			{
				return new BufferedImage (1,1, BufferedImage.TYPE_INT_ARGB);
			}
		}
	}

	public int getFrameSize() {
		// TODO Auto-generated method stub
		return visframes.size();
	}

	public String getFrameTxt(int canvascount)
	{
		return visframes.get(canvascount).getTxt();
	}
	public BufferedImage getFrameImg(int canvascount, Color[] pal) {
		List <JackRawImage> jr = getJri();
		JackFrame jf = visframes.get(canvascount);
		
		BufferedImage out = new BufferedImage(640,480, BufferedImage.TYPE_INT_ARGB);
		BufferedImage canvas = null;

		int masterx=0;
		int mastery=0;
		int masterxs=0;
		int masterys=0;
		for (JackSubFrame jsf : jf.GetSubFrameList())
		{
			try
			{
				if (jsf.idx ==-1)
				{	
					masterx=jsf.xoffset;
					mastery=jsf.yoffset;
					masterxs=jsf.xsize;
					masterys=jsf.ysize;
					canvas = new BufferedImage(masterxs-masterx,masterys-mastery, BufferedImage.TYPE_INT_ARGB);
				}
				else
				{
					int xoffs = jsf.xoffset;
					int yoffs = jsf.yoffset;
					BufferedImage temp = null;
					
					if ((jsf.valflag & 0x20) == 0x20)
					{
						String identifier = ""+jsf.idx;
						int last = identifier.length()-1; 
						int style = Integer.valueOf(identifier.charAt(last));
						identifier = identifier.substring(0, last);
						temp = new BufferedImage(jsf.xsize-xoffs,jsf.ysize-yoffs, BufferedImage.TYPE_INT_ARGB);
						Graphics2D g = temp.createGraphics();
						FontMetrics fm = g.getFontMetrics();
						Font font = null;
						double scale = (jsf.ysize-yoffs) / (fm.getHeight());
						if (style == 1)
						{
							font = g.getFont().deriveFont(Font.BOLD+Font.ITALIC, AffineTransform.getScaleInstance(scale, scale));
						}
						else
						{
							font = g.getFont().deriveFont(Font.BOLD, AffineTransform.getScaleInstance(scale, scale));
						}
						
				        g.setFont(font);
				        g.setColor(Color.RED);
				        fm = g.getFontMetrics(font);
				        int xPos = ((jsf.xsize-xoffs) - fm.stringWidth(identifier)) / 2;
				        int yPos = ( ((jsf.ysize-yoffs) - fm.getHeight()) / 2) + fm.getAscent();
				        g.drawString(identifier, xPos, yPos);
				        g.dispose();
				        
				        if ((jsf.valflag & 0x01) == 0x01)
				        {
					        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
					        tx.translate(-temp.getWidth(null), 0);
					        AffineTransformOp op = new AffineTransformOp(tx, 
	                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
					        temp = op.filter(temp, null);
				        }
					}
					else
					{
						temp = resizeImage(jr.get(jsf.idx).getImgout(pal),jsf.xsize-xoffs,jsf.ysize-yoffs);
					}
					canvas.createGraphics().drawImage(temp,null,xoffs,yoffs);	
					temp=null;
				}
			}
			catch (Exception e)
			{
			//	JOptionPane.showMessageDialog(null, "Image data extends outside of canvas, ox:"+jsf.get(j).xoffset+",oy:"+jsf.get(j).yoffset+",sx:"+jsf.get(j).xsize+",sy:"+jsf.get(j).ysize);
			}
		}
		out.createGraphics().drawImage(canvas,null,masterx,mastery);
		canvas=null;
		return out;	
	}

	public List<JackFrame> getFrames() {
		// TODO Auto-generated method stub
		return visframes;
	}

}
