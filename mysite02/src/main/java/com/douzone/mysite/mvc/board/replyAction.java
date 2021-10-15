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

public class replyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		Long contentNo = Long.parseLong(request.getParameter("no"));
		BoardVo vo = new BoardDao().getGroupOrderDepthNo(contentNo);
		request.setAttribute("vo", vo);
		MvcUtil.forward("board/write", request, response);
	}

}
