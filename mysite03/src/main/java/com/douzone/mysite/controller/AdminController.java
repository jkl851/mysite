package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.GalleryServiceException;
import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.AdminService;
import com.douzone.mysite.vo.SiteVo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private static final Log LOGGER = LogFactory.getLog(AdminController.class);
	
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private AdminService adminService;  
	
	@Auth(role="ADMIN")
	@RequestMapping("")
	public String main(Model model) {
		SiteVo siteVo = adminService.getSite();
		model.addAttribute("siteVo", siteVo);
		return "admin/main";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String upload(
		SiteVo siteVo,
		@RequestParam("file") MultipartFile file) {
		
		try {
			String url = adminService.saveImage(file);
			siteVo.setProfile(url);
		} catch (GalleryServiceException e) {
			LOGGER.info(e);
		}
		
		adminService.UpdateSite(siteVo);
		
		servletContext.setAttribute("siteVo", siteVo);
		
		return "redirect:/admin";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@Auth(role="ADMIN")
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
