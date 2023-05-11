<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 삭제</title>
</head>
<body>
	<h1>삭제 성공</h1>
	<a href="board-list.do">돌아가기</a>
</body>
<script>
setTimeout(function () {
	window.location.href = "/tonysproject/board-list.do";
}, 1000);
</script>
</html>