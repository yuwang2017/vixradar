package com.reloaderscloud.optiontrade.service;

import com.reloaderscloud.optiontrade.bean.MorrisLineData;
import com.reloaderscloud.optiontrade.bean.OptionChain;
import com.reloaderscloud.optiontrade.bean.OptionMeta;
import com.reloaderscloud.optiontrade.bean.OptionStrategy;

public interface OptionService {
	
	public OptionChain getOptionChain(String symbol, String expireDate);
	
	public OptionMeta getOptionMeta(String symbol);
	
	public String getWebContent(String url);
	
	public MorrisLineData getOptionStrategyResults(OptionStrategy strategy);
	
	public void loadVixOptions();
	
}
