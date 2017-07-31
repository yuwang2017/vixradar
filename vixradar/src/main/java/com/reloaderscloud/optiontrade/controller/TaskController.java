package com.reloaderscloud.optiontrade.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.reloaderscloud.optiontrade.bean.OptionChain;
import com.reloaderscloud.optiontrade.bean.OptionMeta;
import com.reloaderscloud.optiontrade.service.OptionService;

@RequestMapping("/tasks")
public class TaskController extends AbstractController {
	
	@Autowired
	OptionService optionService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/checkvix")
	@ResponseBody
	public void getOptionChain(HttpServletRequest request, HttpServletResponse response) {
		
		OptionMeta meta = optionService.getOptionMeta("vix");
		
		
		
	}

}
