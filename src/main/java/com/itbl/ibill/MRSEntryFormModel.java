package com.itbl.ibill;

public class MRSEntryFormModel {

	String billcycle, location, billgrp, book, walkseq, wstype;

	public MRSEntryFormModel(String billcycle, String location, String billgrp, String book, String walkseq,
			String wstype) {
		super();
		this.billcycle = billcycle;
		this.location = location;
		this.billgrp = billgrp;
		this.book = book;
		this.walkseq = walkseq;
		this.wstype = wstype;
	}

	@Override
	public String toString() {
		return "MRSEntryFormModel [billcycle=" + billcycle + ", location=" + location + ", billgrp=" + billgrp
				+ ", book=" + book + ", walkseq=" + walkseq + ", wstype=" + wstype + "]";
	}

	public String getBillcycle() {
		return billcycle;
	}

	public void setBillcycle(String billcycle) {
		this.billcycle = billcycle;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBillgrp() {
		return billgrp;
	}

	public void setBillgrp(String billgrp) {
		this.billgrp = billgrp;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getWalkseq() {
		return walkseq;
	}

	public void setWalkseq(String walkseq) {
		this.walkseq = walkseq;
	}

	public String getWstype() {
		return wstype;
	}

	public void setWstype(String wstype) {
		this.wstype = wstype;
	}

	public MRSEntryFormModel() {
		super();
		// TODO Auto-generated constructor stub
	}

}
