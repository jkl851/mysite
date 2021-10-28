package com.douzone.mysite.repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class AdminRepository {
	@Autowired
	private SqlSession sqlSession;

	public Boolean update(SiteVo vo) {
		return 1 == sqlSession.update("site.update", vo);
	}

	public SiteVo findAll() {
		return sqlSession.selectOne("site.findAll");
	}
}