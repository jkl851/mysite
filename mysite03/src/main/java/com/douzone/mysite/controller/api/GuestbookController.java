package com.douzone.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestbookVo;

@RestController("guestbookControllerApi")
@RequestMapping("/guestbook/api")
public class GuestbookController {
	@Autowired
	private GuestbookService guestbookService;
	
	
	@GetMapping("/list/{no}")
	@ResponseBody
	public JsonResult list(@PathVariable("no") Long startNo) throws Exception {
		List<GuestbookVo> list = guestbookService.list(startNo);
		return JsonResult.success(list);
	}

	@PostMapping("/add")
	@ResponseBody
	public JsonResult add(@RequestBody GuestbookVo vo) {
		guestbookService.addSPA(vo);
		vo.setPassword("");
		return JsonResult.success(vo);
	}

	@DeleteMapping("/delete/{no}")
	@ResponseBody
	public JsonResult delete(
			@PathVariable("no") Long no,
			@RequestParam(value="password", required=true, defaultValue="") String password) {
		boolean result = guestbookService.delete(no, password);
		return JsonResult.success(result ? no : -1);
	}
}