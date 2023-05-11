package com.lcomputerstudy.testmvc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.lcomputerstudy.testmvc.database.DBConnection;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.User;
import java.time.LocalDateTime;

public class BoardDAO {
	private static BoardDAO dao = null;
	
	private BoardDAO() {
		
	}
	
	public static BoardDAO getInstance() {
		if(dao == null) {
			dao = new BoardDAO();
		}
		return dao;
	}
	
	public void createBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String query2 = "insert into board (b_title, b_content, b_writer, b_date, u_idx, b_view) values (?,?,(select u_id from user where u_idx=?),?,?,?)";
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setTimestamp(4, board.getB_date());
			pstmt.setInt(5, board.getU_idx());
			pstmt.setInt(6, 0);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if (stmt != null)
					stmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Board> getBoards() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> boardList = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String query = new StringBuilder()
					.append("select board.*\n")
					.append("from board\n")
					.toString();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			boardList = new ArrayList<Board>();
			
			while(rs.next()) {
				Board board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_date(rs.getTimestamp("b_date"));
				board.setB_writer(rs.getString("b_writer"));
				board.setU_idx(rs.getInt("u_idx"));
				board.setB_view(rs.getInt("b_view"));
				boardList.add(board);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return boardList;
	}
	
	public Board getBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Timestamp tst = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentTimeString = sdf.format(tst);
		System.out.println(currentTimeString);
		
		try {
			conn = DBConnection.getConnection();
			String query = "select * from board where b_idx=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, board.getB_idx());
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				board = new Board();
				board.setB_idx(rs.getInt("b_idx"));
				board.setB_title(rs.getString("b_title"));
				board.setB_content(rs.getString("b_content"));
				board.setB_writer(rs.getString("b_writer"));
				board.setB_date(rs.getTimestamp("b_date"));
				board.setB_view(rs.getInt("b_view") + 1); //조회수 1씩 증가
				
				//조회수 증가 DB에 업데이트
				String updateQuery = "update board set b_view = ? where b_idx=?";
				PreparedStatement updatePstmt = conn.prepareStatement(updateQuery);
				updatePstmt.setInt(1, board.getB_view());
				updatePstmt.setInt(2, board.getB_idx());
				updatePstmt.executeUpdate();
				updatePstmt.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return board;
	}
	
	public void editBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "update board set b_title=?, b_content=? where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getB_idx());
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) 
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void deleteBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			String sql = "delete from board where b_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_idx());
			pstmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null) 
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

















