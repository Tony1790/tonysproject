<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>답글 작성</title>
</head>
<style>
	.content {
		width : 300px;
		height : 300px;
	}
</style>
<body>
	<h2>답글 작성</h2>
	<form action="/tonysproject/reboard-create-process.do?b_group=${board.b_group}&b_depth=${board.b_depth}&b_order=${board.b_order}" name="board" method="POST">
		<input type="hidden" name="u_idx" value="${user.u_idx}">	
		<input type="hidden" name="b_group" value="${board.b_group}">	
		<input type="hidden" name="b_idx" value="${board.b_idx}">	
		<input type="hidden" name="b_order" value="${board.b_order}">	
		<input type="hidden" name="b_depth" value="${board.b_depth}">	
		
		<p class="title"> 제목 : <input type="text" name="title" maxlength="20"></p>
		<textarea class="content" name="content" placeholder="내용 : "></textarea>
		
		<p><input type="submit" value="작성"/></p>
	</form>
</body>
</html>