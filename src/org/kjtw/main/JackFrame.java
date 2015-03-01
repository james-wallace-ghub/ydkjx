package org.kjtw.main;

import java.util.List;

public class JackFrame {
	private List<JackSubFrame> subframes;
	private String jacktxt;
	public JackFrame(List<JackSubFrame> jsflist, String string) {
		subframes=jsflist;
		jacktxt=string;
	}

	public List<JackSubFrame> GetSubFrameList() {
		return subframes;
	}

	public String getTxt() {
		// TODO Auto-generated method stub
		return jacktxt;
	}

}
