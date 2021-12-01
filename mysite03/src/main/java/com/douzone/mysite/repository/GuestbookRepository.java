package com.douzone.mysite.repository;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.douzone.mysite.exception.GuestbookRepositoryException;
import com.douzone.mysite.vo.GuestbookVo;

@Repository
public class GuestbookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestbookVo> findAll() throws GuestbookRepositoryException {
		return sqlSession.selectList("guestbook.findAll");
	}
	
	public List<GuestbookVo> defaultListOfSPA() throws GuestbookRepositoryException {
		return sqlSession.selectList("guestbook.findAllOfSPA");
	}
	
	public List<GuestbookVo> findAll(Long no) throws GuestbookRepositoryException {
		return sqlSession.selectList("guestbook.findAllByNo", no);
	}
	
	public boolean delete(GuestbookVo vo) {
		int count = sqlSession.delete("guestbook.delete", vo);
		return count == 1;		

	}
	
	public boolean insert(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insert", vo);
		return count == 1;
	}
	
	public boolean insertSPA(GuestbookVo vo) {
		int count = sqlSession.insert("guestbook.insertSPA", vo);
		return count == 1;
	}
}