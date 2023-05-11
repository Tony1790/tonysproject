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
	table {
		border-collapse : collapse;
	}
	table tr td {
		border: 1px solid #818181;
		width : 200px;
		text-align: left;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
		border: none;
		cursor : pointer;
		padding:10px;
		display:inline-block;
		
	}
</style>
<body>
	<h1>게시글 상세페이지</h1>
	<table>
		<tr>
			<td>제목 : ${board.b_title}</td>
			<td>작성일자 : ${board.b_date}</td>
		</tr>
		<tr>
			<td>작성자 : ${board.b_writer}</td>
			<td>조회수 : ${board.b_view}</td>
			<!-- 조회수 채워야함 -->
			<td>댓글수 :</td>
			<!-- 댓글 수도 채워야함 -->
		</tr>


		<tr>

			<td>내용 : ${board.b_content}</td>

		</tr>

		<tr style="height:50px;">
			<td style="border:none;">
				<a href="/tonysproject/board-edit.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;" >수정</a>
			</td>
			<td style="border:none;">
				<a href="/tonysproject/board-delete.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
			</td>
		</tr>
	</table>
</body>
</html>