package com.example.admindemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/** 自定义资源映射 */
@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

  /**
   * 自定义映射login为首页
   *
   * @param registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("login");
  }

  /**
   * 静态文件映射
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("/resources/static/**")
        .addResourceLocations("classpath:/static/")
        .addResourceLocations("classpath:/templates/");
    //SpringBoot配置虚拟化路径用于图片的展示
    registry.addResourceHandler("/file/**").addResourceLocations("file:/E:/temp/file/");
    //第一个路径是数据库存入的路径第一个文件夹，第二个参数是图片在本地的要映射的路径
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry
        .addInterceptor(new LocaleChangeInterceptor())
        .addPathPatterns("/**")
        .excludePathPatterns("/**.js", "/**.css", "/**.css", "/**.svg", "/**.html", "/**.png");
  }
}
