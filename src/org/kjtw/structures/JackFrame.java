package org.kjtw.structures;

import java.util.List;

public class JackFrame {
	private List<JackSubFrame> subframes;
	public JackFrame(List<JackSubFrame> jsflist) {
		subframes=jsflist;
	}

	public List<JackSubFrame> GetSubFrameList() {
		return subframes;
	}

}
