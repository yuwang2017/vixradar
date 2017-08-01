package com.reloaderscloud.optiontrade.service;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reloaderscloud.optiontrade.bean.DisplayData;
import com.reloaderscloud.optiontrade.bean.MorrisLineData;
import com.reloaderscloud.optiontrade.bean.OptionChain;
import com.reloaderscloud.optiontrade.bean.OptionEntry;
import com.reloaderscloud.optiontrade.bean.OptionMeta;
import com.reloaderscloud.optiontrade.bean.OptionStrategy;
import com.reloaderscloud.optiontrade.bean.Quote;
import com.reloaderscloud.optiontrade.util.MathUtil;


public class OptionServiceImpl implements OptionService {
	

	private static String YAHOO_OPTION_BASE = "https://finance.yahoo.com/quote/%SYM%/options?p=%SYM%&date=%DATE%";

	private static String YAHOO_OPTION_META_BASE = "https://finance.yahoo.com/quote/%SYM%/options?p=%SYM%";

	private static String DATA_IDENTIFIER = "/* -- Data -- */";
	private static String DATA_START = "root.App.main =";
	private static String DATA_END = "}}}};";
	
	public OptionChain getOptionChain(String symbol, String expireDate) {

		OptionChain chain = new OptionChain();
		chain.setExpire(expireDate);
		chain.setSymbol(symbol);

		String url = YAHOO_OPTION_BASE.replaceAll("%SYM%", symbol);

		url = url.replace("%DATE%", expireDate);

		//System.out.println(url);
		// Retrieve web content
		String content = getWebContent(url);

		try {
			JsonNode baseNode = getJsonContentsNode(content);
			JsonNode storeNode = baseNode.get("context").get("dispatcher").get("stores");
			JsonNode marketStoreNode = storeNode.get("StreamDataStore").get("quoteData");
			// Option info
			JsonNode contactStoreNode = storeNode.get("OptionContractsStore");

			JsonNode metaNode = contactStoreNode.get("meta");
			
			JsonNode strikeNode = metaNode.get("strikes");
			List<String> strikes = new ArrayList<String>();
			Iterator<JsonNode> sts = strikeNode.elements();
			while(sts.hasNext()) {
				JsonNode stNode = sts.next();
				strikes.add(stNode.asText());
			}
			chain.setStrikes(strikes);
			
			JsonNode priceNode = metaNode.get("quote");

			chain.setQuote(getStockQuote(priceNode, marketStoreNode));

			JsonNode contractsNode = contactStoreNode.get("contracts");
			JsonNode callsNode = contractsNode.get("calls");

			HashMap<String, OptionEntry> options = new HashMap<String, OptionEntry>();

			JsonNode putsNode = contractsNode.get("puts");
			Iterator<JsonNode> is = callsNode.elements();

			while (is.hasNext()) {
				JsonNode contract = is.next();
				OptionEntry option = new OptionEntry();
				option.setCallSymbol(contract.get("contractSymbol").asText());
				option.setCall(contract.get("lastPrice").get("raw").asDouble());
				option.setCallAsk(contract.get("ask").get("raw").asDouble());
				option.setCallBid(contract.get("bid").get("raw").asDouble());
				option.setCallVol(contract.get("volume").get("raw").asInt());
				option.setCallIns(contract.get("openInterest").get("raw").asInt());
				option.setStrike(contract.get("strike").get("raw").asDouble());
				options.put("" + option.getStrike(), option);
			}

			is = putsNode.elements();

			while (is.hasNext()) {
				JsonNode contract = is.next();
				double strike = contract.get("strike").get("raw").asDouble();
				OptionEntry option = options.get("" + strike);
				if (option == null)
					option = new OptionEntry();
				option.setPutSymbol(contract.get("contractSymbol").asText());
				option.setPut(contract.get("lastPrice").get("raw").asDouble());
				option.setPutAsk(contract.get("ask").get("raw").asDouble());
				option.setPutBid(contract.get("bid").get("raw").asDouble());
				option.setPutVol(contract.get("volume").get("raw").asInt());
				option.setPutIns(contract.get("openInterest").get("raw").asInt());
			}

			List<OptionEntry> optionList = new ArrayList<OptionEntry>();

			optionList.addAll(options.values());

			Collections.sort(optionList, new OptionEntry());

			chain.setChain(optionList);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return chain;
	}

	public String getWebContent(String url) {
		// Retrieve web content
		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response;
		StringBuffer sb = new StringBuffer();
		try {
			response = client.execute(request);
			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

			String line = "";

			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}

		return sb.toString();

	}

	public OptionMeta getOptionMeta(String symbol) {

		OptionMeta meta = new OptionMeta();

		meta.setSymbol(symbol);

		String url = YAHOO_OPTION_META_BASE.replaceAll("%SYM%", symbol);

		// Retrieve web content
		String content = getWebContent(url);

		try {
			JsonNode baseNode = getJsonContentsNode(content);
			JsonNode storeNode = baseNode.get("context").get("dispatcher").get("stores");
			
			JsonNode marketStoreNode = storeNode.get("StreamDataStore").get("quoteData");

			// Option info
			JsonNode contactStoreNode = storeNode.get("OptionContractsStore");

			JsonNode metaNode = contactStoreNode.get("meta");

			JsonNode priceNode = metaNode.get("quote");

			meta.setQuote(getStockQuote(priceNode, marketStoreNode));

			JsonNode expireNode = metaNode.get("expirationDates");

			Iterator<JsonNode> dates = expireNode.elements();
			List<String> expireDates = new ArrayList<String>();
			while (dates.hasNext()) {
				JsonNode edNode = dates.next();
				expireDates.add(edNode.asText());
			}
			meta.setExpireDates(expireDates);
			
			JsonNode strikeNode = metaNode.get("strikes");
			List<String> strikes = new ArrayList<String>();
			Iterator<JsonNode> sts = strikeNode.elements();
			while(sts.hasNext()) {
				JsonNode stNode = sts.next();
				strikes.add(stNode.asText());
			}
			meta.setStrikes(strikes);

		} catch (Exception e) {

			e.printStackTrace();
		}
		return meta;
	}

	private JsonNode getJsonContentsNode(String content) {
		// Process the content, get the Json contents
		long iPos = content.indexOf(DATA_IDENTIFIER);
		if (iPos > 0) {
			content = content.substring((int) (iPos + DATA_IDENTIFIER.length()));

			iPos = content.indexOf(DATA_START);

			if (iPos > 0) {

				content = content.substring((int) (iPos + DATA_START.length()));

				iPos = content.lastIndexOf(DATA_END);

				content = content.substring(0, (int) (iPos + 4));

				// Now we have the Json data, build with Jackson
				ObjectMapper mapper = new ObjectMapper();
				try {
					JsonNode baseNode = mapper.readTree(content);
					return baseNode;
				} catch (Exception e) {

				}
			}
		}
		return null;
	}

	private Quote getStockQuote(JsonNode priceNode, JsonNode marketStoreNode) {

		Quote quote = new Quote();
		quote.setOpen(priceNode.get("regularMarketOpen").asDouble());
		quote.setPrice(priceNode.get("regularMarketPrice").asDouble());
		quote.setHigh(priceNode.get("regularMarketDayHigh").asDouble());
		quote.setLow(priceNode.get("regularMarketDayLow").asDouble());
		quote.setChange(priceNode.get("regularMarketChange").asDouble());
		
		//Market Quote
		quote.setDowChange(marketStoreNode.get("^DJI").get("regularMarketChange").get("fmt").asDouble());
		quote.setSpChange(marketStoreNode.get("^GSPC").get("regularMarketChange").get("fmt").asDouble());
		//quote.setNasChange(marketStoreNode.get("^GSPC").get("regularMarketChange").get("fmt").asDouble());
		quote.setVixChange(marketStoreNode.get("^VIX").get("regularMarketChange").get("fmt").asDouble());
		quote.setVixChangePercent(marketStoreNode.get("^VIX").get("regularMarketChangePercent").get("fmt").asText());
		quote.setVix(marketStoreNode.get("^VIX").get("regularMarketPrice").get("fmt").asDouble());
		return quote;
	}

	public MorrisLineData getOptionStrategyResults(OptionStrategy strategy) {
		MorrisLineData data = new MorrisLineData();
		OptionChain chain = getOptionChain(strategy.getSymbol(), strategy.getExpire());
		
		HashMap<Double, OptionEntry> optionLookup = new HashMap<Double, OptionEntry>();
		for(OptionEntry oe : chain.getChain()) {
			optionLookup.put(oe.getStrike(), oe);
		}
		
		//Create line data using strike prices
		DisplayData[] md = new DisplayData[chain.getStrikes().size()];
		
		for(int i=0; i<chain.getStrikes().size(); i++) {
			md[i] = new DisplayData();
			
			double price = MathUtil.parseDouble(chain.getStrikes().get(i));
			double profit = getProfit(optionLookup, strategy, price);
			
			md[i].setPrice(price);
			md[i].setProfit(profit);
		}
		
		data.setData(md);
		return data;
	}
	
	
	private double getProfit(HashMap<Double, OptionEntry> optionLookup, OptionStrategy strategy, double price) {
		
		double profit = 0;
		if("single".equals (strategy.getStrategyType())) {
			profit = getOptionProfit(optionLookup, strategy.getAction1(), strategy.getOption1(), strategy.getStrike1(), price);
		} else {
			double profit1 = getOptionProfit(optionLookup, strategy.getAction1(), strategy.getOption1(), strategy.getStrike1(), price);
			double profit2 = getOptionProfit(optionLookup, strategy.getAction2(), strategy.getOption2(), strategy.getStrike2(), price);
			profit = profit1 + profit2;
		}
		
		return profit;
	}
	
	
	private double getOptionProfit(HashMap<Double, OptionEntry> optionLookup, String action, String option, String strike, double price) {
		
		//Strike option entry
		OptionEntry strikeOption = optionLookup.get(Double.parseDouble(strike));
		double credit = 0; //Get when open position 
		double close = 0;  //Profit when close position
		double vstrike = MathUtil.parseDouble(strike);
		//Call option
		if("call".equals(option)) {			
			//Buy call option
			if("buy".equals(action)) {				
				credit = -1 * strikeOption.getCall();	
				if(price <= vstrike) {
					close = 0.0;
				} else {
					close = price - vstrike;
				}
			} else {
				credit = strikeOption.getCall();
				if(price <= vstrike) {
					close = 0.0;
				} else {
					close = vstrike - price;
				}
			}			
			
		
		} else {
			//Buy call option
			if("buy".equals(action)) {				
				credit = -1 * strikeOption.getPut();						
				if(price >= vstrike) {
					close = 0.0;
				} else {
					close = vstrike - price;
				}
			} else {
				credit = strikeOption.getPut();
				if(price >= vstrike) {
					close = 0.0;
				} else {
					close =  price - vstrike;
				}				
			}	
			
		
		}
		
		return MathUtil.round(credit + close);
	}
	
	
	//@Scheduled(cron="0 25 18 ? * MON-FRI")
	public void loadVixOptions() {
		

		
	}

}
