package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public boolean delete(Long contentNo, String password) {
		return boardRepository.delete(contentNo, password);
	} 
	
	public boolean insert(BoardVo vo) {
		return boardRepository.insert(vo);
	} 
	
	public Long maxGroupNoFind() {
		return boardRepository.maxGroupNoFind();
	} 
	
	public BoardVo getTitleContent(Long contentNo) {
		return boardRepository.getTitleContent(contentNo);
	} 
	
	public boolean hit(Long hit, Long contentNo) {
		return boardRepository.hit(hit, contentNo);
	} 
	
	public BoardVo getGroupOrderDepthNo(Long contentNo) {
		return boardRepository.getGroupOrderDepthNo(contentNo);
	}
	
	public boolean modify(BoardVo vo) {
		return boardRepository.modify(vo);
	} 
	
	public List<BoardVo> limitedFind(Long pageNum) {
		return boardRepository.limitedFind(pageNum);
	}
	
	public Long countContents() {
		return boardRepository.countContents();
	}
}
