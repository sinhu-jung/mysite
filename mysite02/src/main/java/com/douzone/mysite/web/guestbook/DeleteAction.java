package com.douzone.mysite.web.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.web.Action;
import com.douzone.web.util.MVCUtils;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("no");
		String password = request.getParameter("password");
		
		Long no = Long.parseLong(number);
		
		new GuestbookRepository().delete(no, password);
		MVCUtils.redirect(request.getContextPath() + "/guestbook", request, response);
	}

}
