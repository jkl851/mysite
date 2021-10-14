package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		Long contentNo= Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().getTitleContent(contentNo);
		
		if(vo.getUser_no() == authUser.getNo()) {
			vo.setTitle(request.getParameter("title"));
			vo.setContents(request.getParameter("content"));
			new BoardDao().update(vo);
			MvcUtil.redirect("board?a=viewform&no="+ contentNo, request, response);
		} else {
			MvcUtil.forward("board/list", request, response);
		}
		
	}

}
