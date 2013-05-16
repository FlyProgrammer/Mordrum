package com.mordrum.mdrug;

/**
 * Created with IntelliJ IDEA.
 * User: Jesse
 * Date: 5/11/13
 * Time: 5:50 PM
 */
public class ProductionStep {
	private ProductionAction action;
	private int data1;
	private int data2;
	private int data3;
	private int data4;

	public ProductionStep(ProductionAction action, int data1) {
		this.action = action;
		this.data1 = data1;
	}

	public ProductionAction getAction() {
		return action;
	}

	public void setAction(ProductionAction action) {
		this.action = action;
	}

	public int getData1() {
		return data1;
	}

	public void setData1(int data1) {
		this.data1 = data1;
	}

	public int getData2() {
		return data2;
	}

	public void setData2(int data2) {
		this.data2 = data2;
	}

	public int getData3() {
		return data3;
	}

	public void setData3(int data3) {
		this.data3 = data3;
	}

	public int getData4() {
		return data4;
	}

	public void setData4(int data4) {
		this.data4 = data4;
	}

	public int getData5() {
		return data5;
	}

	public void setData5(int data5) {
		this.data5 = data5;
	}

	private int data5;
}
