package com.example.admindemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admindemo.model.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IndexController extends BaseController{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 登陆后首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index",method= RequestMethod.GET)
	public String index(Model model) {
		JSONObject jsonObject = new JSONObject();
		Users users = super.getUsers();
		logger.info(jsonObject.toJSONString(users));
		if(null!=users) {
			model.addAttribute("users",users);
		}
		return "index";
	}
	@RequestMapping("/")
	public String home(){
		return "home";
	}
	@RequestMapping("/setting")
	public String tests(Model model){
		System.out.println("请求：======>>setting");
		return "/collect/setting";
	}
	@RequestMapping("/notice")
	public String notice(Model model){
		System.out.println("请求：======>>notice");
		return "/collect/notice";
	}

}
