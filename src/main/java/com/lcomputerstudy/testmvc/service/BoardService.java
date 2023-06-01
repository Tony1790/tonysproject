package com.lcomputerstudy.testmvc.service;

import java.util.ArrayList;

import com.lcomputerstudy.testmvc.dao.BoardDAO;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Search;

public class BoardService {
	private static BoardService service = null;
	private static BoardDAO dao = null;
	
	private BoardService() {
		
	}
	
	public static BoardService getInstance() {
		if(service == null) {
			service = new BoardService();
			dao = BoardDAO.getInstance();
		}
		return service;
	}
	
	public void createBoard(Board board) {
		dao.createBoard(board);
	}
	
	public ArrayList<Board> getBoards(Search search) {
		return dao.getBoards(search);
	}
	
	public ArrayList<Board> getBoards() {
		return dao.getBoards();
	}
	
	public Board getBoard(Board board) {
		return dao.getBoard(board);
	}
	
	public void editBoard(Board board) {
		dao.editBoard(board);
	}
	
	public void deleteBoard(Board board) {
		dao.deleteBoard(board);
	}
	
	public void createReboard(Board board) {
		dao.createReboard(board);
	}
}
