package com.douzone.mysite.mvc.guestbook;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MVCUtils;
import com.douzone.mysite.repository.GuestbookRepository;

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
