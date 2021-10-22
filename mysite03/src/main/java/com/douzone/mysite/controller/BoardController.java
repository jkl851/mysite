package com.douzone.mysite.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageCalVo;
import com.douzone.mysite.vo.UserVo;



@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String view(HttpServletResponse response, HttpServletRequest request,
			@PathVariable("no") Long no, 
			//@CookieValue("visited") Cookie cookie, 
			Model model) {
		Boolean visitedCheck = false;
		Long contentNo = no;
		Cookie[] cookies  = request.getCookies();      
		
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("visited" + contentNo)) {
				visitedCheck = true;
				}
			}                                                                    
		                                                                               
		if (visitedCheck) {                                                            
			BoardVo vo = boardService.getTitleContent(contentNo);                      
			                                                                           
			model.addAttribute("vo", vo);                                              
			return "board/view";                                                       
			                                                                           
		} else {                                                                       
			BoardVo vo = boardService.getTitleContent(contentNo);                      
			boardService.hit(vo.getHit(), contentNo);                                  
			                                                                           
	                                                                                   
			Cookie vCookie = new Cookie("visited" + contentNo, "1");                   
			vCookie.setMaxAge(60); //60초                                              
			response.addCookie(vCookie);                                               
			model.addAttribute("vo", vo);                                              
			return "board/view";                                                       
		}                                                                            
	}
	
	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}
	
	@Auth
	@RequestMapping(value="/write", method=RequestMethod.POST)
	public String write(@AuthUser UserVo authUser, 
			@RequestParam(value = "title", required = true, defaultValue = "") String title,
			@RequestParam(value = "content", required = true, defaultValue = "") String content,
			@RequestParam(value = "oN", required = false) Long orderNo,
			@RequestParam(value = "gN", required = false) Long groupNo,
			@RequestParam(value = "dT", required = false) Long depth) {
		
		if(authUser == null) {
			return "redirect:/user/login";
		}
		/////////////////////////////////////////////////////////
		if (orderNo != null){
			BoardVo vo = new BoardVo();
			vo.setUser_no(authUser.getNo());
			vo.setTitle(title);
			vo.setContents(content);
			vo.setHit(0L);
			vo.setGroup_no(groupNo);
			vo.setOrder_no(orderNo+1L);
			vo.setDepth(depth+1L);
			boardService.insert(vo);
			return "redirect:/board/";
			
		} else {
			BoardVo vo = new BoardVo();

		vo.setUser_no(authUser.getNo());
		vo.setTitle(title);
		vo.setContents(content);
		vo.setHit(0L);
		vo.setGroup_no(boardService.maxGroupNoFind()+1L);
		vo.setOrder_no(0L);
		vo.setDepth(0L);
		boardService.insert(vo);
		return "redirect:/board/";
		}
	}
	@Auth
	@RequestMapping(value = "/reply/{no}", method = RequestMethod.GET)
	public String reply(@AuthUser UserVo authUser, @PathVariable("no") Long no, Model model) {
		// 접근제어(Access Control List : ACL)s
		if(authUser == null) {
			return "redirect:/user/login";
		}
		Long contentNo = no;
		BoardVo vo = boardService.getGroupOrderDepthNo(contentNo);
		model.addAttribute("vo", vo);
		return "board/write";
	}
	@Auth
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long contentNo, Model model) {
		model.addAttribute("no", contentNo);
		return "board/delete";
	}
	
	@Auth
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String delete(
			@RequestParam(value = "no", required = true, defaultValue = "") Long contentNo,
			@RequestParam(value = "password", required = true, defaultValue = "") String password, 
			Model model) {
		boolean result = boardService.delete(contentNo, password);

		if (result) {
			return "redirect:/board";
		} else {
			model.addAttribute("result", "fail");
			model.addAttribute("no", contentNo);
			return "board/delete";
		}
	}
	@Auth
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(
			@AuthUser UserVo authUser,
			@PathVariable("no") Long contentNo, 
			Model model) {
		
		if(authUser == null) {
			return "redirect:/user/login";
		}
		BoardVo vo = boardService.getTitleContent(contentNo);
		model.addAttribute("vo", vo);
		
		return "board/modify";
	}
	@Auth
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(@AuthUser UserVo authUser, 
			@RequestParam(value = "no") Long no,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "content") String content,
			Model model) {
		Long contentNo= no;
		BoardVo vo = boardService.getTitleContent(contentNo);
		
		if(vo.getUser_no() == authUser.getNo()) {
			vo.setTitle(title);
			vo.setContents(content);
			boardService.modify(vo);
			return "redirect:/board/view/"+ contentNo;
		} else {
			return "board/view";
		}
	}
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String list(
			@RequestParam(value = "pageNum", required = false) String SpageNum, 
			@RequestParam(value = "quotient", required = false) String Squotient, 
			@RequestParam(value = "quotientL", required = false) String SquotientL, 
			Model model) {
		
		Long pageNum = 1L;
		if(SpageNum != null) {
			pageNum = Long.parseLong(SpageNum);
		}

		int intpageNum = Math.toIntExact(pageNum);
		int quotient = ((intpageNum-1)/5);
		
		if(Squotient != null) {
			int pagingAction = Integer.parseInt(Squotient);
			quotient = pagingAction;
			pageNum = (long)((quotient*5)+1);
			intpageNum = Math.toIntExact(pageNum);
		} else if (SquotientL != null){
			int pagingAction = Integer.parseInt(SquotientL);
			quotient = pagingAction;
			pageNum = (long)((quotient*5)+5);
			intpageNum = Math.toIntExact(pageNum);
		}
		
		List<BoardVo> list = boardService.limitedFind(pageNum);
		Long countC = boardService.countContents();
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
		model.addAttribute("list", list);
		model.addAttribute("pageList", pageList);
		model.addAttribute("nvo", nvo);
		return "board/list";
	}
	
}
