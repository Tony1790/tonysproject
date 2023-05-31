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
								<!-- form action="/tonysproject/recomment-create-process.do" method="POST">
									<div class="recmt-editor" style="display: none;">
										<textarea rows="3" cols="50" name="recmt-text"></textarea>
										<input type="hidden" name="c_group" value="${cmt.c_group}"></input>
										<input type="hidden" name="c_order" value="${cmt.c_order}"></input>
										<input type="hidden" name="c_depth" value="${cmt.c_depth}"></input>
										<input type="hidden" name="b_idx" value="${board.b_idx}" ></input>
										<button type=submit class="recmt-submit-btn">작성</button>
									</div>
								</form -->
								<li style="display: none">
									<textarea rows="2" cols="80"></textarea>
									<button type="button" class="recmt_submit_btn" 
										c_group="${cmt.c_group}"
										c_order="${cmt.c_order}"
										c_depth="${cmt.c_depth}"
										b_idx = "${board.b_idx}"
										>등록</button>
									<button type="button" class="recmt_cancel_btn">취소</button>
								</li>
							</li>
							
					</c:forEach>
				</ul>