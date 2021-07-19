package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Component
public class initController implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	ServletContext application;
	
	@Autowired
	private AdminService adminService;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		SiteVo vo = adminService.viewPage();
		application.setAttribute("title", vo.getTitle());
	}
}
