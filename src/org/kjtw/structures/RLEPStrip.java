package org.kjtw.structures;

public class RLEPStrip {

	private short rLE;
	private short stripid;
	private short lowcast;
	private short highcast;

	public RLEPStrip(short rLE, short stripid, short lowcast, short highcast) {
		this.rLE =rLE;
		this.stripid=stripid;
		this.lowcast=lowcast;
		this.highcast=highcast;
	}

	public int getLowCast() {
		return lowcast;
	}

	public int getHighCast() {
		return highcast;
	}

	public short getID() {
		return stripid;
	}

}
