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
import com.lcomputerstudy.testmvc.vo.Search;
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
	
	public void createReboard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		board.setB_order(board.getB_order()+1);
		board.setB_depth(board.getB_depth()+1);
		
		
		try {
			conn = DBConnection.getConnection();
			
			String query = "insert into board (b_title, b_content, b_writer, b_date, u_idx, b_view, b_group, b_order, b_depth) "
					+ "values (?,?,(select u_id from user where u_idx=?),?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, " └" + board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setTimestamp(4, board.getB_date());
			pstmt.setInt(5, board.getU_idx());
			pstmt.setInt(6, 0);
			pstmt.setInt(7, board.getB_group());
			pstmt.setInt(8, board.getB_order());
			pstmt.setInt(9, board.getB_depth());
			pstmt.executeUpdate();
			pstmt.close();
			
			query = "update board "
					+ "set b_order = b_order + 1 "
					+ "where b_order >= " + board.getB_order() + " and b_idx != last_insert_id() and b_group = " + board.getB_group();
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
		}catch ( Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createBoard(Board board) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		Statement stmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String query2 = "insert into board (b_title, b_content, b_writer, b_date, u_idx, b_view, b_group, b_order, b_depth) values (?,?,(select u_id from user where u_idx=?),?,?,?,0,1,0)";
			pstmt = conn.prepareStatement(query2);
			pstmt.setString(1, board.getB_title());
			pstmt.setString(2, board.getB_content());
			pstmt.setInt(3, board.getU_idx());
			pstmt.setTimestamp(4, board.getB_date());
			pstmt.setInt(5, board.getU_idx());
			pstmt.setInt(6, 0);
			pstmt.executeUpdate();
			pstmt.close();
			
			query2 = "update board set b_group = last_insert_id() where b_idx = last_insert_id()";
			pstmt = conn.prepareStatement(query2);
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
					.append("order by b_group desc, b_order asc")
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
	
	public ArrayList<Board> getBoards(Search search) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Board> boardList = null;
		
		String where = "";
		
		if(search.getCategory() == null)
			search.setCategory("");
		
		switch(search.getCategory()) {
		case "제목":
			where = "where b_title LIKE ?\n";
			break;
		case "내용":
			where = "where b_content LIKE ?\n";
			break;
		case "제목+내용":
			where = "where b_title LIKE ? or b_content LIKE ?\n";
			break;
		case "작성자":
			where = "where b_writer LIKE ?\n";
			break;
		default:
			break;
		}
		
		
		
		try {
			conn = DBConnection.getConnection();
			
			String query = new StringBuilder()
					.append("select board.*\n")
					.append("from board\n")
					.append(where)
					.append("order by b_group desc, b_order asc")
					.toString();
			pstmt = conn.prepareStatement(query);
			switch(search.getCategory()) {
			case "":
				break;
			case "제목+내용":
				pstmt.setString(1, "%" + search.getKeyword() + "%");
				pstmt.setString(2, "%" + search.getKeyword() + "%");
				break;
			default:
				pstmt.setString(1, "%" + search.getKeyword() + "%");
				break;
			}
			
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
				board.setB_group(rs.getInt("b_group"));
				board.setB_order(rs.getInt("b_order"));
				board.setB_depth(rs.getInt("b_depth"));
				
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

















