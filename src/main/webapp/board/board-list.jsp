<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
</head>
<Style>
	body {
		padding: 10px 50px;
	}
	table {
		border-collapse: collapse;
		margin : 40px auto;
	}
	table tr th{
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	h2 {
		text-align: center;
	}
	
</Style>
<body>
	<h2>게시글 목록</h2>
	<a href = "/tonysproject/user-list.do">유저 목록 가기</a>
	<a href="/tonysproject/board-create.do">게시글 작성하기</a>
	<table>
		<tr>
			<!-- 여기에 전체 게시글 숫자 들어가야함 -->
			<th class = "total_count_board">전체 게시글 수</th>
		</tr>
		<tr>
			<th>No</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일자</th>
			<th>조회수</th>
		</tr>
		<!-- css로 디스플레이 flex로 바꾸고 할튼 정렬해야함 -->
		<c:forEach items="${boardList}" var="item" varStatus="status">
			<tr>
				<td><a href="/tonysproject/board-detail.do?b_idx=${item.b_idx}">${item.b_idx}</a></td>
				<td>${item.b_title}</td>
				<td>${item.b_content}</td>
				<td>${item.b_date}</td>
				<td>${item.b_view}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>