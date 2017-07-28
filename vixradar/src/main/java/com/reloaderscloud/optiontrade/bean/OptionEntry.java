package com.reloaderscloud.optiontrade.bean;

import java.util.Comparator;
import java.util.Date;

import com.reloaderscloud.optiontrade.util.MathUtil;

public class OptionEntry implements Comparator <OptionEntry> {
	
	private String callSymbol = "";
	private String putSymbol = "";
	private double strike = 0.0;
	private double call = 0.0;
	private double put = 0.0;
	private double callBid = 0.0;
	private double callAsk = 0.0;
	private double putBid = 0.0;
	private double putAsk = 0.0;
	private int callIns = 0;
	private int putIns = 0;
	private int callVol = 0;
	private int putVol = 0;
	
	private double close;
	private String stockSymbol;
	private String optionExpireDate;
	private Date scanDate;
	
	public int compare(OptionEntry a1, OptionEntry a2) {

		//ascending order
		return (int)(a1.getStrike() - a2.getStrike());
	}
	
	
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public String getOptionExpireDate() {
		return optionExpireDate;
	}
	public void setOptionExpireDate(String optionExpireDate) {
		this.optionExpireDate = optionExpireDate;
	}
	public Date getScanDate() {
		return scanDate;
	}
	public void setScanDate(Date scanDate) {
		this.scanDate = scanDate;
	}
	public double getCallAsk() {
		return callAsk;
	}
	public void setCallAsk(double callAsk) {
		this.callAsk = callAsk;
	}
	public double getCallBid() {
		return callBid;
	}
	public void setCallBid(double callBid) {
		this.callBid = callBid;
	}
	public double getPutAsk() {
		return putAsk;
	}
	public void setPutAsk(double putAsk) {
		this.putAsk = putAsk;
	}
	public double getPutBid() {
		return putBid;
	}
	public void setPutBid(double putBid) {
		this.putBid = putBid;
	}
	public double getStrike() {
		return strike;
	}
	public void setStrike(double strike) {
		this.strike = strike;
	}
	public double getCall() {
		return call;
	}
	public void setCall(double call) {
		this.call = call;
	}
	public double getPut() {
		return put;
	}
	public void setPut(double put) {
		this.put = put;
	}
	public int getCallIns() {
		return callIns;
	}
	public void setCallIns(int callIns) {
		this.callIns = callIns;
	}
	public int getPutIns() {
		return putIns;
	}
	public void setPutIns(int putIns) {
		this.putIns = putIns;
	}
	public int getCallVol() {
		return callVol;
	}
	public void setCallVol(int callVol) {
		this.callVol = callVol;
	}
	public int getPutVol() {
		return putVol;
	}
	public void setPutVol(int putVol) {
		this.putVol = putVol;
	}
	public String getCallSymbol() {
		return callSymbol;
	}
	public void setCallSymbol(String callSymbol) {
		this.callSymbol = callSymbol;
	}
	public String getPutSymbol() {
		return putSymbol;
	}
	public void setPutSymbol(String putSymbol) {
		this.putSymbol = putSymbol;
	}
	
	public double getAverageCallPrice(){
		return MathUtil.round((callBid + callAsk)/2.0);
	}
	public double getAveragePutPrice(){
		return MathUtil.round((putBid + putAsk)/2.0);
	}
}
