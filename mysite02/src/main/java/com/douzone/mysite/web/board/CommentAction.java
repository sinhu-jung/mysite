package com.douzone.mysite.web.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.Action;
import com.douzone.web.util.MVCUtils;

public class CommentAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session == null) {
			MVCUtils.redirect(request.getContextPath(), request, response);
			return;
		}
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			MVCUtils.redirect(request.getContextPath(), request, response);
			return;
		}

		Long userNo = authUser.getNo();
		Long no = Long.parseLong(request.getParameter("no"));
		String title = request.getParameter("title");
		String contents = request.getParameter("content");

		BoardVo vo = new BoardRepository().findById(no);
		int groupNo = vo.getGroupNo();
		int depth = vo.getDepth();
		int orderNo = vo.getOrderNo();

		if (vo.getDepth() == 0) {
			vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(userNo);
			vo.setGroupNo(groupNo);
			vo.setOrderNo(1);
			vo.setDepth(depth + 1);
			new BoardRepository().updatComment(groupNo);
			new BoardRepository().insertComment(vo);
			MVCUtils.redirect(request.getContextPath() + "/board", request, response);
		} else if (vo.getDepth() >= 1) {
			vo = new BoardVo();
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setUserNo(userNo);
			vo.setGroupNo(groupNo);
			vo.setOrderNo(orderNo + 1);
			vo.setDepth(depth + 1);
			new BoardRepository().updatComment2(groupNo, orderNo);
			new BoardRepository().insertComment(vo);
			MVCUtils.redirect(request.getContextPath() + "/board", request, response);
		}
	}

}
