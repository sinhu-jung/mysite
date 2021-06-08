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
	
	public Map<String, Object> viewPage(int page, String kwd) {
		int listSize = 5;
		int pageSize = 5;
		int totalCount = new BoardRepository().findcount(kwd);
		int pageCount = (int)Math.ceil( (double)totalCount / listSize );
		int blockCount = (int)Math.ceil( (double)pageCount / pageSize );
		int currentBlock = (int)Math.ceil( (double)page / pageSize );
		
		if( page > pageCount ) {
			page = pageCount;
			currentBlock = (int)Math.ceil( (double)page / pageSize );
		}		
		
		if( page < 1 ) {
			page = 1;
			currentBlock = 1;
		}
		
		int beginPage = currentBlock == 0 ? 1 : (currentBlock - 1) * pageSize + 1;
		int prevPage = ( currentBlock > 1 ) ? ( currentBlock - 1 ) * pageSize : 0;
		int nextPage = ( currentBlock < blockCount ) ? currentBlock * pageSize + 1 : 0;
		int endPage = ( nextPage > 0 ) ? ( beginPage - 1 ) + listSize : pageCount;
		
		List<BoardVo> list = boardRepository.findAllByPageAndKeword( kwd, page, listSize );
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put( "list", list );
		map.put( "totalCount", totalCount );
		map.put( "listSize", listSize );
		map.put( "currentPage", page );
		map.put( "beginPage", beginPage );
		map.put( "endPage", endPage );
		map.put( "prevPage", prevPage );
		map.put( "nextPage", nextPage );
		map.put( "keyword", kwd );
		
		return map;
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

	public void commentAction(String title, String content, Long no, UserVo authUser) {
		BoardVo vo1 = new BoardRepository().findById(no);
		int groupNo = vo1.getGroupNo();
		int depth = vo1.getDepth();
		int orderNo = vo1.getOrderNo();
		
		BoardVo vo = new BoardVo();
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserNo(authUser.getNo());
		vo.setGroupNo(groupNo);
		
		if (vo1.getDepth() == 0) {
			vo.setOrderNo(1);
		} else if (vo1.getDepth() >= 1) {
			vo.setOrderNo(orderNo + 1);
		}
		
		vo.setDepth(depth + 1);
		boardRepository.updatComment(groupNo, orderNo);
		boardRepository.insertComment(vo);
	}

	public void modify(BoardVo vo) {
		boardRepository.modify(vo);
	}
}
