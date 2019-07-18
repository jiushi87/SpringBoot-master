package com.example.admindemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.example.admindemo.model.Users;



@Controller
public class IndexController extends BaseController{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 登陆后首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String home(Model model) {
		JSONObject jsonObject = new JSONObject();
		Users users = super.getUsers();
		logger.info(jsonObject.toJSONString(users));
		if(null!=users) {
			model.addAttribute("users",users);
		}
		return "index";
	}
}
