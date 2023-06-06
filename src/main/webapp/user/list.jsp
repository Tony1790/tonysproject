<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록2</title>
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
	<h2>회원 목록</h2>
	<a class="newJoin" href="user-insert.do"><h3>회원 가입</h3></a>
	<a href="board-list.do">게시글 목록</a>
		<table>
			<tr>
				<td colspan="4">전체 회원 수 : ${pagination.count}</td>
			</tr>	
			<tr>
				<th>No</th>
				<th>ID</th>
				<th>이름</th>
				<th>회원등급</th>
			</tr>
			<c:forEach items="${list}" var="item" varStatus="status" >
				<tr class="user_list_membership_grade_table">
					<td><a href="/tonysproject/user-detail.do?u_idx=${item.u_idx}">${item.u_idx}</a></td>
					<td>${item.u_id}</td>
					<td>${item.u_name}</td>
					<td>
						<button 
							type="submit" 
							class="change_membership_grade_btn" 
							u_idx = "${item.u_idx}"
							u_auth = "${item.u_auth}"
							>
						</button>						
					</td>
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
<script>
	$(document).ready(function() {
		  $('.change_membership_grade_btn').each(function() {
		    var uAuth = $(this).attr('u_auth');
		    if (uAuth == 0) {
		      $(this).text("관리자");
		    } else {
		      $(this).text("일반회원");
		    }
		  });
	});
	
	
	 $(document).on('click', '.change_membership_grade_btn', function() {
	   var uIdx = $(this).attr('u_idx');
	   var uAuth = $(this).attr('u_auth');
	   var clickedButton = $(this);
	   console.log($(this));
	   
	   $.ajax({
	     method: 'POST',
	     url: "user-membership-change.do",
	     data: {
	       u_idx: uIdx,
	       u_auth: uAuth
	     }
	   })
	   .done(function(msg) {
		   //msg는 Jquery AJAX 객체인데 어떻게 바꿀 수 있나?
				   
				   
				   
	   		/* var uAuthFromMsg = $(msg).attr('u_auth');
	   		console.log(uAuthFromMsg);
	   		
	   		let modifiedMsg = $(msg).text("1");
		   	if(uAuthFromMsg == 0) {
		   		modifiedMsg = $(msg).text("관리자");
	   		} else {
	   			modifiedMsg = $(msg).text("일반회원");
	   		}
	   		 */
		   	$(".user_list_membership_grade_table").html(msg);
	   		/* console.log($(this));
	   		if (uAuthFromMsg == 0) {
	   	      clickedButton.text("관리자");
	   	    } else {
	   	      clickedButton.text("일반회원");
	   	    } */
	   });
	 });
	
</script>
</html>











