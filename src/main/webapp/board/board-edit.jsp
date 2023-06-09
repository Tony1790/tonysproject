<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 수정</title>
</head>

<style>
	.content {
		width:300px;
		height : 150px;
	}
</style>

<body>
	<h1>게시글 수정</h1>
	<form action="/tonysproject/board-edit-process.do" name="board" method=post enctype="multipart/form-data">
		<input type="hidden" name="b_idx" value="${board.b_idx}">
		<p> 제목 : <input type="text" name="edit_title" value="${board.b_title}"></p>
		<textarea class="content" name="edit_content" placeholder="${board.b_content}">${board.b_content}</textarea>
		<p>
			<span>
				<img src="${pageContext.request.contextPath}${board.b_img}" width="300"></img>
			</span>
		</p>
		<input type="file" name="file1"/>
		<p> <input type = "submit" value="수정 완료"></p>
	</form>
</body>
</html>