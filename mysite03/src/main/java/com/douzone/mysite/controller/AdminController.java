package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.service.FileUploadService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	ServletContext application;
	
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
	public String updateMain(
			SiteVo vo,
			@RequestParam("file1") MultipartFile file1) {
		String url = fileUploadService.restore(file1);
		vo.setprofileURL(url);
		adminService.update(vo);
		application.setAttribute("title", vo.getTitle());
		return "redirect:/admin";
	}
}
