package com.douzone.mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	
	@RequestMapping(value="/{page}", method=RequestMethod.GET)
	public String list(
			@PathVariable("page") int page, 
			String kwd,
			Model model) {
		Map<String, Integer> map = boardService.viewPage(page, kwd);
		List<BoardVo> list = boardService.viewList(map.get("currentPage"), kwd);
		model.addAttribute("page", map);
		model.addAttribute("list", list);
		return "board/list";
	}
	
	@RequestMapping(value="/view/{no}/{hit}")
	public String view(@PathVariable("no") Long no, @PathVariable("hit") int hit, Model model ) {
		BoardVo vo = boardService.view(no, hit);
		model.addAttribute("vo", vo);
		return "board/view";
	}
	
	@RequestMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(String title, String content, @AuthUser UserVo authUser) {
		boardService.writeAction(title, content, authUser);
		return "redirect:/board/0";
	}
	
	@RequestMapping("/delete/{userNo}/{no}")
	public String delete(@PathVariable("userNo") Long userNo, @PathVariable("no") Long no ) {
		boardService.deleteAction(no, userNo);
		return "redirect:/board/0";
	}
	
	public String comment() {
		
	}
	
}
