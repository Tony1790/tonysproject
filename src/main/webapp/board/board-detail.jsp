<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board.b_title}</title>
</head>
<style>
/* 전체 페이지 스타일링 */
body {
  margin: 0;
  padding: 0;
  font-family: Arial, sans-serif;
  background-color: #f5f5f5;
}

/* 게시글 상세페이지 스타일링 */
h1 {
  text-align: center;
  margin-top: 50px;
  margin-bottom: 30px;
  font-size: 36px;
}

table {
  margin: 0 auto;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
}

table tr td {
  border: 1px solid #ccc;
  padding: 15px;
}

table tr td:first-child {
  font-weight: 700;
  width: 120px;
  background-color: #f5f5f5;
}

table tr td:last-child {
  text-align: right;
}

a {
  display: inline-block;
  text-decoration: none;
  color: #fff;
  font-weight: 700;
  padding: 10px 15px;
  margin-top: 20px;
  border-radius: 5px;
  cursor: pointer;
}

a.edit-btn {
  background-color: #333;
}

a.delete-btn {
  background-color: #d9534f;
}

a.edit-btn:hover, a.delete-btn:hover {
  opacity: 0.8;
}

/* 게시글 상세페이지 콘텐츠 스타일링 */
.container {
  max-width: 1200px;
  margin: 0 auto;
}

.content {
  padding: 20px;
}

.title {
  font-size: 24px;
  font-weight: 700;
  margin-bottom: 10px;
}

.author {
  font-size: 16px;
  color: #999;
}

.date {
  font-size: 14px;
  color: #999;
}

.content-text {
  font-size: 16px;
  line-height: 1.5;
}

.btn-container {
  text-align: right;
}
</style>
<body>
	<h1>게시글 상세페이지</h1>
	<table class="container">
		<tr>
			<td class="title">제목 : ${board.b_title}</td>
			<td class="view">작성일자 : ${board.b_date}</td>
		</tr>
		<tr>
			<td class="author">작성자 : ${board.b_writer}</td>
			<td>조회수 : ${board.b_view}</td>
			<!-- 조회수 채워야함 -->
			<td>댓글수 :</td>
			<!-- 댓글 수도 채워야함 -->
		</tr>


		<tr>
			<td class="content">내용 : ${board.b_content}</td>
		</tr>

		<tr style="height:50px;">
			<td style="border:none;">
				<a class="edit-btn" href="/tonysproject/board-edit.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;" >수정</a>
			</td>
			<td style="border:none;">
				<a class="delete-btn" href="/tonysproject/board-delete.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
			</td>
		</tr>
	</table>
</body>
</html>