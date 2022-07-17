package com.itbl.ibill;

public class WalkSeqModel {

	String name, value;

	public WalkSeqModel(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "WalkSeqModel [name=" + name + ", value=" + value + "]";
	}

}
