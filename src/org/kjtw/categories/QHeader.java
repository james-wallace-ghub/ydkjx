package org.kjtw.categories;

public class QHeader {
	protected String name;
	protected int value;
	protected int type;
	protected int subtype;
	protected String title;
	protected String titlea;
	protected String titleb;
	protected String path;
	protected String forced;
	protected String forcing;
	protected int answer;
	public void setName(String qheadnm) {
		this.name = qheadnm;
	}
	public void setValue(int i) {
		this.value =i;
	}
	public void setType(int i) {
		this.type =i;
	}
	public int getType() {
		return this.type;
	}
	public void setSubType(int i) {
		this.subtype =i;
		
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public void setPath(String path) {
		this.path=path;
	}
	public void setAnswer(int i) {
		this.answer=i;
	}
	public void setForcing(String forcestr) {
		this.forcing=forcestr;
	}
	public void setForced(String forcestr) {
		this.forced=forcestr;
	}
	public int getSubType() {
		return this.subtype;
	}
	public String getPath() {
		// TODO Auto-generated method stub
		return this.path;
	}

}
