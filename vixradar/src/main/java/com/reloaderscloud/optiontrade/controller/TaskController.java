package com.reloaderscloud.optiontrade.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.reloaderscloud.optiontrade.bean.OptionChain;
import com.reloaderscloud.optiontrade.bean.OptionMeta;
import com.reloaderscloud.optiontrade.service.CloudService;
import com.reloaderscloud.optiontrade.service.OptionService;

@Controller
@RequestMapping("/tasks")
public class TaskController extends AbstractController {
	
	@Autowired
	OptionService optionService;
	
	@Autowired
	CloudService cloudService;

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@RequestMapping("/checkvix")
	@ResponseBody
	public void getOptionChain(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("VIX Jump!");
		OptionMeta meta = optionService.getOptionMeta("vix");
		if(meta.getQuote().getVixChange() > 0.01) {
			//Send email
			cloudService.sendEmail("7038551497@sms.mycricket.com", "VIX Alert", "VIX " + meta.getQuote().getVixChangePercent());
			cloudService.sendEmail("7037286380@tmomail.net", "VIX Alert", "VIX " + meta.getQuote().getVixChangePercent());
			System.out.println("VIX Jump!");
		}
		response.setStatus(200);
		try {
			response.getOutputStream().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
