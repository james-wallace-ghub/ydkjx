package org.kjtw.structures;

import java.util.List;

public class JackFrame {
	private List<JackSubFrame> subframes;
	private boolean stopframe;
	private boolean noiseframe;
	
	public JackFrame(List<JackSubFrame> jsflist, boolean stopframe, boolean noiseframe) {
		subframes=jsflist;
		this.stopframe=stopframe;
		this.noiseframe=noiseframe;
	}

	public List<JackSubFrame> GetSubFrameList() {
		return subframes;
	}
	public boolean isNoiseFrame()
	{
		return noiseframe;
	}

	public boolean isStopFrame()
	{
		return stopframe;
	}
}
