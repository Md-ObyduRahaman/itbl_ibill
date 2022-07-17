package com.itbl.ibill;

public class BillCycleModel {

	String billcycle;

	public BillCycleModel(String billcycle) {
		super();
		this.billcycle = billcycle;
	}

	public String getBillcycle() {
		return billcycle;
	}

	public void setBillcycle(String billcycle) {
		this.billcycle = billcycle;
	}

	@Override
	public String toString() {
		return "BillCycleModel [billcycle=" + billcycle + "]";
	}

}
