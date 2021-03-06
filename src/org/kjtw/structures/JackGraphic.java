package org.kjtw.structures;

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

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.kreative.ksfl.KSFLUtilities;
import com.sun.javafx.tk.Toolkit;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class JackGraphic {
	private int imagestart;
	private int fpsnum;
	private int fpsden;
	private double fps;
	private int frames;
	private List<JackFrame> visframes;
	private List<JackRawImage> jri;
	private List<JackImageColl> jic;
	private Color[] palette;
	public Color[] GetPalette()
	{
		return this.palette;
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

	public JackGraphic(byte[] data,String palette) {
		visframes = new ArrayList<JackFrame>();
		imagestart = (KSFLUtilities.getInt(data, 0));
		fpsnum = KSFLUtilities.getShort(data, 4);
		fpsden = KSFLUtilities.getShort(data, 6);
		jri = (new ArrayList <JackRawImage>());
		fps = fpsnum/fpsden;
		frames =KSFLUtilities.getShort(data, 8);
		int seekpos =10;
		int oldseek =10;
		//default to YDKJ2, since that's all YDKJS.eu looks at right now;
		
		SetPalette(palette);
		if (frames >0)
		{
			for (int i =0; i < frames;i++)
			{
				boolean stopframe = false;
				boolean noiseframe = false;
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
						jsflist.add(new JackSubFrame(0,xoffset,yoffset,xsize,ysize,frameimgs,-1));
					}
					catch(Exception e)
					{
						continue;
					}
					if (sameoffset !=0)
					{
						seekpos = (10+frames*2+(sameoffset*12));
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
								if ((flag & 0x08) == 0x08)
								{
									noiseframe=true;
								}
								if ((flag & 0x10) == 0x10)
								{
									stopframe=true;
								}
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
								
								jsflist.add(new JackSubFrame(flag,xoffs,yoffs,xs,ys,0,idx));
							}
							catch(Exception e)
							{
								continue;
							}
					}
					}
					seekpos =oldseek;

				}
				visframes.add(new JackFrame(jsflist,stopframe,noiseframe));
			}
		}
		
		//image list
		seekpos = imagestart;
		int numimages = KSFLUtilities.getShort(data,seekpos);
		seekpos+=2;
//		int altnumimages = KSFLUtilities.getShort(data,seekpos);
		seekpos+=2;
		
		int maxwidth = 0, maxheight = 0;

		if (numimages >0)
		{
			for (int i=0; (i < numimages); i++)
			{
				int offset = KSFLUtilities.getInt(data, seekpos);
				seekpos+=4;
				int width = KSFLUtilities.getShort(data,seekpos);
				if (width > maxwidth)
				{
					maxwidth = width;
				}
				seekpos +=2;
				int height = KSFLUtilities.getShort(data,seekpos);
				if (height > maxheight)
				{
					maxheight = height;
				}
				seekpos+=2;
				jri.add(new JackRawImage(data,offset,width,height));
			}
		}
		}
		
	

	public JackGraphic(byte[] data) {
		this(data,"org/kjtw/resources/YDKJ2PAL.bmp");
	}

	public JackGraphic() {
		visframes = new ArrayList<JackFrame>();
		imagestart = 0;
		fpsnum = 0;
		fpsden = 0;
		jri = (new ArrayList <JackRawImage>());
		fps = 0;
		frames =0;

		SetPalette("org/kjtw/resources/YDKJ2PAL.bmp");
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
	
	public  WritableImage resizeWImage(BufferedImage image, int width, int height) {

        // target width and height:
        double scaledWidth = width;
        double scaledHeight = height;

        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }
 
        ImageView imageView = new ImageView(wr);
        
        imageView.setFitWidth(scaledWidth);
        imageView.setFitHeight(scaledHeight);
        imageView.setSmooth(true);
            

        Pane pane = new Pane(imageView);
        Scene offScreenScene = new Scene(pane);
        WritableImage croppedImage = imageView.snapshot(null, null);
       return croppedImage;
    }
	
		
	public List<JackRawImage> getJri() {
		return jri;
	}

	public void setJri(List<JackRawImage> jri) {
		this.jri = jri;
	}
	
	
	public BufferedImage toGif(Color[] pal)
	{
		jic = new ArrayList<JackImageColl>();
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
					jic.add(new JackImageColl(0,realy,j.getWidth(),j.getHeight()));
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
					
					if ((jsf.flag & 0x20) == 0x20)
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

						if(style == 7)
						{
					        g.setColor(new Color(255, 0, 0, 2/3));
						}
						else if(style == 8)
						{
					        g.setColor(new Color(255, 0, 0, 1/3));
						}
						else
						{
							g.setColor(Color.RED);
						}
				        fm = g.getFontMetrics(font);
				        int xPos = ((jsf.xsize-xoffs) - fm.stringWidth(identifier)) / 2;
				        int yPos = ( ((jsf.ysize-yoffs) - fm.getHeight()) / 2) + fm.getAscent();
				        g.drawString(identifier, xPos, yPos);
				        g.dispose();
				        
				        if ((jsf.flag & 0x01) == 0x01)
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
						temp = resizeImage(jr.get(jsf.idx-1).getImgout(pal),jsf.xsize-xoffs,jsf.ysize-yoffs);
					}
					canvas.createGraphics().drawImage(temp,null,xoffs,yoffs);	
					temp=null;
				}
			}
			catch (Exception e)
			{
			}
		}
		out.createGraphics().drawImage(canvas,null,masterx,mastery);
		canvas=null;
		return out;	
	}

	public List<Integer> getFlags(int canvascount)
	{
		List<Integer> flags = new ArrayList<Integer>(); 
		JackFrame jf = visframes.get(canvascount);
		for (JackSubFrame jsf : jf.GetSubFrameList())
		{
			flags.add(jsf.flag);
		}
		return flags;
	}

	public Image getFrameImg(int canvascount) {
		
		BufferedImage Buffimage = getFrameImg(canvascount, palette);
		
		 WritableImage FXimage = null;
	        if (Buffimage != null) {
	        	FXimage = new WritableImage(Buffimage.getWidth(), Buffimage.getHeight());
	            PixelWriter pw = FXimage.getPixelWriter();
	            for (int x = 0; x < Buffimage.getWidth(); x++) {
	                for (int y = 0; y < Buffimage.getHeight(); y++) {
	                    pw.setArgb(x, y, Buffimage.getRGB(x, y));
	                }
	            }
	        }
		return FXimage;
	}

        	public Image getFrameFXImg(int canvascount, Color[] pal) {
		List <JackRawImage> jr = getJri();
		JackFrame jf = visframes.get(canvascount);
		
		WritableImage out = new WritableImage(640,480);
                WritableImage canvas = null;
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
					canvas = new WritableImage(masterxs-masterx,masterys-mastery);
				}
				else
				{
					int xoffs = jsf.xoffset;
					int yoffs = jsf.yoffset;
					WritableImage temp = null;
					
					if ((jsf.flag & 0x20) == 0x20)
					{
						String identifier = ""+jsf.idx;
						int last = identifier.length()-1; 
						int style = Integer.valueOf(identifier.charAt(last));
						identifier = identifier.substring(0, last);
						temp = new WritableImage(jsf.xsize-xoffs,jsf.ysize-yoffs);
						com.sun.javafx.tk.FontMetrics fm = Toolkit.getToolkit().getFontLoader().getFontMetrics(new javafx.scene.text.Font( 12.0 ));
						javafx.scene.text.Font font = new javafx.scene.text.Font( 12.0 );
						double scale = (jsf.ysize-yoffs) / (fm.getXheight());
						if (style == 1)
						{
						//	font. = g.getFont().deriveFont(Font.BOLD+Font.ITALIC, AffineTransform.getScaleInstance(scale, scale));
						}
						else
						{
					//		font = g.getFont().deriveFont(Font.BOLD, AffineTransform.getScaleInstance(scale, scale));
						}
				    //    g.setFont(font);

						if(style == 7)
						{
//					        g.setColor(new Color(255, 0, 0, 2/3));
						}
						else if(style == 8)
						{
//					        g.setColor(new Color(255, 0, 0, 1/3));
						}
						else
						{
//							g.setColor(Color.RED);
						}
//				        fm = g.getFontMetrics(font);
//				        int xPos = ((jsf.xsize-xoffs) - fm.stringWidth(identifier)) / 2;
//				        int yPos = ( ((jsf.ysize-yoffs) - fm.getHeight()) / 2) + fm.getAscent();
//				        g.drawString(identifier, xPos, yPos);
//				        g.dispose();
				        
				        if ((jsf.flag & 0x01) == 0x01)
				        {
					        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
//					        tx.translate(-temp.getWidth(null), 0);
					        AffineTransformOp op = new AffineTransformOp(tx, 
	                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//					        temp = op.filter(temp, null);
				        }
					}
					else
					{
						temp = resizeWImage(jr.get(jsf.idx-1).getImgout(pal),jsf.xsize-xoffs,jsf.ysize-yoffs);
					}
                        PixelWriter pw = canvas.getPixelWriter();
                        PixelReader pr = temp.getPixelReader();
           	            for (int x = 0; x < temp.getWidth(); x++) {
           	                for (int y = 0; y < temp.getHeight(); y++) {
       	                    pw.setArgb(x+xoffs, y+yoffs, pr.getArgb(x, y));
           	                }
           	            }
				}
				}
			catch (Exception e)
			{
			}
		}
		return canvas;	
	}

	public JackFrame getFrame(int index)
	{
		return visframes.get(index);
	}
	public List<JackFrame> getFrames() {
		// TODO Auto-generated method stub
		return visframes;
	}

	public String getTileJSON() {
		Gson gson = new Gson();
		String json = gson.toJson(jic); 
		return json;
	}

	public String getFrameJSON() {
		Gson gson = new Gson();
		String json = gson.toJson(visframes); 
		return json;
	}

	public String getIndFrameJSON(int canvascount) {
		Gson gson = new Gson();
		String json = gson.toJson(visframes.get(canvascount)); 
		return json;
	}

}
