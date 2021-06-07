package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;
	
	public Map<String, Integer> viewPage(int page, String kwd) {
		int count = 0;
		if (kwd == null) {
			count = boardRepository.count();
		} else {
			count = new BoardRepository().findcount(kwd);
		}
		int firstPage, currentPage, displayRow, leftPage, rightPage, lastPage;
		
		if(page == 0) {
			currentPage = 1;
		} else {
			currentPage = page;
		}
		int nextPage = currentPage + 1;
		int prevPage = currentPage - 1;
		
		displayRow = 5;
		
		leftPage = currentPage - 2;
		rightPage = currentPage + 2;
		
		int totalPage = (int) Math.ceil(count/ displayRow);
		
		if(leftPage < 2) {
			leftPage = 1;
			rightPage = 5;
		}
		
		if(rightPage > totalPage) {
			rightPage = totalPage;
			leftPage = totalPage - 4;
		}
		
		if(totalPage == 1 ) {
			lastPage = 1;
		} else {
			lastPage = totalPage;
		}
		
		if(totalPage < 5) {
			leftPage = 1;
			rightPage = totalPage;
		}
		
		firstPage = 1;
		
		if(totalPage == 0) {
			leftPage = 1;
			rightPage = 1;
			lastPage = 1;
		}
		
		Map<String, Integer> map = new HashMap<>();
		map.put("currentPage", currentPage);
		map.put("lastPage", lastPage);
		map.put("nextPage", nextPage);
		map.put("prevPage", prevPage);
		map.put("totalPage", totalPage);
		map.put("firstPage", firstPage);
		map.put("count", count);
		map.put("leftPage", leftPage);
		map.put("rightPage", rightPage);
		map.put("displayRow", displayRow);
		
		return map;
	}

	public List<BoardVo> viewList(int page, String kwd) {
		List<BoardVo> list = null;
		if(kwd == null) {
			list = boardRepository.findAll(page);
		} else {
			list = boardRepository.findAllkey(page, kwd);
		}
		return list;
	}

	public BoardVo view(Long no, int hit) {
		BoardVo vo = boardRepository.findById(no);
		vo.setHit(hit+1);
		boardRepository.updateHit(vo);
		return vo;
	}
	
	public void writeAction(String title, String contents, UserVo authUser) {
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(contents);
		vo.setUserNo(authUser.getNo());
		boardRepository.insert(vo);
	}

	public void deleteAction(Long no, Long userNo) {
		boardRepository.delete(no, userNo);
	}
}
