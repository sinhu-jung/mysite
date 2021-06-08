package com.douzone.mysite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo vo = adminService.viewPage();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("user")
	public String user() {
		return "admin/user";
	}
	
	@RequestMapping(value="/main/update", method=RequestMethod.POST)
	public String updateMain(SiteVo vo) {
		adminService.update(vo);
		return "redirect:/";
	}
}
