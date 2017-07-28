package com.reloaderscloud.optiontrade.bean;

public class Quote {
	private double open;
	private double high;
	private double low;
	private double change;
	private double price;
	
	private double dowChange;
	private double spChange;
	private double nasChange;
	private double vixChange;
	private double vix;
	private String vixChangePercent;
	
	
	
	public String getVixChangePercent() {
		return vixChangePercent;
	}
	public void setVixChangePercent(String vixChangePercent) {
		this.vixChangePercent = vixChangePercent;
	}
	public double getVixChange() {
		return vixChange;
	}
	public void setVixChange(double vixChange) {
		this.vixChange = vixChange;
	}
	public double getVix() {
		return vix;
	}
	public void setVix(double vix) {
		this.vix = vix;
	}
	public double getOpen() {
		return open;
	}
	public void setOpen(double open) {
		this.open = open;
	}
	public double getHigh() {
		return high;
	}
	public void setHigh(double high) {
		this.high = high;
	}
	public double getLow() {
		return low;
	}
	public void setLow(double low) {
		this.low = low;
	}
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getDowChange() {
		return dowChange;
	}
	public void setDowChange(double dowChange) {
		this.dowChange = dowChange;
	}
	public double getSpChange() {
		return spChange;
	}
	public void setSpChange(double spChange) {
		this.spChange = spChange;
	}
	public double getNasChange() {
		return nasChange;
	}
	public void setNasChange(double nasChange) {
		this.nasChange = nasChange;
	}
	
	
	
}
