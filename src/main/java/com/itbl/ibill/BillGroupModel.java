package com.itbl.ibill;

public class BillGroupModel {

	String BILL_GROUP, BILL_GRP_DESCR;

	public BillGroupModel(String bILL_GROUP, String bILL_GRP_DESCR) {
		super();
		BILL_GROUP = bILL_GROUP;
		BILL_GRP_DESCR = bILL_GRP_DESCR;
	}

	public String getBILL_GROUP() {
		return BILL_GROUP;
	}

	public void setBILL_GROUP(String bILL_GROUP) {
		BILL_GROUP = bILL_GROUP;
	}

	public String getBILL_GRP_DESCR() {
		return BILL_GRP_DESCR;
	}

	public void setBILL_GRP_DESCR(String bILL_GRP_DESCR) {
		BILL_GRP_DESCR = bILL_GRP_DESCR;
	}

	@Override
	public String toString() {
		return "BillGroupModel [BILL_GROUP=" + BILL_GROUP + ", BILL_GRP_DESCR=" + BILL_GRP_DESCR + "]";
	}

}
