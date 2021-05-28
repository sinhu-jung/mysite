package com.douzone.mysite.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mvc.Action;
import com.douzone.mvc.util.MVCUtils;
import com.douzone.mysite.repository.UserRepository;
import com.douzone.mysite.vo.UserVo;

public class LoginAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserVo userVo = new UserRepository().findByEmailAndPassword(email, password);
		if(userVo == null) {
			request.setAttribute("result", "fail");
			request.setAttribute("email", email);
			MVCUtils.forward("user/loginform", request, response);
			return;
		}
		
		//인증처리(session 처리)
		//true면 없으면 만들어 달라는 이야기 이고 false면 없으면 null을 달라는 이야기임
		HttpSession session = request.getSession(true);
		session.setAttribute("authUser", userVo);
		
		//main으로 redirect
		MVCUtils.redirect(request.getContextPath(), request, response);
	}

}
