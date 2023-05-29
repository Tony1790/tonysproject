package com.lcomputerstudy.testmvc.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

import com.lcomputerstudy.testmvc.service.BoardService;
import com.lcomputerstudy.testmvc.service.UserService;
import com.lcomputerstudy.testmvc.service.CommentService;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.User;
import com.lcomputerstudy.testmvc.vo.Comment;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		
		int count = 0;
		int page = 1;
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String requestURI = request.getRequestURI(); 
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		String idx = null;
		String pw = null;
		
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
		
		Pagination pagination = null;
		HttpSession session = null;
		command = checkSession(request, response, command);
		
		UserService userService = null;
		User user = null;
		BoardService boardService = null;
		Board board = null;
		CommentService commentService = null;
		Comment comment = null;
		
		switch (command) {
			case "/user-list.do":
				String reqPage = request.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
				}
				userService = UserService.getInstance();
				count = userService.getUsersCount();
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(count);
				pagination.init();
				ArrayList<User> list = userService.getUsers(pagination);
				view = "user/list";
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				break;
			case "/user-insert.do":
				view = "user/insert";
				break;
			case "/user-insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(request.getParameter("age"));
				userService = UserService.getInstance();
				userService.insertUser(user);
				view = "user/insert-result";
				break;
			case "/user-detail.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.getUser(user);
				view = "user/detail";
				request.setAttribute("user", user);
				break;
			case "/user-edit.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				user = userService.getUser(user);
				view = "user/edit";
				request.setAttribute("user", user);
				break;
			case "/user-edit-process.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				user.setU_id(request.getParameter("edit_id"));
				user.setU_pw(request.getParameter("edit_password"));
				user.setU_name(request.getParameter("edit_name"));
				user.setU_tel(request.getParameter("edit_tel1") + "-" + request.getParameter("edit_tel2") + "-" + request.getParameter("edit_tel3"));
				user.setU_age(request.getParameter("edit_age"));
				userService = UserService.getInstance();
				userService.editUser(user);
				view = "user/edit-result";
				break;
			case "/user-delete.do":
				user = new User();
				user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				userService = UserService.getInstance();
				userService.deleteUser(user);
				view = "user/delete";
				break;
			
			
			
			case "/board-list.do":
				boardService = BoardService.getInstance();
				ArrayList<Board> boardList = boardService.getBoards(); 
				view = "board/board-list";
				request.setAttribute("boardList", boardList);
				break;
			case "/board-detail.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				board = boardService.getBoard(board);
				
				int bIdx = Integer.parseInt(request.getParameter("b_idx"));
				commentService = CommentService.getInstance();
				ArrayList<Comment> commentList = commentService.getComments(bIdx);
				
				view = "board/board-detail";
				request.setAttribute("board", board);
				request.setAttribute("commentList", commentList);
				break;
			case "/board-delete.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				boardService.deleteBoard(board);
				view = "board/board-delete";
				break;
			case "/board-edit.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				board = boardService.getBoard(board);
				view = "board/board-edit";
				request.setAttribute("board", board);
				break;
			case "/board-edit-process.do":
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				board.setB_title(request.getParameter("edit_title"));
				board.setB_content(request.getParameter("edit_content"));
				
				boardService = BoardService.getInstance();
				boardService.editBoard(board);
				view = "/board/board-edit-result";
				break;
			case "/board-create.do":
				view = "board/board-create";
				break;
			case "/board-create-process.do":
				board = new Board();
				session = request.getSession();
				user = (User)session.getAttribute("user");
				//board.setU_idx(Integer.parseInt((String) session.getAttribute(idx)));
				//board.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
				board.setU_idx(user.getU_idx());
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				board.setB_date(currentDateTime);
				boardService = BoardService.getInstance();
				boardService.createBoard(board);
				
				view = "board/board-create-result";
				break;
				
				
				
			
			
			case "/reboard-create.do":
				board = new Board();
				board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				board.setB_order(Integer.parseInt(request.getParameter("b_order")));
				board.setB_depth(Integer.parseInt(request.getParameter("b_depth")));
				view = "board/reboard-create";
				request.setAttribute("board", board);
				break;
			case "/reboard-create-process.do":
				board = new Board();
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board.setU_idx(user.getU_idx());
				board.setB_title(request.getParameter("title"));
				board.setB_content(request.getParameter("content"));
				board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				board.setB_order(Integer.parseInt(request.getParameter("b_order")));
				board.setB_depth(Integer.parseInt(request.getParameter("b_depth")));
				board.setB_date(currentDateTime);
				boardService = BoardService.getInstance();
				boardService.createReboard(board);
				view = "board/reboard-create-result";
				
				
				
				
			case "/comment-create-process.do":
				comment = new Comment();
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				session = request.getSession();
				user = (User)session.getAttribute("user");
				comment.setC_writer(user.getU_id());
				comment.setC_content(request.getParameter("origin-cmt-text"));
				comment.setC_date(currentDateTime);
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				commentService = CommentService.getInstance();
				commentService.createComment(comment);
				//request.setAttribute("board", board);
				//response.sendRedirect(request.getContextPath() + "/board-detail.do");
				//redirect 하는 법?
				view = "board/board-detail";
				break;
				
				
			case "/recomment-create-process.do":
				comment = new Comment();
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				session = request.getSession();
				user = (User)session.getAttribute("user");
				comment.setC_writer(user.getU_id());
				comment.setC_content(request.getParameter("recmt-text"));
				comment.setC_date(currentDateTime);
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				comment.setC_group(Integer.parseInt(request.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(request.getParameter("c_order")));
				comment.setC_depth(Integer.parseInt(request.getParameter("c_depth")));
				commentService = CommentService.getInstance();
				commentService.createRecomment(comment);
				view = "board/board-detail";
				break;
				
				
				
			case "/user-login.do":
				view = "user/login";
				break;
			case "/user-login-process.do":
				idx = request.getParameter("login_id");
				pw = request.getParameter("login_password");
				
				userService = UserService.getInstance();
				user = userService.loginUser(idx, pw);
				
				if(user != null) {
					session = request.getSession();
//					session.setAttribute("u_idx", user.getU_idx());
//					session.setAttribute("u_id", user.getU_id());
//					session.setAttribute("u_pw", user.getU_pw());
//					session.setAttribute("u_name", user.getU_name());
					session.setAttribute("user", user);
					view = "user/login-result";
					
				} else {
					view = "user/login-fail";
				}
				break;
			case "/logout.do":
				session = request.getSession();
				session.invalidate();
				view = "user/login";
				break;
			case "/access-denied.do":
				view = "user/access-denied";
				break;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
		rd.forward(request, response);
	}
	
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/user-list.do",
				"/user-detail.do",
				"/user-edit.do",
				"/user-edit-process.do",
				"/user-logout.do",
				"/board-create.do",
				"/board-create-process.do",
				"/reboard-create.do",
				"/reboard-create-process.do",
				"/comment-create-process.do",
				"/recomment-create-process.do"
		};
		
		for (String item : authList) {
			if(item.equals(command)) {
				if(session.getAttribute("user") == null) {
					command = "/access-denied.do";
				}
			}
		}
		return command;
	}
}















