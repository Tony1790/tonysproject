<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<style>
	.content {
		width : 600px;
		height : 400px;
	}
</style>
<body>
	<h2>게시글 작성</h2>
	<form action="/tonysproject/board-create-process.do" name="board" method="POST" enctype="multipart/form-data">
		<p class="title"> 제목 : <input type="text" name="title" maxlength="20"></p>
		<textarea class="content" name="content" placeholder="내용 : "></textarea>
		
    	<p><input type="file" name="file1" /></p>
		
		<p><input type="submit" value="작성"/></p>
	</form>
</body>
</html>