package com.example.admindemo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.admindemo.comm.Const;
import com.example.admindemo.model.Users;
import com.example.admindemo.model.result.ExceptionMsg;
import com.example.admindemo.model.result.ResponseData;
import com.example.admindemo.repository.UsersRepository;
import com.example.admindemo.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Controller
public class IndexController extends BaseController {
  protected Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired UsersRepository usersRepository;

  @Value("${static.url}")
  private String staticUrl;

  @Value("${file.profilepictures.url}")
  private String fileProfilepicturesUrl;

  /**
   * 登陆后首页
   *
   * @param model
   * @return
   */
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index(Model model) {
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
    logger.info(sdf.format(date) + ":========开始========");
    JSONObject jsonObject = new JSONObject();
    Users users = super.getUsers();
    logger.info(jsonObject.toJSONString(users));
    if (null != users) {
      model.addAttribute("users", users);
      logger.info(sdf.format(date) + ":home========结束========");
      return "home";
    } else {
      logger.info(sdf.format(date) + ":login========结束========");
      return "login";
    }
  }

  @RequestMapping("/")
  public String home() {
    return "login";
  }

  /**
   * 设置个人信息
   *
   * @param model
   * @param id
   * @return
   */
  @RequestMapping("/setting/{id}")
  public String tests(Model model, @PathVariable("id") Integer id) {
    Date date = new Date();
    JSONObject jsonObject = new JSONObject();
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
    logger.info(sdf.format(date) + ":========开始========>>+" + id);
    Users settingUsers = usersRepository.findUsersById(id);
    model.addAttribute("settingUsers", settingUsers);
    logger.info(sdf.format(date) + ":========结束========" + jsonObject.toJSONString(settingUsers));
    return "collect/setting";
  }

  @RequestMapping("/notice")
  public String notice(Model model) {
    System.out.println("请求：======>>notice");
    return "collect/notice";
  }

  @RequestMapping("/profileSettings")
  public String profileSettings() {
    System.out.println("请求：======>>profileSettings");
    return "collect/profile";
  }

  @RequestMapping("/privacyAndSecurity")
  public String privacyAndSecurity() {
    System.out.println("请求：======>>profileSettings");
    return "collect/privacyAndSecurity";
  }

  @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
  public ResponseData uploadHeadPortrait(@RequestBody String dataURL) {
    Date date = new Date();
    JSONObject jsonObject = new JSONObject();
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd hh:mm:ss");
    logger.info(sdf.format(date) + ":========执行 上传头像 开始========" + dataURL);
    if (StringUtils.isEmpty(dataURL)) {
      return new ResponseData(ExceptionMsg.FAILED);
    }
    try {
      String filePath = staticUrl + fileProfilepicturesUrl;
      String fileName = UUID.randomUUID().toString() + ".png";
      String savePath = fileProfilepicturesUrl + fileName;
      String image = dataURL;
      String header = "data:image";
      String[] imageArr = image.split(",");
      if (imageArr[0].contains(header)) {
        image = imageArr[1];
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(image);
        FileUtil.uploadFile(decodedBytes, filePath, fileName);
        Users users = super.getUsers();
        usersRepository.updateProfilePicture(savePath, users.getId());
        Users user = usersRepository.findUsersById(users.getId());
        super.getSession().setAttribute(Const.LOGIN_SESSION_KEY, user);
        logger.info("========" + jsonObject.toJSONString(user) + "========");
      }
      logger.info("========头像地址========" + savePath);
      logger.info(sdf.format(date) + "：========执行 上传头像 结束========");
      return new ResponseData(ExceptionMsg.SUCCESS, savePath);
    } catch (Exception e) {
      logger.error("upload head portrait failed, ", e);
      return new ResponseData(ExceptionMsg.FAILED);
    }
  }
}
