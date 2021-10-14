package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.dao.GuestbookDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.GuestbookVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		if(authUser == null) {
			MvcUtil.forward("user/loginform", request, response);
			return;
		}
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardVo();
		vo.setUser_no(authUser.getNo());
		vo.setTitle(title);
		vo.setContents(content);
		vo.setHit(0L);
		vo.setGroup_no(new BoardDao().maxGroupNoFind()+1L);
		vo.setOrder_no(0L);
		vo.setDepth(0L);

		new BoardDao().insert(vo);

		MvcUtil.redirect("/mysite02/board", request, response);
	}

}
