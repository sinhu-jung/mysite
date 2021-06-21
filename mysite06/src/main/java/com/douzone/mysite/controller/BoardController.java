package com.douzone.mysite.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public String list(
			@RequestParam( value="p", required=true, defaultValue="1") Integer page, 
			@RequestParam( value="kwd", required=true, defaultValue="") String keyword,
			Model model) {
		Map<String, Object> map = boardService.viewPage(page, keyword);
		model.addAttribute("map", map);
		return "board/list";
	}
	
	@RequestMapping(value="/view")
	public String view(
			@RequestParam( value="no", required=true, defaultValue="") Long no, 
			@RequestParam( value="hit", required=true, defaultValue="") Integer hit,
			Model model) {
		BoardVo vo = boardService.view(no, hit);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.GET)
	public String write(@RequestParam( value="msg", required=true, defaultValue="") String msg, Model model) {
		model.addAttribute("msg", msg);
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@Valid String title, @Valid String content, @AuthUser UserVo authUser,
			@RequestParam( value="msg", required=true, defaultValue="") String msg,
			@RequestParam( value="no", required=true, defaultValue="") Long no
			) {
		if("write".equals(msg)) {
			boardService.writeAction(title, content, authUser);
		} else {
			boardService.commentAction(title, content, no, authUser);
		}
		return "redirect:/board";
	}
	
	@RequestMapping(value="/comment", method=RequestMethod.GET)
	public String comment(
			@RequestParam( value="msg", required=true, defaultValue="") String msg,
			@RequestParam( value="no", required=true, defaultValue="") Long no,
			Model model) {
		model.addAttribute("msg", msg);
		model.addAttribute("no", no);
		return "board/write";
	}
	
	@Auth
	@RequestMapping("/delete")
	public String delete(
			@RequestParam( value="userNo", required=true, defaultValue="") Long userNo, 
			@RequestParam( value="no", required=true, defaultValue="") Long no ) {
		boardService.deleteAction(no, userNo);
		return "redirect:/board";
	}
	
	@Auth
	@RequestMapping(value="/modify", method=RequestMethod.GET )
	public String modify(
			@RequestParam( value="userNo", required=true, defaultValue="") Long userNo,
			@RequestParam( value="no", required=true, defaultValue="") Long no ,
			@AuthUser UserVo authUser,
			Model model) {
		model.addAttribute("userNo", userNo);
		model.addAttribute("no", no);
		model.addAttribute("authNo", authUser.getNo());
		return "board/modify";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST )
	public String modify(
			@Valid BoardVo vo,
			@RequestParam( value="no", required=true, defaultValue="") Long no ) {
		vo.setNo(no);
		boardService.modify(vo);
		return "redirect:/board";
	}
}
