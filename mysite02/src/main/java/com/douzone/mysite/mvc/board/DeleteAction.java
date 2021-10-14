package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long contentNo = Long.parseLong(request.getParameter("no"));
		String password = request.getParameter("password");
		
		boolean result = new BoardDao().delete(contentNo, password);
		
		if(result == true) {
			MvcUtil.redirect("board?a=success", request, response);
		} else {
			request.setAttribute("result", "fail");
			MvcUtil.forward("board/deleteform", request, response);
		}
	}

}
