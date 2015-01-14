package org.kjtw.main;

public class JackSubFrame {

	public JackSubFrame(int valflag, int xoffset, int yoffset, int xsize,
			int ysize) {
		this.sameoffset = valflag;
		this.offsetx = xoffset;
		this.offsety = yoffset;
		this.sizex = xsize;
		this.sizey = ysize;
	}
	private int sameoffset;
	private int offsetx;
	private int offsety;
	private int sizex;
	private int sizey;
	private int numimagesinframe;

}
