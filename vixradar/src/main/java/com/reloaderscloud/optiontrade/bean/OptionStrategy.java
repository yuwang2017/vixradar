package com.reloaderscloud.optiontrade.bean;

public class OptionStrategy {
	
	private String symbol;
	private String expire;
	
	public String getExpire() {
		return expire;
	}
	public void setExpire(String expire) {
		this.expire = expire;
	}
	private String strategyType = "single";
	
	private String action1;
	private String option1;
	private String strike1;
	
	private String action2;
	private String option2;
	private String strike2;
	
	
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public String getStrategyType() {
		return strategyType;
	}
	public void setStrategyType(String strategyType) {
		this.strategyType = strategyType;
	}
	public String getAction1() {
		return action1;
	}
	public void setAction1(String action1) {
		this.action1 = action1;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getStrike1() {
		return strike1;
	}
	public void setStrike1(String strike1) {
		this.strike1 = strike1;
	}
	public String getAction2() {
		return action2;
	}
	public void setAction2(String action2) {
		this.action2 = action2;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getStrike2() {
		return strike2;
	}
	public void setStrike2(String strike2) {
		this.strike2 = strike2;
	}
	
	

}
