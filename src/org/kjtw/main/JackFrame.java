package org.kjtw.main;

import java.util.List;
import java.util.Set;

public class JackFrame {
	private int imgnum=0;
	private List<JackSubFrame> subframes;

	public JackFrame(List<JackSubFrame> jsflist) {
		subframes=jsflist;
	}

	public List<JackSubFrame> GetSubFrameList() {
		// TODO Auto-generated method stub
		return subframes;
	}

}
