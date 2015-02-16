package org.kjtw.main;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Set;

public class JackFrame {
	private int imgnum=0;
	private int val=0;
	private int idx=0;
	private List<JackSubFrame> subframes;
	private List<BufferedImage> framelist;
	public JackFrame(List<JackSubFrame> jsflist) {
		subframes=jsflist;
	}

	public List<JackSubFrame> GetSubFrameList() {
		// TODO Auto-generated method stub
		return subframes;
	}

}
