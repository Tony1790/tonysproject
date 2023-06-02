<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록2</title>
</head>
<style>
	body {
	padding : 10px 50px;
	}
	table {
		border-collapse:collapse;
		margin : 40px auto;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
	a {
		text-decoration:none;
		color:#000;
		font-weight:700;
	}
	h3 {
		color:red;
		text-align:left;
		margin : 5px;
	}
	h2 {
		text-align:center;
	}
	ul {
		width : 500px;
		height : 50px;
		margin: 10px auto;
	}
	li {
		list-style : none;
		width : 50px;
		line-height : 50px;
		border: 1px solid #ededed;
		float: left;
		text-align: center;
		margin: 0 5px;
		border-radius: 5px;
	}
</style>
<body>
	<h2>회원 목록</h2>
	<a class="newJoin" href="user-insert.do"><h3>회원 가입</h3></a>
	<a href="board-list.do">게시글 목록</a>
		<table>
			<tr>
				<td colspan="3">전체 회원 수 : ${pagination.count}</td>
			</tr>	
			<tr>
				<th>No</th>
				<th>ID</th>
				<th>이름</th>
			</tr>
			<c:forEach items="${list}" var="item" varStatus="status">
				<tr>
					<td><a href="/tonysproject/user-detail.do?u_idx=${item.u_idx}">${item.u_idx}</a></td>
					<td>${item.u_id}</td>
					<td>${item.u_name}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div>
			<ul>
				<c:choose>
					<c:when test="${pagination.prevPage >= 1 }">
						<li>
							<a href="user-list.do?page=${pagination.prevPage}">
								◀️
							</a>
						</li>
					</c:when>
				</c:choose>
				<c:forEach var = "i" begin="${pagination.startPage}" end="${pagination.endPage}" step="1">
					<c:choose>
						<c:when test="${ pagination.page eq i }">
							<li style="background-color:#ededed;">
								<span>${i}</span>
							</li>
						</c:when>
						<c:when test="${pagination.page ne i}">
							<li>
								<a href="user-list.do?page=${i}">${i}</a>
							</li>
						</c:when>
					</c:choose>
				</c:forEach>
				 <c:choose>
					<c:when test="${ pagination.nextPage <= pagination.lastPage }">
						<li style="">
							<a href="user-list.do?page=${pagination.nextPage}">▶</a>
						</li>
					</c:when>
				</c:choose> 
			</ul>
		</div>
</body>
</html>











