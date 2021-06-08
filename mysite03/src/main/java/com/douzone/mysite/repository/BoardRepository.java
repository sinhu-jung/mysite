package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		int count = sqlSession.update("board.update", vo);
		return count == 1;
	}
	
	public boolean insertComment(BoardVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();
			String sql = null;
			sql = "insert into board values(null, ?, ?, now(), 0, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getGroupNo());
			pstmt.setInt(4, vo.getOrderNo());
			pstmt.setInt(5, vo.getDepth());
			pstmt.setLong(6, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				// 자원 정리(clean-up)
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}

	public int findcount(String keyword) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		try {
			conn = getConnection();
			
			if(keyword.equals(null)) {
				sql = "select count(*) from board";
				pstmt = conn.prepareStatement(sql);
			}else {
				sql = 
					  " select count(*)" +
					  "	from board b join user a on a.no = b.user_no" +
					  " where b.title like ? or b.contents like ? or a.name like ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, "%"+keyword+"%");
				pstmt.setString(2, "%"+keyword+"%");
				pstmt.setString(3, "%"+keyword+"%");
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				// 자원 정리(clean-up)
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
	
	public Boolean updatComment(int no, int orderNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();
			String sql = null;
			if(orderNo > 1) {
				sql = "update board set order_no=order_no+1 where group_no = ? and order_no>?";
			} else {
				sql = "update board set order_no=order_no+1 where group_no = ? and order_no>=1";
			}
			pstmt = conn.prepareStatement(sql);

			pstmt.setInt(1, no);
			if(orderNo > 1) {
				pstmt.setInt(2, orderNo);
			}

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (Exception e2) {
				// TODO: handle exception
			}
		}

		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.80.103:3307/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}

	public List<BoardVo> findAllByPageAndKeword(String kwd, int page, int listSize) {
		
		List<BoardVo> result = new ArrayList<>();

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;

		try {
			conn = getConnection();
			if(kwd.equals(null)) {
				sql = "select a.no, a.title, a.depth, a.hit, b.no as userNo, b.name, a.reg_date " +
						 " from board a, user b " +
						 " where a.user_no = b.no " +
						 " order by a.group_no DESC, a.order_no ASC limit ?,?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, (page-1)*listSize);
				pstmt.setInt(2, listSize);
			} else {
				 sql = 
					   " select b.no, b.title, b.depth, b.hit, b.user_no, a.name, b.reg_date" +
					   "  from board b join user a on a.no = b.user_no" +
					   " where b.title like ? or b.contents like ? or a.name like ?" +
					   "  order by b.group_no DESC, b.order_no ASC limit ?,?;";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, "%"+kwd+"%");
				pstmt.setString(2, "%"+kwd+"%");
				pstmt.setString(3, "%"+kwd+"%");
				pstmt.setInt(4, (page-1)*listSize);
				pstmt.setInt(5, listSize);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int depth = rs.getInt(3);
				int hit = rs.getInt(4);
				Long userNo = rs.getLong(5);
				String name = rs.getString(6);
				String regDate = rs.getString(7);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setDepth(depth);
				vo.setHit(hit);
				vo.setUserNo(userNo);
				vo.setUserName(name);
				vo.setRegDate(regDate);
				
				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				// 자원 정리(clean-up)
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
