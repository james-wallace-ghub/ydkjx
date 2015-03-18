package org.kjtw.structures;

public class JackSubFrame {

	public JackSubFrame(int flag, int xoffset, int yoffset, int xsize,
			int ysize, int frameimgs, int idx) {
		this.flag = flag;
		this.xoffset = xoffset;
		this.yoffset = yoffset;
		this.xsize = xsize;
		this.ysize = ysize;
		this.numimagesinframe = frameimgs;
		this.idx=idx;
	}
	public int flag;
	public int xoffset;
	public int yoffset;
	public int xsize;
	public int ysize;
	public int numimagesinframe;
	public int idx;

}
