package com.douzone.mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class AdminRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean update(SiteVo vo) {
		int count = sqlSession.update("admin.update", vo);
		return count == 1;
	}

	public SiteVo findAll() {
		return sqlSession.selectOne("admin.findAll");
	}
	
}
