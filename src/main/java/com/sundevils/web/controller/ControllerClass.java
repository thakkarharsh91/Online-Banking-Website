package com.sundevils.web.controller;

import gunpreetPackage.BusinessLogic.CheckAuthentication;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerClass {

	@RequestMapping("/reset")
	public ModelAndView resetPasswordFunc(HttpSession session){
		ModelAndView modelandview = new ModelAndView("resetpassword");
		String userName = (String)session.getAttribute("USERNAME");
		modelandview.addObject("user", userName);
		return modelandview;
	}	

	@RequestMapping(value="/resetButton", method = RequestMethod.POST)
	public ModelAndView reset(
			@RequestParam (value="current") String presenttpassword,
			@RequestParam (value="new") String newpassword,
			@RequestParam (value="confirm") String confirmpassword,HttpSession session) throws ClassNotFoundException{

		String userName = (String)session.getAttribute("USERNAME");
		ModelAndView model = new ModelAndView();
		model.addObject("user", userName);
		CheckAuthentication auth= new CheckAuthentication();
		if(presenttpassword.equals("") || newpassword.equals("") || confirmpassword.equals("")){
			model.addObject("emptyFields", "All fields are mandatory");
			model.setViewName("resetpassword");
		}
		else{
			String result = auth.check(userName,presenttpassword, newpassword, confirmpassword);
			if(!result.equals("")){
				model.addObject("errormessage", result);
				model.setViewName("resetpassword");
			}
			else
				model.addObject("success", "Your password has been successfully changed.");
				model.setViewName("resetpassword");
		}
		return model;
	}	
}
