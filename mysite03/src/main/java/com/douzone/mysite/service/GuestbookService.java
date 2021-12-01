package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> list() throws Exception {
		return guestbookRepository.findAll();
	} 
	
	public List<GuestbookVo> defaultListOfSPA() throws Exception {
		return guestbookRepository.defaultListOfSPA();
	} 
	
	public List<GuestbookVo> list(Long no) throws Exception {
		return guestbookRepository.findAll(no);
	} 
	
	public boolean add(GuestbookVo vo) {
		return guestbookRepository.insert(vo);
	} 
	
	public void addSPA(GuestbookVo vo) {
		guestbookRepository.insertSPA(vo);
	}
	
	public boolean delete(Long no, String password) {
		GuestbookVo vo = new GuestbookVo();
		vo.setNo(no);
		vo.setPassword(password);
		return guestbookRepository.delete(vo);
	} 
}
