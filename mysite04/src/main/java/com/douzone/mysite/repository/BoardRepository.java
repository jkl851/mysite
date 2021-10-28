package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	public List<BoardVo> limitedFind(Long pageNum) {
		return sqlSession.selectList("board.limitedFind", pageNum);
	}
	
	public Long maxGroupNoFind() {
		return sqlSession.selectOne("board.maxGroupNoFind");
	}
	
	public Long countContents() {
		return sqlSession.selectOne("board.countContents");
	}
	
	public BoardVo getTitleContent(Long contentNo) {
		return sqlSession.selectOne("board.getTitleContent", contentNo);
	}
	
	public boolean hit(Long contentNo) {
		return 1 == sqlSession.update("board.hit", contentNo);
	}
	
	public BoardVo getGroupOrderDepthNo(Long contentNo) {
		return sqlSession.selectOne("board.getGroupOrderDepthNo", contentNo);
	}
	
	public boolean delete(Long contentNo, String password) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "contentNo", contentNo );
		map.put( "password", password );
		return 1 == sqlSession.delete("board.delete", map);
	}
	
	public boolean insert(BoardVo vo) {
		return 1 == sqlSession.insert("board.insert", vo);
	}
	
	public boolean modify(BoardVo vo) {
		return 1 == sqlSession.update("board.modify", vo);
	}
	
	public boolean replyUpdate(Long orderNo) {
		return 1 == sqlSession.update("board.replyUpdate", orderNo);
	}
}
