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
			
			String query = "insert into comment (c_content, c_writer, c_date, b_idx) values (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, comment.getC_content());
			pstmt.setString(2, comment.getC_writer());
			pstmt.setTimestamp(3, comment.getC_date());
			pstmt.setInt(4, comment.getB_idx());
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
					.toString();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			commentList = new ArrayList<Comment>();
			
			while(rs.next()) {
				Comment comment = new Comment();
				comment.setC_idx(rs.getInt("c_idx"));
				comment.setB_idx(bIdx);
				comment.setC_content(rs.getString("c_content"));
				comment.setC_writer(rs.getString("c_writer"));
				comment.setC_date(rs.getTimestamp("c_date"));
			}
			
		} catch(Exception e) {
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
		return commentList;
	}

}
















