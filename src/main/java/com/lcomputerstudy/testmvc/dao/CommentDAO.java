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
import com.lcomputerstudy.testmvc.vo.Comment;
import java.time.LocalDateTime;

public class CommentDAO {
	private static CommentDAO dao = null;
	
	private CommentDAO() {
		
	}
	
	public static CommentDAO getInstance() {
		if(dao == null) {
			dao = new CommentDAO();
		}
		return dao;
	}
	
	public void createComment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String query = "insert into comment (c_content, c_writer, c_date, b_idx, c_group, c_order, c_depth) values (?, ?, ?, ?, 0, 1, 0)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment.getC_content());
			pstmt.setString(2, comment.getC_writer());
			pstmt.setTimestamp(3, comment.getC_date());
			pstmt.setInt(4, comment.getB_idx());
			pstmt.executeUpdate();
			pstmt.close();
			
			query = "update comment set c_group = last_insert_id() where c_idx = last_insert_id()";
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ArrayList<Comment> getComments(int bIdx) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<Comment> commentList = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String query = new StringBuilder()
					.append("select comment.*\n")
					.append("from comment\n")
					.append("where b_idx = ?\n")
					.append("order by c_group asc, c_order asc")
					.toString();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, bIdx);
			rs = pstmt.executeQuery();
			commentList = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setB_idx(rs.getInt("b_idx"));
				comment.setC_content(rs.getString("c_content"));
				comment.setC_writer(rs.getString("c_writer"));
				comment.setC_date(rs.getTimestamp("c_date"));
				comment.setC_group(rs.getInt("c_group"));
				comment.setC_order(rs.getInt("c_order"));
				comment.setC_depth(rs.getInt("c_depth"));
				commentList.add(comment);
			}
			rs.close();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return commentList;
	}
	
	
	public void createRecomment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		comment.setC_order(comment.getC_order() + 1);
		comment.setC_depth(comment.getC_depth() + 1);
		
		try {
			conn = DBConnection.getConnection();
			
			String query = "insert into comment (c_content, c_writer, c_date, b_idx, c_group, c_order, c_depth) " 
					+ "values (?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "　└" + comment.getC_content());
			pstmt.setString(2, comment.getC_writer());
			pstmt.setTimestamp(3, comment.getC_date());
			pstmt.setInt(4, comment.getB_idx());
			pstmt.setInt(5, comment.getC_group());
			pstmt.setInt(6, comment.getC_order());
			pstmt.setInt(7, comment.getC_depth());
			pstmt.executeUpdate();
			pstmt.close();
			
			query = "update comment "
					+ "set c_order = c_order + 1 "
					+ "where c_order >= " + comment.getC_order() + " and c_idx != last_insert_id() and c_group = " + comment.getC_group();
			pstmt = conn.prepareStatement(query);
			pstmt.executeUpdate();
			
			
		} catch ( Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null)
					pstmt.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void editcomment(Comment comment) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBConnection.getConnection();
			
			String sql = "update comment set c_content=?, c_date=? where c_idx=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, comment.getC_content());
			pstmt.setTimestamp(2, comment.getC_date());
			pstmt.setInt(3, comment.getC_idx());
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
}
















