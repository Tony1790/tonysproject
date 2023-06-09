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
/* 전체 body에 적용될 스타일 */
body {
	font-family: Arial, sans-serif;
	background-color: #f0f0f0;
	margin: 0;
}

/* h2 태그에 적용될 스타일 */
h2 {
	text-align: center;
	color: #333;
	margin-top: 40px;
}

/* 테이블에 적용될 스타일 */
table {
	border-collapse: collapse;
	margin: 40px auto;
	background-color: #fff;
	border: 1px solid #ccc;
}

/* 테이블 제목 셀에 적용될 스타일 */
table th {
	font-weight: bold;
	text-align: center;
	border: 1px solid #ccc;
	background-color: #f0f0f0;
}

/* 테이블 내용 셀에 적용될 스타일 */
table td {
	border: 1px solid #ccc;
	text-align: center;
	padding: 10px;
}

/* 링크에 적용될 스타일 */
a {
	color: #333;
	text-decoration: none;
	padding: 5px;
	border: 1px solid #ccc;
	margin: 10px;
	display: inline-block;
}

/* 마우스를 올리면 링크에 적용될 스타일 */
a:hover {
	background-color: #f0f0f0;
}

/* 전체 게시글 수 셀에 적용될 스타일 */
.total_count_board {
	text-align: center;
	font-size: 16px;
	font-weight: bold;
	color: #333;
	height: 35px;
}
ul {
		width : 500px;
		height : 50px;
		margin: 10px auto;
	}
li {
		list-style : none;
		width : 50px;
		height : 50px;
		border: 1px solid #ededed;
		float: left;
		text-align: center;
		margin: 0 5px;
		border-radius: 5px;
	}
</Style>
<body>
	<h2>게시글 목록</h2>
	<a href = "/tonysproject/user-list.do">유저 목록 가기</a>
	<a href="/tonysproject/board-create.do">게시글 작성하기</a>
	<a href="/tonysproject/user-login.do">로그인</a>
	
	<form action="board-list.do" method="get" class="search_form">
		<select name="search_option" class="search_option">
			<option value="">검색</option>
			<option value="제목">제목</option>
			<option value="내용" >내용</option>
			<option value="제목+내용" >제목+내용</option>
			<option value="작성자" >작성자</option>
		</select>
		<input type="text" name="keyword" class="search_keyword" size=10></input>
		<button type=submit class="search_btn">검색</button>
	</form>
	<table>
		<tr>
			<!-- 여기에 전체 게시글 숫자 들어가야함 -->
			<th class = "total_count_board" colspan="5">전체 게시글 수 : ${pagination.count}</th>
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
				<td>${item.b_writer}</td>
				<td>${item.b_date}</td>
				<td>${item.b_view}</td>
			</tr>
		</c:forEach>
	</table>
	<div>
		<ul>
			<c:choose>
				<c:when test="${pagination.prevPage >= 1 }">
					<li>
						<a href="board-list.do?page=${pagination.prevPage}&search_option=${search.category}&keyword=${search.keyword}">
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
							<a href="board-list.do?page=${i}&search_option=${search.category}&keyword=${search.keyword}">${i}</a>
						</li>
					</c:when>
				</c:choose>
			</c:forEach>
			 <c:choose>
				<c:when test="${ pagination.nextPage <= pagination.lastPage }">
					<li>
						<a href="board-list.do?page=${pagination.nextPage}&search_option=${search.category}&keyword=${search.keyword}">▶</a>
					</li>
				</c:when>
			</c:choose> 
		</ul>
	</div>
</body>
</html>