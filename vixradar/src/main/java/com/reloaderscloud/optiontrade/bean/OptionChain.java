package com.reloaderscloud.optiontrade.bean;

import java.util.ArrayList;
import java.util.List;

import com.reloaderscloud.optiontrade.util.MathUtil;

public class OptionChain {
	
	private String symbol;
	private String expire;
	
	private Quote quote;
	
	List<OptionEntry> chain = new ArrayList<OptionEntry>();
	
	List<String> strikes;

	
	public List<String> getStrikes() {
		return strikes;
	}
	public void setStrikes(List<String> strikes) {
		this.strikes = strikes;
	}
	public Quote getQuote() {
		return quote;
	}
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getExpire() {
		return expire;
	}
	public void setExpire(String expire) {
		this.expire = expire;
	}
	public List<OptionEntry> getChain() {
		return chain;
	}
	public void setChain(List<OptionEntry> chain) {
		this.chain = chain;
	}
	
	
	public int getTotalVolume(){
		
		int vol = 0;
		for(OptionEntry op : chain){
			vol = vol + op.getCallVol() + op.getPutVol();
		}
		return vol;
	}
	
	public int getTotalOpenInterest(){
		
		int vol = 0;
		for(OptionEntry op : chain){
			vol = vol + op.getCallIns() + op.getPutIns();
		}
		return vol;
	}
	
	public int getChainLength(){
		return chain.size();
	}
	
	public int getStepRatio(double price){
		
		int r = 100;
		if(chain.size()>2){
			double d = Math.abs(chain.get(0).getStrike() - chain.get(1).getStrike());
			r = (int)(d * 100.0/price);
		}
		return r;		
	}
	
	public OptionEntry getNearByOpenInterests(double price, double var){
		OptionEntry op = new OptionEntry();
		
		double delta = price * var;
		double min = price - delta;
		double max = price + delta;
		
		for(OptionEntry o : chain){
			if((o.getStrike()< max) && (o.getStrike()>min)){
				op.setCallIns(op.getCallIns() + o.getCallIns());
				op.setPutIns(op.getPutIns() + o.getPutIns());
				op.setCallVol(op.getCallVol() + o.getCallVol());
				op.setPutVol(op.getPutVol() + o.getPutVol());
			}			
		}
		
		return op;
	}
	
	public double[] getNearByStraddlePrice(double price){
		
		OptionEntry op = getNearByOptionEntry(price);
		double[] d = new double[2];
		if(op!=null){
			d[0] = op.getStrike();
			d[1] = MathUtil.round(op.getCallBid() + op.getPutBid());
		}
	
		return d;
	}
	
	public double getStraddleBuyPrice (double strike){
		for(OptionEntry oe : chain){
			if(oe.getStrike()==strike){
				return MathUtil.round(oe.getCallAsk() + oe.getPutAsk());
			}
		}
		return 100.0;
	}
	
	public OptionEntry getOptionEntry (double strike){
		for(OptionEntry oe : chain){
			if(oe.getStrike()==strike){
				return oe;
			}
		}
		return null;
	}
	
	public OptionEntry getNearByOptionEntry (double price){
		
		double delta = 1000;
		OptionEntry op = null;
		for(OptionEntry o : chain){
			if(Math.abs(o.getStrike()-price)<delta){
				delta = Math.abs(o.getStrike()-price);
				op = o;
			}			
		}
		return op;
	}
	
	public List<OptionEntry> getNearByOptions(double price, int half_length){
		//trim option chain to 12 around current price
		OptionEntry so = getNearByOptionEntry(price);
		int middle = 0;
		for(OptionEntry oe : chain){
			middle++;
			if(so!=null){
			if(so.getStrike()==oe.getStrike()){
				break;
			}
			}
		}
		int start = middle - half_length;
		if(start<0) start = 0;
		int end = middle + half_length;
		if(end>getChainLength()){
			end = getChainLength();
		}
		List<OptionEntry> options = chain.subList(start, end);
		return options;
	}
	
	public List<Double> getStrikePrices(){
		List<Double> strikes = new ArrayList<Double>();
		for(OptionEntry op : chain){
			strikes.add(new Double(op.getStrike()));
		} 
		return strikes;
	}
}
