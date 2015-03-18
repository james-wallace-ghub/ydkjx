package org.kjtw.structures;

public class GTEntry {

	public GTEntry(byte[] data, int seekval) {
		seekval+=12;
		this.set = data[seekval];
		seekval++;
		this.intro=data[seekval];
		seekval++;
		this.qtype=data[seekval];
		seekval++;		
		this.diff=data[seekval];
		seekval++;		
		this.topic=data[seekval];
		seekval++;		
		this.format=data[seekval];
		seekval++;		
	}
	int set;
	int intro;
	int qtype;
	int diff;
	int topic;
	int format;
	
}


