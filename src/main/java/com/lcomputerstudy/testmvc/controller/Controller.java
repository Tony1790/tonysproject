package com.lcomputerstudy.testmvc.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lcomputerstudy.testmvc.service.BoardService;
import com.lcomputerstudy.testmvc.service.UserService;
import com.lcomputerstudy.testmvc.service.CommentService;
import com.lcomputerstudy.testmvc.vo.Board;
import com.lcomputerstudy.testmvc.vo.Pagination;
import com.lcomputerstudy.testmvc.vo.Search;
import com.lcomputerstudy.testmvc.vo.User;
import com.lcomputerstudy.testmvc.vo.Comment;

@WebServlet("*.do")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 파일이 업로드될 디렉토리를 지정합니다.
    private static final String UPLOAD_DIRECTORY = "C:\\Users\\L4A\\Documents\\work1\\tonysproject\\src\\main\\webapp\\img";
	
	protected void doGet(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
		
		int count = 0;
		int page = 1;
		Timestamp currentDateTime = new Timestamp(System.currentTimeMillis());
		
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		
		String requestURI = request.getRequestURI(); 
		String contextPath = request.getContextPath();
		String command = requestURI.substring(contextPath.length());
		String view = null;
		String idx = null;
		String pw = null;
		
		Pagination pagination = null;
		HttpSession session = null;
		command = checkSession(request, response, command);
		
		UserService userService = null;
		User user = null;
		BoardService boardService = null;
		Board board = null;
		CommentService commentService = null;
		Comment comment = null;
		Search search = null;
		boolean isRedirected = false;
		
		
		
		switch (command) {
			
			case "/user-insert.do":
				view = "user/insert";
				break;
			case "/user-insert-process.do":
				user = new User();
				user.setU_id(request.getParameter("id"));
				user.setU_pw(request.getParameter("password"));
				user.setU_name(request.getParameter("name"));
				user.setU_tel(request.getParameter("tel1") + "-" + request.getParameter("tel2") + "-" + request.getParameter("tel3"));
				user.setU_age(Integer.parseInt(request.getParameter("u_age")));
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
				user.setU_age(Integer.parseInt(request.getParameter("u_age")));
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
			
			case "/user-membership-change.do":
				user = new User();
				if(request.getParameter("u_idx") == null){
					user.setU_idx(1);
					user.setU_auth(0);
				} else {
					user.setU_idx(Integer.parseInt(request.getParameter("u_idx")));
					user.setU_auth(Integer.parseInt(request.getParameter("u_auth")));
				}
				userService = UserService.getInstance();
				userService.changeMembership(user);
				
				reqPage = request.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
				}
				count = userService.getUsersCount();
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(count);
				pagination.init();
				list = userService.getUsers(pagination);
				
				request.setAttribute("list", list);
				request.setAttribute("pagination", pagination);
				
				view = "user/user_list_membership_grade_table";
				break;
				
			case "/board-list.do":
				reqPage = request.getParameter("page");
				if(reqPage != null) {
					page = Integer.parseInt(reqPage);
				}
				
				search = new Search();
				search.setCategory(request.getParameter("search_option"));
				search.setKeyword(request.getParameter("keyword"));
				
				boardService = BoardService.getInstance();
				count = boardService.getBoardsCount(search);
				
				pagination = new Pagination();
				pagination.setPage(page);
				pagination.setCount(count);
				pagination.init();
				
				ArrayList<Board> boardList = boardService.getBoards(search, pagination);					
				view = "board/board-list";
				request.setAttribute("boardList", boardList);
				request.setAttribute("pagination", pagination);
				request.setAttribute("search", search);
				break;
				
			case "/board-detail.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				board = boardService.getBoard(board);
				
				int bIdx = Integer.parseInt(request.getParameter("b_idx"));
				commentService = CommentService.getInstance();
				ArrayList<Comment> commentList = commentService.getComments(bIdx);
				
				view = "board/board-detail";
				request.setAttribute("user", user);
				request.setAttribute("board", board);
				request.setAttribute("commentList", commentList);
				break;
			case "/board-delete.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				board = boardService.getBoard(board);
				//작성자가 아니면서 동시에 관리자도 아닐때
				if(!((board.getU_idx() == user.getU_idx()) || (user.getU_auth() == 0) )) {
					isRedirected = true;
					view = "user-list.do";
				} else {
					isRedirected = false;
					boardService.deleteBoard(board);
					view = "board/board-delete";
				}
				
				break;
			case "/board-edit.do":
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				boardService = BoardService.getInstance();
				board = boardService.getBoard(board);
				if(user.getU_idx() != board.getU_idx()) {
					isRedirected = true;
					view = "user-list.do";
				} else {
					isRedirected = false;
					view = "board/board-edit";
					request.setAttribute("board", board);
				}
				break;
			case "/board-edit-process.do":
				board = new Board();
				if(ServletFileUpload.isMultipartContent(request)) {
					try {
						// 요청을 파싱하여 파일 아이템의 리스트를 얻습니다.
						List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
						
						for(FileItem item : multiparts) {
							// 아이템이 폼 필드가 아니라면 (즉, 파일이라면)
							if(!item.isFormField()) {
								// 원본파일의 이름을 얻고,
								String originalFilename = item.getName(); 
								// 확장자 추출
								String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
								//랜덤한 이름과 확장자를 합침.
								String name = UUID.randomUUID().toString() + extension;
								//파일URL 생성.
								String fileUrl = UPLOAD_DIRECTORY + File.separator + name;
								// 해당 파일을 지정된 디렉토리에 저장합니다.
		                        item.write(new File(fileUrl));
		                        //board에 이미지파일의 이름과 폴더명을 저장합니다.
		                        String bImg = "/img" + "/" + name;
		                        board.setB_img(bImg);
							} else if (item.isFormField()) {
								//아이템이 폼필드라면
								String fieldName = item.getFieldName();
								String fieldValue = item.getString();
								
								if(fieldName.equals("edit_title")) {
									board.setB_title(fieldValue);
								} else if(fieldName.equals("edit_content")) {
									board.setB_content(fieldValue);
								} else if(fieldName.equals("b_idx")) {
									board.setB_idx(Integer.parseInt(fieldValue));
								}
							}
						}
						boardService = BoardService.getInstance();
						boardService.editBoard(board);
					} catch (Exception ex) {
		                ex.printStackTrace();
		            }
				} else {
					System.out.println("fail!");
				}
				
				view = "/board/board-edit-result";
				break;
			case "/board-create.do":
				view = "board/board-create";
				break;
			case "/board-create-process.do":
				board = new Board();
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board.setU_idx(user.getU_idx());
				// 요청이 멀티파트 컨텐츠인지 (즉, 파일을 포함하고 있는지) 확인
				if(ServletFileUpload.isMultipartContent(request)) {
					try {
						// 요청을 파싱하여 파일 아이템의 리스트를 얻습니다.
						List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
						
						for(FileItem item : multiparts) {
							// 아이템이 폼 필드가 아니라면 (즉, 파일이라면)
							if(!item.isFormField()) {
								// 원본파일의 이름을 얻고,
								String originalFilename = item.getName(); 
								// 확장자 추출
								String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
								//랜덤한 이름과 확장자를 합침.
								String name = UUID.randomUUID().toString() + extension;
								//파일URL 생성.
								String fileUrl = UPLOAD_DIRECTORY + File.separator + name;
								// 해당 파일을 지정된 디렉토리에 저장합니다.
		                        item.write(new File(fileUrl));
		                        //board에 이미지파일의 이름과 폴더명을 저장합니다.
		                        String bImg = "/img" + "/" + name;
		                        board.setB_img(bImg);
							} else if (item.isFormField()) {
								//아이템이 폼필드라면
								String fieldName = item.getFieldName();
								String fieldValue = item.getString();
								
								if(fieldName.equals("title")) {
									board.setB_title(fieldValue);
								} else if(fieldName.equals("content")) {
									board.setB_content(fieldValue);
								} 
							}
						}
						
						board.setB_date(currentDateTime);
						boardService = BoardService.getInstance();
						boardService.createBoard(board);
						
					} catch (Exception ex) {
		                ex.printStackTrace();
		            }
				} else {
					System.out.println("fail!");
					// 요청이 파일 업로드를 위한 것이 아니라면 에러 메시지를 설정합니다.
		            //request.setAttribute("message", "Sorry this Servlet only handles file upload request");
				}
				
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
				session = request.getSession();
				user = (User)session.getAttribute("user");
				board = new Board();
				board.setU_idx(user.getU_idx());
				// 요청이 멀티파트 컨텐츠인지 (즉, 파일을 포함하고 있는지) 확인
				if(ServletFileUpload.isMultipartContent(request)) {
					try {
						// 요청을 파싱하여 파일 아이템의 리스트를 얻습니다.
						List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
						
						for(FileItem item : multiparts) {
							// 아이템이 폼 필드가 아니라면 (즉, 파일이라면)
							if(!item.isFormField()) {
								// 원본파일의 이름을 얻고,
								String originalFilename = item.getName(); 
								// 확장자 추출
								String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
								//랜덤한 이름과 확장자를 합침.
								String name = UUID.randomUUID().toString() + extension;
								//파일URL 생성.
								String fileUrl = UPLOAD_DIRECTORY + File.separator + name;
								// 해당 파일을 지정된 디렉토리에 저장합니다.
		                        item.write(new File(fileUrl));
		                        //board에 이미지파일의 이름과 폴더명을 저장합니다.
		                        String bImg = "/img" + "/" + name;
		                        board.setB_img(bImg);
							} else if (item.isFormField()) {
								//아이템이 폼필드라면
								String fieldName = item.getFieldName();
								String fieldValue = item.getString();
								
								if(fieldName.equals("title")) {
									board.setB_title(fieldValue);
								} else if(fieldName.equals("content")) {
									board.setB_content(fieldValue);
								} else if(fieldName.equals("b_group")) {
									board.setB_group(Integer.parseInt(fieldValue));
								} else if(fieldName.equals("b_idx")) {
									board.setB_idx(Integer.parseInt(fieldValue));
								} else if(fieldName.equals("b_order")) {
									board.setB_order(Integer.parseInt(fieldValue));
								} else if(fieldName.equals("b_depth")) {
									board.setB_depth(Integer.parseInt(fieldValue));
								} 
							}
						}
						
						board.setB_date(currentDateTime);
						boardService = BoardService.getInstance();
						boardService.createReboard(board);
						
					} catch (Exception ex) {
		                ex.printStackTrace();
		            }
				} else {
					System.out.println("fail!");
				}
				view = "board/reboard-create-result";
				break;
				/*
				 * board.setB_title(request.getParameter("title"));
				 * board.setB_content(request.getParameter("content"));
				 * board.setB_group(Integer.parseInt(request.getParameter("b_group")));
				 * board.setB_order(Integer.parseInt(request.getParameter("b_order")));
				 * board.setB_depth(Integer.parseInt(request.getParameter("b_depth")));
				 * board.setB_date(currentDateTime); boardService = BoardService.getInstance();
				 * boardService.createReboard(board); view = "board/reboard-create-result";
				 * break;
				 */
				
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
				isRedirected = true;
				view = "board-detail.do?b_idx=" + board.getB_idx();
				//view = "board/board-detail";
				break;
				
							
			case "/recomment-create-cmt-list.do":
				comment = new Comment();
				board = new Board();
				board.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				session = request.getSession();
				user = (User)session.getAttribute("user");
				comment.setC_writer(user.getU_id());
				comment.setC_content(request.getParameter("c_content"));
				comment.setC_date(currentDateTime);
				comment.setB_idx(Integer.parseInt(request.getParameter("b_idx")));
				comment.setC_group(Integer.parseInt(request.getParameter("c_group")));
				comment.setC_order(Integer.parseInt(request.getParameter("c_order")));
				comment.setC_depth(Integer.parseInt(request.getParameter("c_depth")));
				commentService = CommentService.getInstance();
				commentService.createRecomment(comment);
				//대댓글 처리 과정
				
				ArrayList<Comment> commentList2 = commentService.getComments(Integer.parseInt(request.getParameter("b_idx")));
				request.setAttribute("commentList", commentList2);
				// 대댓글 처리 후 전체 댓글과 대댓글을 jsp파일로 보냄.
				view = "comment/recmt-process";
				break;
				
				
			case "/recomment-edit-cmt-list.do":
				//향후 작성자가 아닐 경우를 구별해야함
				comment = new Comment();
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				comment.setC_content(request.getParameter("c_content"));
				comment.setC_date(currentDateTime);
				commentService = CommentService.getInstance();
				commentService.editcomment(comment);
				
				ArrayList<Comment> commentList3 = commentService.getComments(Integer.parseInt(request.getParameter("b_idx")));
				request.setAttribute("commentList", commentList3);
				view = "comment/recmt-process";
				break;
				
			case "/recomment-delete-cmt.do":
				comment = new Comment();
				comment.setC_idx(Integer.parseInt(request.getParameter("c_idx")));
				commentService = CommentService.getInstance();
				commentService.deleteComment(comment);
				
				ArrayList<Comment> commentList4 = commentService.getComments(Integer.parseInt(request.getParameter("b_idx")));
				request.setAttribute("commentList", commentList4);
				view = "comment/recmt-process";
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
	
		if (isRedirected) {
			response.sendRedirect(view);
		} else {
			RequestDispatcher rd = request.getRequestDispatcher(view+".jsp");
			rd.forward(request, response);
		}
	}
	
	String checkSession(HttpServletRequest request, HttpServletResponse response, String command) {
		HttpSession session = request.getSession();
		
		String[] authList = {
				"/user-detail.do",
				"/user-edit.do",
				"/user-edit-process.do",
				"/user-logout.do",
				"/board-detail.do",
				"/board-delete.do",
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















