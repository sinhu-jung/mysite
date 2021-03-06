package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	@GetMapping("/list/{no}")
	public JsonResult list(@PathVariable("no") Long no) {
		System.out.println(no);
		List<GuestbookVo> list = guestbookService.getMessageList(no);
		return JsonResult.success(list);
	}
	
	@PostMapping("/add")
	public JsonResult add(@RequestBody GuestbookVo vo) {
		guestbookService.insertMessage(vo);
		vo.setPassword("");
		return JsonResult.success(vo);
	}
	
	@DeleteMapping("/delete/{no}")
	public JsonResult del(@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="")String password) {
		// 삭제 작업(GuestbookService)
		Long data = 0L;
		boolean result = guestbookService.deleteMessage(no, password);
		// 1. 삭제가 안된 경우
		if(result == false) {
			data = -1L;
		}
		// 2. 삭제가 성공한 경우
		if(result == true) {
			data = no;
		}	
		return JsonResult.success(data);
	}
}
