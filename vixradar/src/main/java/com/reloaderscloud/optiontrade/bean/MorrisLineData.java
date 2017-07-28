package com.reloaderscloud.optiontrade.bean;

public class MorrisLineData {

	private String element = "morris-line-chart";
	private String xkey = "price";
	
	private String[] ykeys = {"profit"};
	
	private String[] labels = {"Profit"};
	
	private boolean smooth = false;
	private boolean resize = true;
	
	private boolean parseTime = false;
	
	private DisplayData[] data;



	public boolean isParseTime() {
		return parseTime;
	}

	public void setParseTime(boolean parseTime) {
		this.parseTime = parseTime;
	}

	public String getElement() {
		return element;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getXkey() {
		return xkey;
	}

	public void setXkey(String xkey) {
		this.xkey = xkey;
	}

	public String[] getYkeys() {
		return ykeys;
	}

	public void setYkeys(String[] ykeys) {
		this.ykeys = ykeys;
	}

	public String[] getLabels() {
		return labels;
	}

	public void setLabels(String[] labels) {
		this.labels = labels;
	}

	public boolean isSmooth() {
		return smooth;
	}

	public void setSmooth(boolean smooth) {
		this.smooth = smooth;
	}

	public boolean isResize() {
		return resize;
	}

	public void setResize(boolean resize) {
		this.resize = resize;
	}

	public DisplayData[] getData() {
		return data;
	}

	public void setData(DisplayData[] data) {
		this.data = data;
	}
	
	
}
