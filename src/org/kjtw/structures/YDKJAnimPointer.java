package org.kjtw.structures;


public class YDKJAnimPointer {
	public String path;
	public short id;
	public int startframe;
	public int endframe;
	public String sndtype;
	public short sndid;

	public YDKJAnimPointer(String path, short id, int startframe, int endframe, String sndtype,
			short sndid) {
		
		this.path = path;
		this.id =id;
		this.startframe= startframe;
		this.endframe = endframe;
		this.sndtype = sndtype;
		this.sndid = sndid;

	}

}
