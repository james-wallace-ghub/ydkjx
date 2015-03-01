package org.kjtw.main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class JackAnim {
	public JackAnim(List<BufferedImage> outlist2, List<String> outtext2) {
		this.outlist=new ArrayList<BufferedImage>(outlist2);
		this.outtext=new ArrayList<String>(outtext2);
	}
	public List<String> outtext;
	public List<BufferedImage> outlist;
	public List<BufferedImage> getFrames() {
		return outlist;
	}
	public List<String> getText() {
		return outtext;
	}

}
