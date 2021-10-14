package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	public List<BoardVo> limitedFind(Long pageNum) {
		List<BoardVo> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql =
				"select b.no, b.user_no, a.name, b.title, b.contents, b.hit, b.reg_date, b.group_no, b.order_no, b.depth"
				+ "	from board b, user a"
				+ "    where b.user_no = a.no"
				+ "	order by group_no desc, order_no asc	"
				+ "limit 0, 10";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				Long user_no = rs.getLong(2);
				String user_name = rs.getString(3);
				String title = rs.getString(4);
				String contents = rs.getString(5);
				Long hit = rs.getLong(6);
				String reg_date = rs.getString(7);
				Long group_no = rs.getLong(8);
				Long order_no = rs.getLong(9);
				Long depth = rs.getLong(10);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setUser_no(user_no);
				vo.setUser_name(user_name);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setHit(hit);
				vo.setReg_date(reg_date);
				vo.setGroup_no(group_no);
				vo.setOrder_no(order_no);
				vo.setDepth(depth);
				
				list.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	public Long maxGroupNoFind() {
		Long maxNum = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql =
				"select max(group_no)"
				+ "	from board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			rs.next();

			maxNum = rs.getLong(1);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return maxNum;
	}
	
	public BoardVo getTitleContent(Long contentNo) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		try {
			conn = getConnection();
			
			String sql =
				"select user_no, title, contents, group_no, order_no, depth"
				+ "	from board" +
				"	where no=? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, contentNo);
			
			rs = pstmt.executeQuery();
			rs.next();
			
			Long user_no = rs.getLong(1);
			String title = rs.getString(2);
			String contents = rs.getString(3);
			Long group_no = rs.getLong(4);
			Long order_no = rs.getLong(5);
			Long depth = rs.getLong(6);
			
			vo.setNo(contentNo);
			vo.setUser_no(user_no);
			vo.setTitle(title);
			vo.setContents(contents);
			vo.setGroup_no(group_no);
			vo.setOrder_no(order_no);
			vo.setDepth(depth);
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	
	public boolean delete(Long contentNo, String password) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" delete b from board b "+
					"join user a on b.user_no=a.no "+
						"where b.no=? and a.password=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, contentNo);
			pstmt.setString(2, password);
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;		
	}
	
	public boolean insert(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" insert into board "+
					"values (null, ?, ?, ?, ?, now(), ?, ?, ?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContents());
			pstmt.setLong(4, vo.getHit());
			pstmt.setLong(5, vo.getGroup_no());
			pstmt.setLong(6, vo.getOrder_no());
			pstmt.setLong(7, vo.getDepth());
			
			int count = pstmt.executeUpdate();
			result = count == 1;
			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	public boolean update(BoardVo vo) {
		boolean result = false;

		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			
			String sql =
					" update " + 
					"board set" + 
					" title=?, contents=?, reg_date=now()"+
					" where no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getNo());
			
			int count = pstmt.executeUpdate();
			result = count == 1;			
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		
		return result;
	}
	
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패:" + e);
		} 
		
		return conn;
	}
}
