package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.Action;
import com.douzone.web.util.MVCUtils;

public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = request.getParameter("msg");
		Long no = 0L;
		if(request.getParameter("no") != null) {
			no = Long.parseLong(request.getParameter("no"));
		}
		if(msg == null) {
			request.setAttribute("msg", "write");
		} else if (msg.equals("comment")) {
			request.setAttribute("msg", msg);
			request.setAttribute("no", no);
		}
		MVCUtils.forward("board/write", request, response);
	}

}
