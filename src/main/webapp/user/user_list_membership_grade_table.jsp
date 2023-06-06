<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	
