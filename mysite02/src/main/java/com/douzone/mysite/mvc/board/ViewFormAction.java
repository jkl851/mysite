package com.douzone.mysite.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ViewFormAction implements Action {
	private Boolean visitedCheck = false;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long contentNo = Long.parseLong(request.getParameter("no"));
		Cookie[] cookies  = request.getCookies(); //hit(조회수) 조작 방지 
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("visited" + contentNo)) {
				visitedCheck = true;
				}
			}
		
		if (visitedCheck) {
			BoardVo vo = new BoardDao().getTitleContent(contentNo);
			
			request.setAttribute("vo", vo);
			MvcUtil.forward("board/view", request, response);
			
		} else {
			BoardVo vo = new BoardDao().getTitleContent(contentNo);
			new BoardDao().hit(vo.getHit(), contentNo);
			

			Cookie vCookie = new Cookie("visited" + contentNo, "1");
			vCookie.setMaxAge(60); //60초
			response.addCookie(vCookie);
			request.setAttribute("vo", vo);
			MvcUtil.forward("board/view", request, response);
		}
	}

}
