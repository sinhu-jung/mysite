package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.web.Action;
import com.douzone.web.util.MVCUtils;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long no = Long.parseLong(request.getParameter("no"));
		Long userNo = Long.parseLong(request.getParameter("userNo"));
		
		new BoardRepository().delete(no, userNo);
		MVCUtils.redirect(request.getContextPath() + "/board", request, response);
	}

}
