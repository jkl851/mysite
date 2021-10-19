package com.douzone.mysite.mvc.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageCalVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.MvcUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long pageNum = 1L;
		if(request.getParameter("pageNum") != null) {
			pageNum = Long.parseLong(request.getParameter("pageNum"));
		}

		int intpageNum = Math.toIntExact(pageNum);
		int quotient = ((intpageNum-1)/5);
		
		if(request.getParameter("quotient") != null) {
			int pagingAction = Integer.parseInt(request.getParameter("quotient"));
			quotient = pagingAction;
			pageNum = (long)((quotient*5)+1);
			intpageNum = Math.toIntExact(pageNum);
		} else if (request.getParameter("quotientL") != null){
			int pagingAction = Integer.parseInt(request.getParameter("quotientL"));
			quotient = pagingAction;
			pageNum = (long)((quotient*5)+5);
			intpageNum = Math.toIntExact(pageNum);
		}
		
		List<BoardVo> list = new BoardDao().limitedFind(pageNum);
		Long countC = new BoardDao().countContents();
		int maxPageNum = (int)(Math.ceil((double)countC/10d));
		int maxQuotient = (int)(Math.ceil(((double)maxPageNum-1d)/5d));
		
		ArrayList<Integer> pageList = new ArrayList<Integer>();
		for (int i=1; i<=5; i++) {
			pageList.add(i-1, (quotient*5)+i);
		}
		
		PageCalVo nvo= new PageCalVo();
		
		nvo.setPageNum(intpageNum);
		nvo.setMaxPageNum(maxPageNum);
		nvo.setQuotient(quotient);
		nvo.setMaxQuotient(maxQuotient);
		request.setAttribute("list", list);
		request.setAttribute("pageList", pageList);
		request.setAttribute("nvo", nvo);
		MvcUtil.forward("board/list", request, response);
	}

}
