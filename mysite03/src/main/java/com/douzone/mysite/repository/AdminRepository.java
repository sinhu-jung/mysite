package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.SiteVo;

@Repository
public class AdminRepository {

	public boolean update(SiteVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "update site set welcome=?, description=? ";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, vo.getWelcomeMessage());
			pstmt.setString(2, vo.getDescription());

			int count = pstmt.executeUpdate();
			result = count == 1;

		} catch (SQLException e) {
			System.out.println("error :" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				System.out.println("connection close error:" + e);
			}
		}
		return result;
	}

	public SiteVo findAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SiteVo vo = null;

		try {
			conn = getConnection();

			String sql = "select title, welcome, profile, description from site";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String title = rs.getString(1);
				String welcomeMessage = rs.getString(2);
				String profileURL = rs.getString(3);
				String description = rs.getString(4);
				
				vo = new SiteVo();
				vo.setTitle(title);
				vo.setWelcomeMessage(welcomeMessage);
				vo.setprofileURL(profileURL);
				vo.setDescription(description);
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

		return vo;
	}
	
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://192.168.142.101:3307/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		}

		return conn;
	}
	
}
