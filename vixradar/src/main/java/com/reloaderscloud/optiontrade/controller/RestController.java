package com.reloaderscloud.optiontrade.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.reloaderscloud.optiontrade.bean.MorrisLineData;
import com.reloaderscloud.optiontrade.bean.OptionChain;
import com.reloaderscloud.optiontrade.bean.OptionMeta;
import com.reloaderscloud.optiontrade.bean.OptionStrategy;
import com.reloaderscloud.optiontrade.service.CloudService;
import com.reloaderscloud.optiontrade.service.OptionService;

@Controller
public class RestController extends AbstractController {
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	CloudService cloudService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@RequestMapping("/getOptionChain")
	@ResponseBody
	public  OptionChain getOptionChain(@RequestParam String symbol, @RequestParam String expireDate){
		//System.out.println("getOptionChain called");
		cloudService.saveFile("", null);
		return optionService.getOptionChain(symbol, expireDate);
	}
	
	@RequestMapping("/getOptionMeta")
	@ResponseBody
	public  OptionMeta getOptionMeta(@RequestParam String symbol){
		//System.out.println("getOptionMeta called");
		return optionService.getOptionMeta(symbol);
	}
	
	@RequestMapping("/getOptionStrategyResults")
	@ResponseBody
	public  MorrisLineData getOptionStrategyResults(@RequestBody OptionStrategy strategy){
		
		return optionService.getOptionStrategyResults(strategy);
	}
	
}
