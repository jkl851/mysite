
package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping("")
	public String list(Model model) throws Exception {
		List<GuestbookVo> list = guestbookService.list();
		model.addAttribute("list", list);
		return "guestbook/list";
	}
	
	////////////////////////////////////     SPA
	@RequestMapping("/spa")
	public String spa() {
		return "guestbook/index-spa";
	}
	
	////////////////////////////////////     SPA
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(GuestbookVo vo) {
		guestbookService.add(vo);
		return "redirect:/guestbook/";
	}

	@RequestMapping(value="/delete/{no}", method=RequestMethod.GET)
	public String delete(
			@PathVariable("no") Long no, 
			Model model) {
		model.addAttribute("no", no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public String delete(
			@RequestParam(value="no", required=true, defaultValue="") Long no, 
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		guestbookService.delete(no, password);
		return "redirect:/guestbook/";
	}

	
}
