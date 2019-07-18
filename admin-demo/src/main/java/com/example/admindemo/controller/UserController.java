package com.example.admindemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admindemo.comm.Const;
import com.example.admindemo.model.Users;
import com.example.admindemo.model.result.ExceptionMsg;
import com.example.admindemo.model.result.ResponseData;
import com.example.admindemo.repository.UsersRepository;
import com.example.admindemo.utils.Des3EncryptionUtil;
import com.example.admindemo.utils.MD5Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
public class UserController extends BaseController{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UsersRepository usersRepository;

	/**
	 * 登录
	 * 
	 * @param users
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseData login(@RequestBody Users users, HttpServletResponse response) {
		JSONObject jsonObject = new JSONObject();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
		String usersjsonString = jsonObject.toJSONString(users);
		try {
			logger.info(sdf.format(date) + ":开始========>>" + usersjsonString);
			if (users.getUserName() == null || users.getPassWord() == null || "".equals(users.getUserName())
					|| "".equals(users.getPassWord())) {
				logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.FAILUREUSERS.getMsg());
				return new ResponseData(ExceptionMsg.FAILUREUSERS);
			}
			Users loginUsers = usersRepository.findUsersByUserName(users.getUserName());
			logger.info(jsonObject.toJSONString(loginUsers));
			if (loginUsers == null) {
				logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.LoginNameNotExists.getMsg());
				return new ResponseData(ExceptionMsg.LoginNameNotExists);
			}
			String encrypt = MD5Util.encrypt(users.getPassWord());
			logger.info(encrypt);
			if (!encrypt.equals(loginUsers.getPassWord())) {
				logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.LoginNameOrPassWordError.getMsg());
				return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
			}
			Cookie cookie = new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUsers.getId().toString()));
			cookie.setMaxAge(Const.COOKIE_TIMEOUT);
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUsers);
			String preurl = "/index";
			logger.info(sdf.format(date) + ":成功");
			return new ResponseData(ExceptionMsg.SUCCESS, preurl);
		} catch (Exception e) {
			logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.FAILED.getMsg());
			logger.error("login failed, ", e);
			return new ResponseData(ExceptionMsg.FAILED);
		}
	}
}
