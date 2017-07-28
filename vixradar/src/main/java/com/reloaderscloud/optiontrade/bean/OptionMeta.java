package com.reloaderscloud.optiontrade.bean;

import java.util.ArrayList;
import java.util.List;

public class OptionMeta {

	private String symbol;
	
	private List<String> expireDates;
	
	private List<String> strikes;

	private Quote quote;
	
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

	public List<String> getExpireDates() {
		return expireDates;
	}

	public void setExpireDates(List<String> expireDates) {
		this.expireDates = expireDates;
	}

	public List<String> getStrikes() {
		return strikes;
	}

	public void setStrikes(List<String> strikes) {
		this.strikes = strikes;
	}	
	
	
	
}
