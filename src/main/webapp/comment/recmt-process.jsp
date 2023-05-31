<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<ul>
		<c:forEach items="${commentList2}" var="cmt" varStatus="status">
				<li>
					<div>
						<span>${cmt.c_writer}</span>
					</div>
					<div>
						<span>${cmt.c_content}</span>
					</div>
					<div>
						<span>${cmt.c_date}</span>
					</div>
					<button class="recmt-show-btn btnReCommentForm">답글</button>
					<button class="recmt-edit-btn btnReCommentEdit">수정</button>
					<li class="recmt-create-form" style="display: none">
						<textarea rows="2" cols="80"></textarea>
						<button type="button" class="recmt_submit_btn" 
							c_group="${cmt.c_group}"
							c_order="${cmt.c_order}"
							c_depth="${cmt.c_depth}"
							b_idx = "${board.b_idx}"
							>등록</button>
						<button type="button" class="recmt_cancel_btn">취소</button>
					</li>
					<li class="recmt-edit-form" style="display: none">
						<textarea rows="3" cols="80">${cmt.c_content}</textarea>
						<button type="button" class="recmt_edit_submit_btn" 
							c_group="${cmt.c_group}"
							c_order="${cmt.c_order}"
							c_depth="${cmt.c_depth}"
							b_idx = "${cmt.b_idx}"
							c_writer = "${cmt.c_writer}"
							c_idx = "${cmt.c_idx}"
							>등록</button>								
						<button type="button" class="recmt_cancel_btn">취소</button>
					</li>
				</li>
				
		</c:forEach>
	</ul>