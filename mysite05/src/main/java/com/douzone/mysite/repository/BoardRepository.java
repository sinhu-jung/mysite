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
	
	public Boolean insert(BoardVo vo) {
		int count = sqlSession.insert("board.insert", vo);
		return count == 1;
	}
	
	public BoardVo findById(Long no) {
		return sqlSession.selectOne("board.findById", no);
	}
	
	public boolean delete(Long no, Long userNo) {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("no", no);
		map.put("userNo", userNo);
		int count =  sqlSession.delete("board.delete", map);
		return count == 1;
	}
	
	public boolean modify(BoardVo vo) {
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}
	
	public boolean updateHit(BoardVo vo) {
		int count = sqlSession.update("board.updateHit", vo);
		return count == 1;
	}
	
	public boolean insertComment(BoardVo vo) {
		int count = sqlSession.insert("board.insertComment", vo);
		return count == 1;
	}

	public Boolean updatComment(BoardVo vo) {
		int count = sqlSession.update("board.updateComment", vo);
		return count == 1;
	}
	
	public int findcount(String keyword) {
		return sqlSession.selectOne("board.findcount", keyword);
	}
	

	public List<BoardVo> findAllByPageAndKeword(String kwd, int page, int listSize) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("kwd", kwd);
		map.put("start", (page-1)*listSize);
		map.put("size", listSize);
		
		return sqlSession.selectList("board.findAllByPageAndKeword", map);
	}

}
