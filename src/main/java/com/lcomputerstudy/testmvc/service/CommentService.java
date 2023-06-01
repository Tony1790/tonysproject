package com.lcomputerstudy.testmvc.service;

import java.util.ArrayList;

import com.lcomputerstudy.testmvc.dao.CommentDAO;
import com.lcomputerstudy.testmvc.vo.Comment;

public class CommentService {
	private static CommentService service = null;
	private static CommentDAO dao = null;
	
	private CommentService() {
		
	}
	
	public static CommentService getInstance() {
		if(service == null) {
			service = new CommentService();
			dao = CommentDAO.getInstance();
		}
		return service;
	}
	
	public void createComment(Comment comment) {
		dao.createComment(comment);
	}
	
	public ArrayList<Comment> getComments(int bIdx) {
		return dao.getComments(bIdx);
	}
	
	public void createRecomment(Comment comment) {
		dao.createRecomment(comment);
	}
	
	public void editcomment(Comment comment) {
		dao.editcomment(comment);
	}
	
	public void deleteComment(Comment comment) {
		dao.deleteComment(comment);
	}
}
