package com.douzone.mysite.web.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.Action;
import com.douzone.web.util.MVCUtils;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int page = 0;
		if(request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}
		int count = new BoardRepository().count();
		int firstpage = 0;
		int lastpage = (int) Math.ceil(count/5);
		
		List<BoardVo> list = new BoardRepository().findAll(page);
		int size = list.size();
		
		request.setAttribute("firstPage", firstpage);
		request.setAttribute("lastPage", lastpage);
		request.setAttribute("size", size);
		request.setAttribute("list", list);
		MVCUtils.forward("board/list", request, response);
	}

}
