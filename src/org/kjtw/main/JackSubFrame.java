package org.kjtw.main;

public class JackSubFrame {

	public JackSubFrame(int valflag, int xoffset, int yoffset, int xsize,
			int ysize, int frameimgs, int idx) {
		this.valflag = valflag;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xsize = xsize;
		this.ysize = ysize;
		this.numimagesinframe = frameimgs;
		this.idx=idx;
	}
	public int valflag;
	public int xoffset;
	public int yoffset;
	public int xsize;
	public int ysize;
	public int numimagesinframe;
	public int idx;

}
