package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Controller
public class MainController {
	@Autowired
	ServletContext application;
	
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("")
	public String index(Model model) {
		SiteVo vo = adminService.viewPage();
		model.addAttribute("vo", vo);
		application.setAttribute("title", vo.getTitle());
		return"main/index";
	}
}
