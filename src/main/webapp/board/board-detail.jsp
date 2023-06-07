<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${board.b_title}</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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

a.edit-btn:hover, a.delete-btn:hover, a.re_content_btn {
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

.cmt-container {
	max-width : 1200px;
	margin : 0 auto;
}
</style>
</head>
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
			<td>댓글수 :</td>
		</tr>


		<tr>
			<td class="content">내용 : ${board.b_content}</td>
		</tr>

		<tr style="height:50px;">
			<td class="edit-td-btn" u_auth="${user.u_auth}" u_idx="${user.u_idx}" b_udx = "${board.u_idx}" b_idx="${board.b_idx}" style="border:none;">
				<a class="edit-btn" href="/tonysproject/board-edit.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:#818181;color:#fff;" >수정</a>
			</td>
			<td class="delete-td-btn" u_auth="${user.u_auth}" u_idx="${user.u_idx}" b_udx = "${board.u_idx}" bIdx="${board.b_idx}" style="border:none;">
				<a class="delete-btn" href="/tonysproject/board-delete.do?b_idx=${board.b_idx}" style="width:70%;font-weight:700;background-color:red;color:#fff;" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
			</td>
			<td style="border:none;">
				<a class="re_content_btn" href="/tonysproject/reboard-create.do?b_idx=${board.b_idx}&b_group=${board.b_group}&b_depth=${board.b_depth}&b_order=${board.b_order}" style="width:70%;font-weight:700;background-color:#333;color:#fff;">답글</a>
				
			</td>
		</tr>
	</table>	
		
	<div class="cmt-container">
		<div class="cmt-box1">
			<div class="cmt-count">
				<span>댓글 수 : N개</span>
			</div>
			<div class="cmt-list">
				<ul>
					<c:forEach items="${commentList}" var="cmt" varStatus="status">
							<li>
								<div>
									<span>${cmt.c_writer}</span>
								</div>
								<div>
									<span>${cmt.c_content}</span>
								</div>
								<div>
									<span>${cmt.c_date}</span>
								</div>
								<button class="recmt-show-btn btnReCommentForm">답글</button>
								<button class="recmt-edit-btn btnReCommentEdit">수정</button>
								<button type=button class="recmt-delete-btn btnReCommentdelete" c_idx = "${cmt.c_idx}" b_idx = "${cmt.b_idx}" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
								<li class="recmt-create-form" style="display: none">
									<textarea rows="2" cols="80"></textarea>
									<button type="button" class="recmt_submit_btn" 
										c_group="${cmt.c_group}"
										c_order="${cmt.c_order}"
										c_depth="${cmt.c_depth}"
										b_idx = "${cmt.b_idx}"
										>등록</button>
									<button type="button" class="recmt_cancel_btn">취소</button>
								</li>
								<li class="recmt-edit-form" style="display: none">
									<textarea rows="3" cols="80">${cmt.c_content}</textarea>
									<button type="button" class="recmt_edit_submit_btn" 
										c_group="${cmt.c_group}"
										c_order="${cmt.c_order}"
										c_depth="${cmt.c_depth}"
										b_idx = "${cmt.b_idx}"
										c_writer = "${cmt.c_writer}"
										c_idx = "${cmt.c_idx}"
										>등록</button>								
									<button type="button" class="recmt_cancel_btn">취소</button>
								</li>
							</li>
							
					</c:forEach>
				</ul>
			</div>
		</div>
		<form action="/tonysproject/comment-create-process.do" method="POST">
			<div class="cmt-editor">
				<textarea rows="5" cols="25" name="origin-cmt-text" placeholder="댓글을 입력하세요" wrap="soft" required></textarea>
				<input type="hidden" name="b_idx" value="${board.b_idx}" ></input>
				<button type="submit" class="origin-cmt-submit-btn">
					작성
				</button>
			</div>
		</form>
	</div>
	<script>
		$(document).ready(function(){
			var UAuth = $('.delete-td-btn').attr("u_auth");
			var uIdx = $('.delete-td-btn').attr("u_idx");
			var buIdx = $('.delete-td-btn').attr("b_udx");
			
			if(UAuth == 0) {
				$('.edit-td-btn').css('display', 'none');
			} else if(uAuth !== 0 && uIdx !== buIdx) {
				$('.edit-td-btn').css('display', 'none');
				$('.delete-td-btn').css('display', 'none');
			}
		});
	
		/* let bIdx = $(.edit-td-btn).attr("b_idx");
		$.ajax({
			url: "board-detail.do",
			method : "GET",
			data : {
				b_idx : bIdx;
			},
			
			success: function(response) {
				console.log(response);
			}
		}); */
	
		$(document).on('click', '.btnReCommentForm', function () {
			
			$(this).closest('li').next('li').css('display', 'block');
		});
		
		$(document).on('click', '.recmt_cancel_btn', function () {
			$(this).parent().css('display', 'none');
		});
		
		$(document).on("click", ".recmt-edit-btn", function () {
			$(this).closest('li').next('li').next('li').css('display', 'block');
		})
		
		$(document).on('click', '.recmt-delete-btn', function() {
			let cIdx = $(this).attr('c_idx');
			let bIdx = $(this).attr('b_idx');
			
			$.ajax({
				  method: "POST",
				  url: "recomment-delete-cmt.do",
				  data: {	c_idx : cIdx,
					  		b_idx: bIdx
				  }
				})
			    .done(function( msg ) {
			    	$('.cmt-list').html(msg);

			    });
		})
		
		
		$(document).on('click', '.recmt_submit_btn', function () {
			let cGroup = $(this).attr('c_group');
			let cOrder = $(this).attr('c_order');
			let cDepth = $(this).attr('c_depth');
			let bIdx = $(this).attr('b_idx');
			let cContent = $(this).closest('li').find('textarea').val();

			$.ajax({
			  method: "POST",
			  url: "recomment-create-cmt-list.do",
			  data: {	c_group: cGroup,
						c_order: cOrder,
						c_depth: cDepth,
						b_idx: bIdx,
						c_content : cContent
			  }
			})
		    .done(function( msg ) {
		    	$('.cmt-list').html(msg);
		      //alert( "Data Saved: " + msg );
		      //console.log(msg);
		    });
		});
		
		$(document).on('click', '.recmt_edit_submit_btn', function () {
			let cGroup = $(this).attr('c_group');
			let cOrder = $(this).attr('c_order');
			let cDepth = $(this).attr('c_depth');
			let bIdx = $(this).attr('b_idx');
			let cContent = $(this).closest('li').find('textarea').val();
			let cWriter = $(this).attr('c_writer');
			let cIdx = $(this).attr('c_idx');
			
			$.ajax({
			  method: "POST",
			  url: "recomment-edit-cmt-list.do",
			  data: {	c_group: cGroup,
						c_order: cOrder,
						c_depth: cDepth,
						b_idx: bIdx,
						c_content : cContent,
						c_idx : cIdx
			  }
			})
		    .done(function( msg ) {
		    	$('.cmt-list').html(msg);
		      //alert( "Data Saved: " + msg );
		      //console.log(msg);
		    });
		});
		
	</script>
</body>

</html>