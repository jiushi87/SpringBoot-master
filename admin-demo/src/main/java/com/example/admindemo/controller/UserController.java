package com.example.admindemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admindemo.comm.Const;
import com.example.admindemo.model.Users;
import com.example.admindemo.model.result.ExceptionMsg;
import com.example.admindemo.model.result.ResponseData;
import com.example.admindemo.repository.UsersRepository;
import com.example.admindemo.utils.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UserController extends BaseController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired UsersRepository usersRepository;

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
			// 验证用户名密码不为空
			if (users.getUserName() == null
					|| users.getPassword() == null
					|| "".equals(users.getUserName())
					|| "".equals(users.getPassword())) {
				logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.FAILUREUSERS.getMsg());
				return new ResponseData(ExceptionMsg.FAILUREUSERS);
			}
			// 判断用户是否存在
			Users loginUsers = usersRepository.findUsersByUserName(users.getUserName());
			logger.info(jsonObject.toJSONString(loginUsers));
			if (loginUsers == null) {
				logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.LoginNameNotExists.getMsg());
				return new ResponseData(ExceptionMsg.LoginNameNotExists);
			}
			// 判断用户密码是否正确
			String encrypt = MD5Util.encrypt(users.getPassword());
			logger.info(encrypt);
			if (!encrypt.equals(loginUsers.getPassword())) {
				logger.info(
						sdf.format(date) + ":结束========>>" + ExceptionMsg.LoginNameOrPassWordError.getMsg());
				return new ResponseData(ExceptionMsg.LoginNameOrPassWordError);
			}
			// 写入cookie
			Cookie cookie =
					new Cookie(Const.LOGIN_SESSION_KEY, cookieSign(loginUsers.getId().toString()));
			cookie.setMaxAge(Const.COOKIE_TIMEOUT);
			cookie.setPath("/");
			response.addCookie(cookie);
			getSession().setAttribute(Const.LOGIN_SESSION_KEY, loginUsers);
			// 成功跳转首页
			String preurl = "/index";
			logger.info(sdf.format(date) + ":结束========>>:成功");
			return new ResponseData(ExceptionMsg.SUCCESS, preurl);
		} catch (Exception e) {
			logger.info(sdf.format(date) + ":结束========>>" + ExceptionMsg.FAILED.getMsg());
			logger.error("login failed, ", e);
			return new ResponseData(ExceptionMsg.FAILED);
		}
	}
}
