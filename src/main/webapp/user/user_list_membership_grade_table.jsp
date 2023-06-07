<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<tr >
				<td><a href="/tonysproject/user-detail.do?u_idx=${item.u_idx}">${item.u_idx}</a></td>
				<td>${item.u_id}</td>
				<td>${item.u_name}</td>
				<td >
					<c:if test="${item.u_auth eq 0}">
						<button type="submit" class="change_membership_grade_btn"
							u_idx="${item.u_idx}" u_auth="${item.u_auth}">관리자</button>
					</c:if> 
					<c:if test="${item.u_auth eq 1}">
						<button type="submit" class="change_membership_grade_btn"
							u_idx="${item.u_idx}" u_auth="${item.u_auth}">일반회원</button>
					</c:if>
				</td>
			</tr>
		</c:forEach>

